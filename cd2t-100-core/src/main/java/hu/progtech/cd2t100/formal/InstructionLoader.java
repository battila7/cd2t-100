package hu.progtech.cd2t100.formal;

import java.lang.annotation.Annotation;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.io.InputStream;
import java.io.IOException;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import groovy.lang.GroovyClassLoader;
import org.codehaus.groovy.control.CompilationFailedException;

import org.apache.commons.io.IOUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableInt;

import hu.progtech.cd2t100.formal.annotations.*;
import hu.progtech.cd2t100.computation.ExecutionEnvironment;

/**
 *  The class that's able to load Groovy classes and create new
 *  {@code InstructionInfo} objects. When loading external Groovy code,
 *  this class checks if it's valid Groovy code containing one or more
 *  {@code apply} methods with proper annotations and matching parameter
 *  types.
 */
public class InstructionLoader {
  private static final Logger	logger = LoggerFactory.getLogger(InstructionLoader.class);

  /*
   * There's no need for ^ and $ because we're going to
   * use Matcher.matches() which matches against the entire string.
   */
  private static final Pattern wordPattern =
    Pattern.compile("[^\\s\\!\\:\\@\\.\\#\\,]+",
                    Pattern.CASE_INSENSITIVE);

  private static final List<Class<?>> autoImportClasses =
    Arrays.asList(Opcode.class, Rules.class, Parameter.class,
                  ExecutionEnvironment.class, ParameterType.class,
                  ReadResult.class, MutableInt.class);

  private static final String automaticImportStatement;

  static {
    automaticImportStatement =
      autoImportClasses.stream()
                       .map(x -> "import " + x.getName())
                       .collect(Collectors.joining(";\n", "", ";\n\n"));

    logger.debug("Automatic import statements initialized:\n{}",
      automaticImportStatement);
  }

  private InstructionLoader() {
    /*
     *  The class must not be instantiated.
     */
  }

  /**
   *  Loads and creates and {@code InstructionInfo} from the contents
   *  of the specified {@code InputStream}. Calls
   *  {@link InstructionLoader#loadInstruction(String)}.
   *
   *  @param codeStream an {@code InputStream} of valid Groovy code
   *
   *  @return an {@code InstructionInfo} object created from the specified Groovy code
   *
   *  @throws IOException If any kind of I/O exception occurs.
   *  @throws CompilationFailedException If the contents of the specified stream
   *                                     cannot be parsed into a {@code Class}.
   *  @throws InvalidInstructionClassException
   *            If a {@code Class} object can be created but it's not annotated
   *            properly or does not contain any applicable {@code apply} methods.
   *  @throws InvalidFormalParameterListException
   *            If any of the {@code apply} methods has a faulty formal
   *            parameter list.
   */
  public static InstructionInfo loadInstruction(InputStream codeStream)
    throws IOException,
           CompilationFailedException,
           InvalidInstructionClassException,
           InvalidFormalParameterListException
  {
    String code = IOUtils.toString(codeStream, "UTF-8");

    return loadInstruction(code);
  }

  /**
   *  Attempts to parse the Groovy code specified as the parameter and
   *  instantiates a new {@code InstructionInfo} object. Throws an exception
   *  if the code cannot be parsed or the parsed {@code Class} object does
   *  not meet the requirements (methods, annotations).
   *
   *  @param classCode the code of a valid Groovy class
   *
   *  @return an {@code InstructionInfo} object created from the specified Groovy code
   *
   *  @throws CompilationFailedException If the contents of the specified stream
   *                                     cannot be parsed into a {@code Class}.
   *  @throws InvalidInstructionClassException
   *            If a {@code Class} object can be created but it's not annotated
   *            properly or does not contain any applicable {@code apply} methods.
   *  @throws InvalidFormalParameterListException
   *            If any of the {@code apply} methods has a faulty formal
   *            parameter list.
   */
  public static InstructionInfo loadInstruction(String classCode)
    throws CompilationFailedException,
           InvalidInstructionClassException,
           InvalidFormalParameterListException
  {
    String actualCode = automaticImportStatement + classCode;

    GroovyClassLoader groovyClassLoader
      = new GroovyClassLoader(InstructionLoader.class.getClassLoader());

    Class<?> instructionClass = groovyClassLoader.parseClass(actualCode);

    String opcode = checkOpcode(instructionClass)
                    .orElseThrow(InvalidInstructionClassException::new);

    List<String> rules = getUsedPreprocessorRules(instructionClass);

    List<FormalCall> calls = getFormalCalls(instructionClass);

    if (calls.isEmpty()) {
      throw new InvalidInstructionClassException(
        "Method(s) with name \"apply\" not found.");
    }

    return new InstructionInfo(opcode, rules, instructionClass, calls);
  }

  private static Optional<String> checkOpcode(Class<?> instructionClass) {
    if (!(instructionClass.isAnnotationPresent(Opcode.class))) {
      return Optional.empty();
    }

    String opcode = instructionClass.getDeclaredAnnotation(Opcode.class)
                                    .value();

    logger.debug("Parsing Groovy class for {}.", opcode);

    return Optional.ofNullable(opcode)
                   .filter(x -> wordPattern.matcher(x).matches());
  }

  private static List<String> getUsedPreprocessorRules(Class<?> instructionClass)
    throws InvalidInstructionClassException
  {
    ArrayList<String> rules = new ArrayList<>();

    Rules r = instructionClass.getDeclaredAnnotation(Rules.class);

    if (r == null) {
      return rules;
    }

    for (String rule : r.value()) {
      if (!(wordPattern.matcher(rule).matches())) {
        throw new InvalidInstructionClassException(
          "Invalid rule name: " + rule);
      } else {
        rules.add(rule);
      }
    }

    return rules;
  }

  private static List<FormalCall> getFormalCalls(Class<?> instructionClass)
    throws InvalidFormalParameterListException,
           InvalidInstructionClassException
  {
    List<Method> applyMethods =
      Arrays.stream(instructionClass.getDeclaredMethods())
            .filter(x -> x.getName().equals("apply"))
            .filter(InstructionLoader::checkModifiers)
            .collect(Collectors.toList());

    ArrayList<FormalCall> calls = new ArrayList<>();

    /*
     *  map() cannot be used since it is not possible to throw checked
     *  exceptions from streams
     */
    for (Method m : applyMethods) {
      if (!(Modifier.isStatic(m.getModifiers()))) {
        throw new InvalidInstructionClassException(
          "\"apply\" methods must be public and static.");
      }

      FormalCall f = formalCallFromMethod(m);

      if (checkAmbiguousity(calls, f)) {
        throw new InvalidInstructionClassException(
          "\"apply\" methods must not be ambiguous. Check parameters with implicit values.");
      }

      calls.add(f);
    }

    return calls;
  }

  private static boolean checkAmbiguousity(List<FormalCall> calls, FormalCall f) {
    for (FormalCall call : calls) {
      if (isAmbiguous(call, f)) {
        return true;
      }
    }

    return false;
  }

  private static boolean isAmbiguous(FormalCall callA, FormalCall callB) {
    if (callA.getDemandedParams() != callB.getDemandedParams()) {
      return false;
    }

    List<FormalParameter> paramsA = callA.getFormalParameterList(),
                          paramsB = callB.getFormalParameterList();

    for (int i = 0; i < callA.getDemandedParams(); ++i) {
      if (paramsA.get(i).getParameterType() != paramsB.get(i).getParameterType()) {
        return false;
      }
    }

    return true;
  }

  private static boolean checkModifiers(Method method) {
    int permittedModifiers = Modifier.PUBLIC + Modifier.STATIC;

    int actualModifiers = method.getModifiers() & permittedModifiers;

    return permittedModifiers == actualModifiers;
  }

  private static FormalCall formalCallFromMethod(Method method)
    throws InvalidFormalParameterListException
  {
    Class<?>[] paramTypes =
      method.getParameterTypes();

    if ((paramTypes.length == 0) ||
        (paramTypes[0] != ExecutionEnvironment.class)) {
      throw new InvalidFormalParameterListException(
        "The method must accept at least one parameter with type ExecutionEnvironment.");
    }

    Annotation[][] paramAnnotations =
      method.getParameterAnnotations();

    for (int i = 1; i < paramAnnotations.length; ++i) {
      Annotation[] a = paramAnnotations[i];

      if ((a.length != 1) || (!(a[0] instanceof Parameter))) {
        throw new InvalidFormalParameterListException(
          "All parameters must be annotated with the Parameter annotation (except the ExecutionEnvironment).");
      }
    }

    List<FormalParameter> formalParameters
      = getFormalParameters(paramTypes, paramAnnotations);

    int demandedParams = (int)formalParameters
                                .stream()
                                .filter(x -> !x.hasImplicitValue())
                                .count();

    return new FormalCall(formalParameters, method, demandedParams);
  }

  private static List<FormalParameter> getFormalParameters(
    Class<?>[] paramTypes, Annotation[][] paramAnnotations)
    throws InvalidFormalParameterListException
  {
    ArrayList<FormalParameter> formalParameters =
      new ArrayList<>();

    boolean implicitValueSet = false;

    for (int i = 1; i < paramTypes.length; ++i) {
      Parameter param = (Parameter)paramAnnotations[i][0];

      if (param.parameterType().getRequiredClass() != paramTypes[i]) {
        throw new InvalidFormalParameterListException(
          "Method parameter type did not match the required parameter type.");
      }

      if ((StringUtils.isEmpty(param.implicitValue())) &&
          (implicitValueSet)) {
        throw new InvalidFormalParameterListException(
          "Parameter without an implicit value must not occur after a parameter with an implicit value");
      }

      implicitValueSet = !StringUtils.isEmpty(param.implicitValue());

      formalParameters.add(FormalParameter.fromParameterAnnotation(param));
    }

    return formalParameters;
  }
}

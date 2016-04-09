package hu.progtech.cd2t100.computation;

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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableInt;

import hu.progtech.cd2t100.computation.annotations.*;

public class InstructionLoader {
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
                  MutableInt.class);

  private static final String automaticImportStatement;

  static {
    automaticImportStatement =
      autoImportClasses.stream()
                       .map(x -> "import " + x.getName())
                       .collect(Collectors.joining(";\n", "", ";\n\n"));
  }

  private InstructionLoader() {
    /*
     *  The class must not be instantiated.
     */
  }

  public static InstructionInfo loadInstruction(InputStream codeStream)
    throws IOException,
           CompilationFailedException,
           InvalidInstructionClassException,
           InvalidFormalParameterListException
  {
    String code = IOUtils.toString(codeStream, "UTF-8");

    return loadInstruction(code);
  }

  public static InstructionInfo loadInstruction(String classCode)
    throws CompilationFailedException,
           InvalidInstructionClassException,
           InvalidFormalParameterListException
  {
    classCode = automaticImportStatement + classCode;

    GroovyClassLoader groovyClassLoader
      = new GroovyClassLoader(InstructionLoader.class.getClassLoader());

    Class<?> instructionClass = groovyClassLoader.parseClass(classCode);

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
    /*
     *  map() cannot be used since it is not possible to throw checked
     *  exceptions from streams
     */
    List<Method> applyMethods =
      Arrays.stream(instructionClass.getDeclaredMethods())
            .filter(x -> x.getName().equals("apply"))
            .collect(Collectors.toList());

    ArrayList<FormalCall> calls = new ArrayList<>();

    for (Method m : applyMethods) {
      if (!(Modifier.isStatic(m.getModifiers()))) {
        throw new InvalidInstructionClassException(
          "\"apply\" methods must be static.");
      }

      calls.add(formalCallFromMethod(m));
    }

    return calls;
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

    return new FormalCall(formalParameters, method);
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

package hu.progtech.cd2t100.computation;

import java.lang.annotation.Annotation;

import java.lang.reflect.Method;

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

import hu.progtech.cd2t100.computation.annotations.*;

class InstructionLoader {
  /*
   * There's no need for ^ and $ because we're going to
   * use Matcher.matches() which matches against the entire string.
   */
  private static Pattern opcodePattern =
    Pattern.compile("[^\\s\\!\\:\\@\\.\\#\\,]+",
                    Pattern.CASE_INSENSITIVE);

  public static InstructionInfo loadInstruction(InputStream codeStream)
    throws IOException,
           CompilationFailedException,
           InvalidOrMissingOpcodeException,
           MissingApplyMethodException,
           InvalidFormalParameterListException
  {
    String code = IOUtils.toString(codeStream, "UTF-8");

    return loadInstruction(code);
  }

  public static InstructionInfo loadInstruction(String classCode)
    throws CompilationFailedException,
           InvalidOrMissingOpcodeException,
           MissingApplyMethodException,
           InvalidFormalParameterListException
  {
    GroovyClassLoader groovyClassLoader
      = new GroovyClassLoader(InstructionLoader.class.getClassLoader());

    Class<?> instructionClass = groovyClassLoader.parseClass(classCode);

    String opcode = checkOpcode(instructionClass)
                    .orElseThrow(InvalidOrMissingOpcodeException::new);

    List<String> rules = getUsedPreprocessorRules(instructionClass);

    List<FormalCall> calls = getFormalCalls(instructionClass);

    if (calls.isEmpty()) {
      throw new MissingApplyMethodException();
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
                   .filter(x -> opcodePattern.matcher(x).matches());
  }

  private static List<String> getUsedPreprocessorRules(Class<?> instructionClass) {
    return
      Arrays.stream(instructionClass
                   .getDeclaredAnnotationsByType(Rule.class))
            .map(Rule::value)
            .collect(Collectors.toList());
  }

  private static List<FormalCall> getFormalCalls(Class<?> instructionClass)
    throws InvalidFormalParameterListException
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
      throw new InvalidFormalParameterListException();
    }

    Annotation[][] paramAnnotations =
      method.getParameterAnnotations();

    for (int i = 1; i < paramAnnotations.length; ++i) {
      Annotation[] a = paramAnnotations[i];

      if ((a.length != 1) ||
          (a[0].getClass() != Parameter.class)) {
        throw new InvalidFormalParameterListException();
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
        throw new InvalidFormalParameterListException();
      }

      if ((StringUtils.isEmpty(param.implicitValue())) &&
          (implicitValueSet)) {
        throw new InvalidFormalParameterListException();
      }

      implicitValueSet = !StringUtils.isEmpty(param.implicitValue());

      formalParameters.add(FormalParameter.fromParameterAnnotation(param));
    }

    return formalParameters;
  }
}

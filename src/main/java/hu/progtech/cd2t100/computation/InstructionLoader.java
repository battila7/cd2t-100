package hu.progtech.cd2t100.computation;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import java.io.InputStream;
import java.io.IOException;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import groovy.lang.GroovyClassLoader;
import org.codehaus.groovy.control.CompilationFailedException;

import org.apache.commons.io.IOUtils;

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
    throws IOException, CompilationFailedException,
           InvalidOrMissingOpcodeException
  {
    String code = IOUtils.toString(codeStream, "UTF-8");

    return loadInstruction(code);
  }

  public static InstructionInfo loadInstruction(String classCode)
    throws CompilationFailedException, InvalidOrMissingOpcodeException
  {
    GroovyClassLoader groovyClassLoader
      = new GroovyClassLoader(InstructionLoader.class.getClassLoader());

    Class<?> instructionClass = groovyClassLoader.parseClass(classCode);

    String opcode = checkOpcode(instructionClass)
                    .orElseThrow(InvalidOrMissingOpcodeException::new);

    List<String> rules = getUsedPreprocessorRules(instructionClass);

    return null;
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
}

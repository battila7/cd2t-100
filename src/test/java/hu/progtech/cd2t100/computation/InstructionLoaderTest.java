package hu.progtech.cd2t100.computation;

import java.io.InputStream;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.StringStartsWith.startsWith;

public class InstructionLoaderTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static InputStream getCodeStream(String resourceName) {
    return InstructionLoaderTest.class
                                .getClassLoader()
                                .getResourceAsStream(resourceName);
  }

  @Test
  public void noOpcode() throws Exception {
    thrown.expect(InvalidInstructionClassException.class);
    thrown.expectMessage(startsWith("Opcode"));

    InstructionLoader.loadInstruction(getCodeStream("NoOpcode.groovy"));
  }

  @Test
  public void invalidOpcode() throws Exception {
    thrown.expect(InvalidInstructionClassException.class);
    thrown.expectMessage(startsWith("Opcode"));

    InstructionLoader.loadInstruction(getCodeStream("InvalidOpcode.groovy"));
  }

  @Test
  public void missingApply() throws Exception {
    thrown.expect(InvalidInstructionClassException.class);
    thrown.expectMessage(startsWith("Method(s)"));

    InstructionLoader.loadInstruction(getCodeStream("MissingApply.groovy"));
  }

  @Test
  public void invalidRule() throws Exception {
    thrown.expect(InvalidInstructionClassException.class);
    thrown.expectMessage(startsWith("Invalid rule"));

    InstructionLoader.loadInstruction(getCodeStream("InvalidRule.groovy"));
  }

  @Test
  public void notStatic() throws Exception {
    thrown.expect(InvalidInstructionClassException.class);
    thrown.expectMessage(startsWith("\"apply\""));

    InstructionLoader.loadInstruction(getCodeStream("NotStatic.groovy"));
  }

  @Test
  public void noArguments() throws Exception {
    thrown.expect(InvalidFormalParameterListException.class);
    thrown.expectMessage(startsWith("The method must accept"));

    InstructionLoader.loadInstruction(getCodeStream("NoArguments.groovy"));
  }

  @Test
  public void InvalidFirstArgument() throws Exception {
    thrown.expect(InvalidFormalParameterListException.class);
    thrown.expectMessage(startsWith("The method must accept"));

    InstructionLoader.loadInstruction(getCodeStream("InvalidFirstArgument.groovy"));
  }

  @Test
  public void nop() throws Exception {
    InstructionLoader.loadInstruction(getCodeStream("Nop.groovy"));
  }
}

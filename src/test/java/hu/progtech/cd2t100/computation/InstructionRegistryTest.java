package hu.progtech.cd2t100.computation;

import java.io.InputStream;

import java.util.HashMap;

import org.junit.Test;
import org.junit.Rule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.rules.ExpectedException;

import hu.progtech.cd2t100.formal.InstructionInfo;
import hu.progtech.cd2t100.formal.InstructionLoader;

public class InstructionRegistryTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private InstructionRegistry instructionRegistry;

  private static InputStream getCodeStream(String resourceName) {
    return InstructionRegistryTest.class
                                  .getClassLoader()
                                  .getResourceAsStream(resourceName);
  }

  @Before
  public void setUp() {
    instructionRegistry = new InstructionRegistry();
  }

  @Test
  public void addTest() throws Exception {
    InstructionInfo info =
      InstructionLoader.loadInstruction(getCodeStream("TestInstruction.groovy"));

    instructionRegistry.registerInstruction(info);

    Assert.assertEquals("Registered instruction must be equal to loaded.",
                        info,
                        instructionRegistry.getInstructionInfoFor(info.getOpcode()));
  }

  @Test
  public void registerTwice() throws Exception {
    thrown.expect(OpcodeAlreadyRegisteredException.class);

    InstructionInfo info =
      InstructionLoader.loadInstruction(getCodeStream("TestInstruction.groovy"));

    instructionRegistry.registerInstruction(info);
    instructionRegistry.registerInstruction(info);
  }

  @Test
  public void ruleTest() throws Exception {
    HashMap<String, String> rules = new HashMap<>();

    rules.put("clampat", "999");
    rules.put("overflow", "off");

    instructionRegistry.putRules(rules);

    Assert.assertEquals("Rule map must contain the newly added rules.",
                        rules, instructionRegistry.getRules());
  }

  @Test
  public void overrideRuleTest() throws Exception {
    HashMap<String, String> rules = new HashMap<>();

    HashMap<String, String> overrideRules = new HashMap<>();

    rules.put("clampat", "999");
    rules.put("overflow", "off");

    overrideRules.put("clampat", "1000");

    instructionRegistry.putRules(rules);

    instructionRegistry.putRules(overrideRules);

    Assert.assertEquals("Rule map must be updated.",
                        "1000", instructionRegistry.getRuleValue("clampat"));
  }
}

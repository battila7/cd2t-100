package hu.progtech.cd2t100.asm;

import java.util.Set;
import java.util.HashSet;

import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

public class CodeFactoryTest {
  private Set<String> registerNameSet;
  private Set<String> portNameSet;

  @Before
  public void initFixture() {
    this.registerNameSet = new HashSet<>();
    this.portNameSet = new HashSet<>();
  }

  @Test
  public void emptyInputTest() {
    CodeElementSet elementSet =
      CodeFactory.createCodeElementSet(registerNameSet, portNameSet, "");

    Assert.assertEquals("Empty input must produce empty exception list.",
                        0, elementSet.getExceptionList().size());

    Assert.assertEquals("If exception list is empty, no exception occurred.",
                        false, elementSet.isExceptionOccurred());
  }
}

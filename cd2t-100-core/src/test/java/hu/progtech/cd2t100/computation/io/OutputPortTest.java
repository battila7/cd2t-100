package hu.progtech.cd2t100.computation.io;

import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

public class OutputPortTest {
  private OutputPort port;

  @Before
  public void setUp() {
    port = new OutputPort("OP");
  }

  @Test
  public void hasDataTest() {
    port.write(10);

    Assert.assertFalse("The post must not contain data",
                       port.hasData());
  }

  @Test
  public void readContentsTest() {
    port.write(10);

    Assert.assertEquals("The post must not contain data",
                        null, port.readContents());
  }
}

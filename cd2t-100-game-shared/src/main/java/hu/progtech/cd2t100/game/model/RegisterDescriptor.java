package hu.progtech.cd2t100.game.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *  {@code RegisterDescriptor} represents a
 *  {@link hu.progtech.cd2t100.computation.io.Register} with its
 *  local-name and capacity. 
 */
@XmlRootElement(name = "registerDescriptor")
public class RegisterDescriptor {
  private String name;

  private int capacity;

  /**
   *  Gets the (local) name of register.
   *
   *  @return the name
   */
  @XmlAttribute
  public String getName() {
    return name;
  }

  /**
   *  Sets the (local) name of the register.
   *
   *  @param name the name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   *  Gets the capacity.
   *
   *  @return the capacity
   */
  @XmlAttribute
  public int getCapacity() {
    return capacity;
  }

  /**
   *  Sets the capacity.
   *
   *  @param capacity the capacity
   */
  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  @Override
  public String toString() {
    return name + "[" + capacity + "]";
  }
}

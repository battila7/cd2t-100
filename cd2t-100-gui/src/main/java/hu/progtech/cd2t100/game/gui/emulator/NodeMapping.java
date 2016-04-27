package hu.progtech.cd2t100.game.gui.emulator;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

import hu.progtech.cd2t100.game.model.NodeDescriptor;
import hu.progtech.cd2t100.game.model.RegisterDescriptor;

/**
 *  Helper class with values bound to properties of the appropiate
 *  UI elements to ease displaying and collecting the data of the {@code Node}s.
 */
public class NodeMapping {
  private final String globalName;

  private final StringProperty sourceCode;

  private final ObservableList<RegisterMapping> registerList;

  private NodeMapping(String globalName, List<RegisterMapping> registerList) {
    this.globalName = globalName;

    this.registerList = FXCollections.observableArrayList(registerList);

    sourceCode = new SimpleStringProperty();
  }

  /**
   *  Factory method for creating a {@code NodeMapping} instance using an existing
   *  {@code NodeDescriptor}.
   *
   *  @param descriptor the {@code NodeDescriptor}
   *
   *  @return a new mapping
   */
  public static NodeMapping fromNodeDescriptor(NodeDescriptor descriptor) {
    List<RegisterMapping> mappings =
      descriptor.getRegisterDescriptors()
                .stream()
                .map(r -> new RegisterMapping(r.getName(), r.getCapacity()))
                .collect(Collectors.toList());

    return new NodeMapping(descriptor.getGlobalName(), mappings);
  }

  /**
   *  Gets the global name.
   *
   *  @return the global name
   */
  public String getGlobalName() {
    return globalName;
  }

  /**
   *  Gets the list of register mappings.
   *
   *  @return the mapping list
   */
  public ObservableList<RegisterMapping> getRegisterList() {
    return registerList;
  }

  /**
   *  Gets the source code.
   *
   *  @return the source code
   */
  public String getSourceCode() {
    return sourceCode.get();
  }

  /**
   *  Binds the source code property to the specified property.
   *
   *  @param property the property
   */
  public void bindSourceCode(StringProperty property) {
    sourceCode.bind(property);
  }
}

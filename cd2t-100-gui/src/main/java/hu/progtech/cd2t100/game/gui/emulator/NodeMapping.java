package hu.progtech.cd2t100.game.gui.emulator;

import java.util.Map;
import java.util.HashMap;
import static java.util.stream.Collectors.toMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

import hu.progtech.cd2t100.game.model.NodeDescriptor;
import hu.progtech.cd2t100.game.model.RegisterDescriptor;

public class NodeMapping {
  private final String globalName;

  private final StringProperty sourceCode;

  private final Map<String, RegisterMapping> registerMappings;

  private final ObservableList<RegisterMapping> mappingList;

  private NodeMapping(String globalName, Map<String, RegisterMapping> registerMappings) {
    this.globalName = globalName;

    this.registerMappings = registerMappings;

    mappingList = FXCollections.observableArrayList(registerMappings.values());

    sourceCode = new SimpleStringProperty();
  }

  public static NodeMapping fromNodeDescriptor(NodeDescriptor descriptor) {
    Map<String, RegisterMapping> mappings = new HashMap<>();

    mappings =
      descriptor.getRegisterDescriptors()
                .stream()
                .collect(toMap(RegisterDescriptor::getName,
                               r -> new RegisterMapping(r.getName(), r.getCapacity())));

    return new NodeMapping(descriptor.getGlobalName(), mappings);
  }

  public Map<String, RegisterMapping> getMapping() {
    return registerMappings;
  }

  public String getGlobalName() {
    return globalName;
  }

  public ObservableList<RegisterMapping> getMappingList() {
    return mappingList;
  }

  public String getSourceCode() {
    return sourceCode.get();
  }

  public void bindSourceCode(StringProperty property) {
    sourceCode.bind(property);
  }
}

package hu.progtech.cd2t100.game.gui.emulator;

import java.util.Map;
import java.util.HashMap;
import static java.util.stream.Collectors.toMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import hu.progtech.cd2t100.game.model.NodeDescriptor;
import hu.progtech.cd2t100.game.model.RegisterDescriptor;

public class NodeMapping {
  private final String globalName;

  private final Map<String, RegisterMapping> registerMappings;

  private final ObservableList<RegisterMapping> mappingList;

  private NodeMapping(String globalName, Map<String, RegisterMapping> registerMappings) {
    this.globalName = globalName;

    this.registerMappings = registerMappings;

    mappingList = FXCollections.observableArrayList(registerMappings.values());
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

  public String getGlobalName() {
    return globalName;
  }

  public ObservableList<RegisterMapping> getMappingList() {
    return mappingList;
  }
}

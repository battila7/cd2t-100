package hu.progtech.cd2t100.game.gui.emulator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import static java.util.stream.Collectors.toMap;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Control;
import javafx.geometry.Insets;
import javafx.scene.layout.Priority;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.ObjectProperty;

import hu.progtech.cd2t100.emulator.EmulatorCycleData;

import hu.progtech.cd2t100.game.model.Puzzle;
import hu.progtech.cd2t100.game.model.NodeDescriptor;
import hu.progtech.cd2t100.computation.NodeMemento;

/**
 *  The {@code NodeController} is responsible for supplying node-related
 *  UI controls with data from the latest cycle and collecting the source
 *  of the nodes from the UI.
 */
public class NodeController {
  private final Puzzle puzzle;

  private final StringProperty selectedNodeName;

  private GridPane sourceCodeGridPane;

  private Tab nodeStatusTab;

  private TableView<RegisterMapping> registerTable;

  private final Map<String, NodeMapping>  nodeMappings;

  private final BooleanProperty editable;

  /**
   *  Constructs a new {@code NodeController} that builds the UI according to the
   *  specified {@code Puzzle} and listens to the changes of the passed cycle data.
   *
   *  @param puzzle the Puzzle
   *  @param emulatorCycleData an {@code ObjectProperty} wrapping {@code EmulatorCycleData}
   */
  public NodeController(Puzzle puzzle, ObjectProperty<EmulatorCycleData> emulatorCycleData) {
    this.puzzle = puzzle;

    selectedNodeName = new SimpleStringProperty();

    nodeMappings = new HashMap<>();

    editable = new SimpleBooleanProperty(true);

    emulatorCycleData.addListener(
      (observable, oldValue, newValue) -> refresh(newValue)
    );
  }

  /**
   *  Links the {@code NodeController} to the specified controls.
   *
   *  @param sourceCodeGridPane the grid pane the {@code TextArea}s will be placed on
   *  @param nodeStatusTab a tab the selected node's data will be displayed on
   *  @param registerTable a {@code TableView} for node register data
   */
  public void link(GridPane sourceCodeGridPane, Tab nodeStatusTab,
                   TableView<RegisterMapping> registerTable)
  {
    this.sourceCodeGridPane = sourceCodeGridPane;

    this.nodeStatusTab = nodeStatusTab;

    this.registerTable = registerTable;

    initGridPane();

    initStatusTab();

    for (NodeDescriptor descriptor : puzzle.getNodeDescriptors()) {
      nodeMappings.put(descriptor.getGlobalName(),
                       linkNode(descriptor));
    }

    selectedNodeName.addListener(
      (observable, oldValue, newValue) -> {
        changeStatusTab(nodeMappings.get(newValue));
      }
    );
  }

  /**
   *  Sets whether the {@code TextArea} storing the source codes
   *  should be editable.
   *
   *  @param editable whether the {@code TextArea}s should be editable
   */
  public void setEditable(boolean editable) {
    this.editable.set(editable);
  }

  private void initGridPane() {
    List<NodeDescriptor> nodes = puzzle.getNodeDescriptors();

    int gridRows = nodes.stream()
                        .mapToInt(NodeDescriptor::getRow)
                        .max().getAsInt(),
        gridCols = nodes.stream()
                        .mapToInt(NodeDescriptor::getColumn)
                        .max().getAsInt();

    for (int i = 0; i < gridRows; ++i) {
      sourceCodeGridPane.getRowConstraints().add(new RowConstraints(200));
    }

    for (int i = 0; i < gridCols; ++i) {
      sourceCodeGridPane.getColumnConstraints().add(new ColumnConstraints(200));
    }
  }

  private void initStatusTab() {
    TableColumn<RegisterMapping, String> nameCol = new TableColumn<>("Register");
    nameCol.setCellValueFactory(
            new PropertyValueFactory<RegisterMapping, String>("name"));

    TableColumn<RegisterMapping, Integer> capacityCol = new TableColumn<>("Capacity");
    capacityCol.setCellValueFactory(
            new PropertyValueFactory<RegisterMapping, Integer>("capacity"));

    TableColumn<RegisterMapping, String> valuesCol = new TableColumn<>("Values");
    valuesCol.setCellValueFactory(
            new PropertyValueFactory<RegisterMapping, String>("values"));

    registerTable.getColumns().addAll(nameCol, capacityCol, valuesCol);
  }

  private NodeMapping linkNode(NodeDescriptor descriptor) {
    VBox container = new VBox();

    Label nameLabel = new Label(descriptor.getGlobalName());
    nameLabel.setAlignment(Pos.CENTER);
    nameLabel.setTextAlignment(TextAlignment.CENTER);
    nameLabel.setMaxWidth(Double.MAX_VALUE);
    nameLabel.setMaxHeight(Control.USE_COMPUTED_SIZE);
    nameLabel.setPadding(new Insets(5.0, 0, 5.0, 0));

    container.getChildren().add(nameLabel);
    container.setVgrow(nameLabel, Priority.ALWAYS);

    TextArea codeArea = new TextArea();
    codeArea.setPrefWidth(200.0);
    codeArea.setPrefHeight(200.0);
    codeArea.setMaxWidth(Double.MAX_VALUE);
    codeArea.setMaxHeight(Double.MAX_VALUE);

    codeArea.setOnMouseClicked(evt -> selectedNodeName.set(descriptor.getGlobalName()));

    codeArea.editableProperty().bind(editable);

    container.getChildren().add(codeArea);
    container.setVgrow(codeArea, Priority.ALWAYS);

    sourceCodeGridPane.add(container, descriptor.getColumn() - 1, descriptor.getRow() - 1);

    NodeMapping nodeMapping = NodeMapping.fromNodeDescriptor(descriptor);

    nodeMapping.bindSourceCode(codeArea.textProperty());

    return nodeMapping;
  }

  private void changeStatusTab(NodeMapping mapping) {
    registerTable.setItems(mapping.getRegisterList());
  }

  private void refresh(EmulatorCycleData emulatorCycleData) {
    for (NodeMemento memento : emulatorCycleData.getNodeMementos().values()) {
      refreshNode(memento);
    }
  }

  private void refreshNode(NodeMemento memento) {
    NodeMapping node = nodeMappings.get(memento.getGlobalName());

    Map<String, int[]> registerValues = memento.getRegisterValues();

    for (RegisterMapping register : node.getRegisterList()) {
      String values = Arrays.toString(registerValues.get(register.getName()));

      register.setValues(values);
    }
  }

  /**
   *  Gets the source code collected from the {@code TextArea}s.
   *
   *  @return a map containing source codes mapped to the global names of the
   *          {@code Node}s
   */
  public Map<String, String> getSourceCodes() {
    return
      nodeMappings.entrySet()
                  .stream()
                  .collect(toMap(Map.Entry::getKey, e -> e.getValue().getSourceCode()));
  }
}

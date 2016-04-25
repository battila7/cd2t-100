package hu.progtech.cd2t100.computation;

import java.lang.reflect.Type;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.IOException;

import java.nio.file.Paths;

import java.util.Iterator;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import static org.assertj.core.api.Assertions.*;

import hu.progtech.cd2t100.asm.CodeFactory;
import hu.progtech.cd2t100.asm.CodeElementSet;
import hu.progtech.cd2t100.asm.InstructionElement;

import hu.progtech.cd2t100.formal.InstructionLoader;
import hu.progtech.cd2t100.formal.InstructionInfo;
import hu.progtech.cd2t100.formal.InvalidInstructionClassException;
import hu.progtech.cd2t100.formal.InvalidFormalParameterListException;

@RunWith(Parameterized.class)
public class ArgumentMatcherTest {
  private static String CODE_FILE = "computation/arg-matcher-test.json";

  private static int PARAMETER_COUNT = 6;

  @Parameter
  public String description;

  @Parameter(value = 1)
  public DummyNodeConfig nodeConfig;

  @Parameter(value = 2)
  public InstructionElement element;

  @Parameter(value = 3)
  public InstructionInfo info;

  @Parameter(value = 4)
  public List<Argument>  arguments;

  @Parameter(value = 5)
  public String exceptionMessage;

  @Parameters
  public static Collection<Object[]> data() {
    Gson gson = constructGson();

    InputStream is =
      ArgumentMatcherTest.class
                         .getClassLoader()
                         .getResourceAsStream(CODE_FILE);

    JsonArray root =
      gson.fromJson(new InputStreamReader(is), JsonElement.class)
          .getAsJsonArray();

    return extractTestData(gson, root);
  }

  @Test
  public void test() throws Exception {
    ArgumentMatcher argumentMatcher =
      new ArgumentMatcher(nodeConfig.getRegisterSet(),
                          nodeConfig.getReadablePortSet(),
                          nodeConfig.getWriteablePortSet());

    argumentMatcher.setLabels(nodeConfig.getLabelSet());

    argumentMatcher.setInstructionElement(element);

    argumentMatcher.setInstructionInfo(info);

    if (exceptionMessage != null) {
      assertThatThrownBy(() -> argumentMatcher.match())
        .as(description)
        .hasMessageContaining(exceptionMessage);
    } else {
      argumentMatcher.match();

      assertThat(argumentMatcher.getActualArguments())
        .as(description)
        .isEqualTo(arguments);
    }
  }

  private static InputStream getCodeStream(String resourceName) {
    File f = new File(resourceName);

    return ArgumentMatcherTest.class
                              .getClassLoader()
                              .getResourceAsStream(f.getPath());
  }

  private static Gson constructGson() {
    return new GsonBuilder()
      .registerTypeAdapter(DummyNodeConfig.class,
                           new DummyNodeConfigDeserializer())
      .registerTypeAdapter(Argument.class,
                           new ArgumentInstanceCreator())
      .create();
  }

  private static List<Object[]> extractTestData(Gson gson, JsonArray root) {
    ArrayList<Object[]> testData = new ArrayList<>();

    Iterator<JsonElement> it = root.iterator();

    Type argListType =
      new TypeToken<ArrayList<Argument>>() {}.getType();

    while (it.hasNext()) {
      JsonObject testCase = it.next().getAsJsonObject();

      Object[] params = new Object[PARAMETER_COUNT];

      params[0] = gson.fromJson(testCase.get("description"), String.class);

      DummyNodeConfig nodeConfig
        = gson.fromJson(testCase.get("node"), DummyNodeConfig.class);

      params[1] = nodeConfig;

      HashSet<String> allPorts = new HashSet<>(nodeConfig.getReadablePortSet());
      allPorts.addAll(nodeConfig.getWriteablePortSet());

      String instruction
        = gson.fromJson(testCase.get("instruction"), String.class);

      CodeElementSet elementSet =
        CodeFactory.createCodeElementSet(nodeConfig.getRegisterSet(),
                                        allPorts, instruction);

      params[2] = elementSet.getInstructionList().get(0);

      String groovyResource = gson.fromJson(testCase.get("groovy"), String.class);

      try {
        params[3] = InstructionLoader.loadInstruction(getCodeStream(groovyResource));
      } catch (IOException | InvalidInstructionClassException |
               InvalidFormalParameterListException e) {
        continue;
      }

      params[4] = gson.fromJson(testCase.get("arguments"), argListType);

      if (testCase.has("exceptionMessage")) {
        params[5] = gson.fromJson(testCase.get("exceptionMessage"), String.class);
      } else {
        params[5] = null;
      }

      testData.add(params);
    }

    return testData;
  }
}

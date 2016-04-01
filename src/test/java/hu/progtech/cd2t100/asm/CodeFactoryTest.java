package hu.progtech.cd2t100.asm;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.assertTrue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;

@RunWith(Parameterized.class)
public class CodeFactoryTest {
  private static String CODE_FILE = "test-code.json";

  private static int PARAMETER_COUNT = 5;

  @Parameter
  public String inputDescription;

  @Parameter(value = 1)
  public String inputSourceCode;

  @Parameter(value = 2)
  public Set<String> inputRegisterNameSet;

  @Parameter(value = 3)
  public Set<String> inputPortNameSet;

  @Parameter(value = 4)
  public ExpectedElementSet inputExpectedElementSet;

  @Parameters
  public static Collection<Object[]> data() {
    Gson gson = constructGson();

    InputStream is =
      CodeFactoryTest.class.getClassLoader().getResourceAsStream(CODE_FILE);

    JsonArray root =
      gson.fromJson(new InputStreamReader(is), JsonElement.class)
          .getAsJsonArray();

    return extractTestData(gson, root);
  }

  @Test
  public void test() {
    CodeElementSet elementSet =
      CodeFactory.createCodeElementSet(
          inputRegisterNameSet,
          inputPortNameSet,
          inputSourceCode);

    assertTrue(inputDescription,
               inputExpectedElementSet.compareToElementSet(elementSet));
  }

  private static Gson constructGson() {
    return new GsonBuilder()
      .registerTypeAdapter(ExpectedElementSet.class,
                           new ExpectedElementSetDeserializer())
      .registerTypeAdapter(Location.class,
                           new LocationInstanceCreator())
      .registerTypeAdapter(ArgumentElement.class,
                           new ArgumentElementInstanceCreator())
      .registerTypeAdapter(InstructionElement.class,
                           new InstructionElementInstanceCreator())
      .create();
  }

  private static List<Object[]> extractTestData(Gson gson, JsonArray root) {
    ArrayList<Object[]> testData = new ArrayList<>();

    Iterator<JsonElement> it = root.iterator();

    Type stringHashSet = new TypeToken<HashSet<String>>() {}.getType();

    while (it.hasNext()) {
      JsonObject testCase = it.next().getAsJsonObject();

      Object[] params = new Object[PARAMETER_COUNT];

      params[0] = gson.fromJson(testCase.get("description"), String.class);

      String[] codeListing = gson.fromJson(testCase.get("code"), String[].class);
      params[1] = StringUtils.join(codeListing, "\n");

      params[2] = gson.fromJson(testCase.get("registers"), stringHashSet);

      params[3] = gson.fromJson(testCase.get("ports"), stringHashSet);

      params[4] = gson.fromJson(testCase.get("expected"),
                                ExpectedElementSet.class);

      testData.add(params);
    }

    return testData;
  }
}

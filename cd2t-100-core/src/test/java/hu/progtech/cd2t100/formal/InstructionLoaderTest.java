package hu.progtech.cd2t100.formal;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;

import java.nio.file.Paths;

import java.util.Iterator;
import java.util.List;
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

import static org.assertj.core.api.Assertions.*;

@RunWith(Parameterized.class)
public class InstructionLoaderTest {
  private static String CODE_FILE = Paths.get("formal", "formal-test.json")
                                         .toString();

  private static int PARAMETER_COUNT = 3;

  @Parameter
  public String description;

  @Parameter(value = 1)
  public String groovyResource;

  @Parameter(value = 2)
  public ExpectedInstructionInfo expectedInfo;

  @Parameters
  public static Collection<Object[]> data() {
    Gson gson = constructGson();

    InputStream is =
      InstructionLoaderTest.class
                           .getClassLoader()
                           .getResourceAsStream(CODE_FILE);

    JsonArray root =
      gson.fromJson(new InputStreamReader(is), JsonElement.class)
          .getAsJsonArray();

    return extractTestData(gson, root);
  }

  @Test
  public void test() throws Exception {
    String exceptionClassName = expectedInfo.getThrownExceptionName();

    if (exceptionClassName != null) {
      Class<?> exceptionClass = Class.forName(exceptionClassName);

      assertThatThrownBy(() -> InstructionLoader.loadInstruction(getCodeStream(groovyResource)))
                        .as(description)
                        .isInstanceOf(exceptionClass)
                        .hasMessageContaining(expectedInfo.getMessageFragment());
    } else {
      InstructionInfo info =
        InstructionLoader.loadInstruction(getCodeStream(groovyResource));

      assertThat(expectedInfo.compareToInstructionInfo(info))
                .as(description)
                .isTrue();
    }
  }

  private static InputStream getCodeStream(String resourceName) {
    File f = new File(resourceName);

    return InstructionLoaderTest.class
                                .getClassLoader()
                                .getResourceAsStream(f.getPath());
  }

  private static Gson constructGson() {
    return new GsonBuilder()
      .registerTypeAdapter(ExpectedInstructionInfo.class,
                           new ExpectedInstructionInfoDeserializer())
      .registerTypeAdapter(FormalCall.class,
                           new FormalCallDeserializer())
      .registerTypeAdapter(FormalParameter.class,
                           new FormalParameterInstanceCreator())
      .create();
  }

  private static List<Object[]> extractTestData(Gson gson, JsonArray root) {
    ArrayList<Object[]> testData = new ArrayList<>();

    Iterator<JsonElement> it = root.iterator();

    while (it.hasNext()) {
      JsonObject testCase = it.next().getAsJsonObject();

      Object[] params = new Object[PARAMETER_COUNT];

      params[0] = gson.fromJson(testCase.get("description"), String.class);

      params[1] = gson.fromJson(testCase.get("groovy"), String.class);

      params[2] = gson.fromJson(testCase.get("expected"), ExpectedInstructionInfo.class);

      testData.add(params);
    }

    return testData;
  }
}

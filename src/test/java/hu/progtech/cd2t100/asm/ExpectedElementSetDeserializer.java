package hu.progtech.cd2t100.asm;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.ArrayList;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

class ExpectedElementSetDeserializer implements
  JsonDeserializer<ExpectedElementSet> {

  public ExpectedElementSet deserialize(JsonElement json,
                                 Type typeOfT,
                                 JsonDeserializationContext context)
                                 throws JsonParseException {
    JsonObject jObject = json.getAsJsonObject();

    Type ruleMapType =
      new TypeToken<HashMap<String, String>>() {}.getType();
    Type labelMapType =
      new TypeToken<HashMap<String, Integer>>() {}.getType();
    Type instructionListType =
      new TypeToken<ArrayList<InstructionElement>>() {}.getType();
    Type exceptionListType =
      new TypeToken<ArrayList<ExceptionDescriptor>>() {}.getType();

    ArrayList<ExceptionDescriptor> exceptionList =
      context.deserialize(jObject.get("exceptions"), exceptionListType);

    ArrayList<InstructionElement> instructionList =
      context.deserialize(jObject.get("instructions"), instructionListType);

    HashMap<String, Integer> labelMap =
      context.deserialize(jObject.get("labels"), labelMapType);

    HashMap<String, String> ruleMap =
      context.deserialize(jObject.get("rules"), ruleMapType);

    return new ExpectedElementSet(exceptionList, instructionList,
                                  labelMap, ruleMap);
  }

}

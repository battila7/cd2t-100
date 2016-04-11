package hu.progtech.cd2t100.formal;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.ArrayList;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

class ExpectedInstructionInfoDeserializer implements
  JsonDeserializer<ExpectedInstructionInfo> {

  public ExpectedInstructionInfo deserialize(JsonElement json,
                                 Type typeOfT,
                                 JsonDeserializationContext context)
                                 throws JsonParseException {
    JsonObject jObject = json.getAsJsonObject();

    Type usedRuleListType =
      new TypeToken<ArrayList<String>>() {}.getType();
    Type formalCallListType =
      new TypeToken<ArrayList<FormalCall>>() {}.getType();

    String opcode = context.deserialize(jObject.get("opcode"), String.class);

    String thrownExceptionName = null,
           messageFragment = "";

    if (jObject.has("exception")) {
      thrownExceptionName = context.deserialize(jObject.get("exception"), String.class);

      if (jObject.has("exceptionMessage")) {
        messageFragment = context.deserialize(jObject.get("exceptionMessage"), String.class);
      }
    }

    ArrayList<String> ruleList =
      context.deserialize(jObject.get("rules"), usedRuleListType);

    ArrayList<FormalCall> possibleCalls =
      context.deserialize(jObject.get("calls"), formalCallListType);

    return new ExpectedInstructionInfo(opcode, ruleList, possibleCalls,
                                       thrownExceptionName, messageFragment);
  }

}

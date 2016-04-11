package hu.progtech.cd2t100.formal;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

class FormalCallDeserializer implements
  JsonDeserializer<FormalCall> {

  public FormalCall deserialize(JsonElement json,
                                 Type typeOfT,
                                 JsonDeserializationContext context)
                                 throws JsonParseException {
    JsonObject jObject = json.getAsJsonObject();

    Type paramListType =
      new TypeToken<ArrayList<FormalParameter>>() {}.getType();

    ArrayList<FormalParameter> paramList =
      context.deserialize(jObject.get("params"), paramListType);

    return new FormalCall(paramList, null);
  }

}

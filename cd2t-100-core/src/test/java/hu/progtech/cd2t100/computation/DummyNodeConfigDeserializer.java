package hu.progtech.cd2t100.computation;

import java.lang.reflect.Type;
import java.util.HashSet;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

class DummyNodeConfigDeserializer implements
  JsonDeserializer<DummyNodeConfig> {

  public DummyNodeConfig deserialize(JsonElement json,
                                     Type typeOfT,
                                     JsonDeserializationContext context)
                                 throws JsonParseException {
    JsonObject jObject = json.getAsJsonObject();

    Type stringSetType =
      new TypeToken<HashSet<String>>() {}.getType();

    return new DummyNodeConfig(
      context.deserialize(jObject.get("registers"), stringSetType),
      context.deserialize(jObject.get("readablePorts"), stringSetType),
      context.deserialize(jObject.get("writeablePorts"), stringSetType),
      context.deserialize(jObject.get("labels"), stringSetType));
  }

}

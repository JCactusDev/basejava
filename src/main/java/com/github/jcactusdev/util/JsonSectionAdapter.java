package com.github.jcactusdev.util;

import com.github.jcactusdev.model.Section;
import com.google.gson.*;

import java.lang.reflect.Type;

public class JsonSectionAdapter implements JsonSerializer<Section>, JsonDeserializer<Section> {
    private static final String CLASSNAME = "CLASSNAME";
    private static final String INSTANCE = "INSTANCE";

    @Override
    public Section deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = prim.getAsString();

        try {
            Class clazz = Class.forName(className);
            return context.deserialize(jsonObject.get(INSTANCE), clazz);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    @Override
    public JsonElement serialize(Section section, Type type, JsonSerializationContext context) {
        JsonObject retValue = new JsonObject();
        retValue.addProperty(CLASSNAME, section.getClass().getName());
        JsonElement elem = context.serialize(section);
        retValue.add(INSTANCE, elem);
        return retValue;
    }
}

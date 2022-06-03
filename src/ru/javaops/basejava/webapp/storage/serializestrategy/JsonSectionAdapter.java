package ru.javaops.basejava.webapp.storage.serializestrategy;

import com.google.gson.JsonSerializer;
import com.google.gson.*;
import ru.javaops.basejava.webapp.exception.StorageException;

import java.lang.reflect.Type;

public class JsonSectionAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    private static final String CLASSNAME = "CLASSNAME";
    private static final String INSTANCE = "INSTANCE";

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive primitive = jsonObject.getAsJsonPrimitive(CLASSNAME);
        String className = primitive.getAsString();
        try {
            Class clazz = Class.forName(className);
            return context.deserialize(jsonObject.getAsJsonObject(INSTANCE), clazz);
        } catch (ClassNotFoundException e) {
            throw new StorageException("JSON deserialization error, can't find Resume class", null, e);
        }
    }

    @Override
    public JsonElement serialize(T section, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CLASSNAME, section.getClass().getName());
        JsonElement jsonElement = context.serialize(section);
        jsonObject.add(INSTANCE, jsonElement);
        return jsonObject;
    }
}

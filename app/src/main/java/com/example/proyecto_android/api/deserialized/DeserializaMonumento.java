package com.example.proyecto_android.api.deserialized;

import com.example.proyecto_android.model.City;
import com.example.proyecto_android.model.Monumento;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class DeserializaMonumento implements JsonDeserializer<Monumento> {
    @Override
    public Monumento deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int id = json.getAsJsonObject().get("idnotes").getAsInt();

        Monumento monumento = new Monumento(id);
        return monumento;
    }
}

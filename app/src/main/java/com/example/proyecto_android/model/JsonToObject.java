package com.example.proyecto_android.model;

import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonToObject {

    private static final String MONUMENTOS_FEATURES = "features";
    private static final String MONUMENTOS_PROPERTIES = "properties";
    private static final String MONUMENTOS_GEOMETRY = "geometry";
    private static final String MONUMENTOS_NOMBRE = "nombre";
    private static final String MONUMENTOS_NUMPOL = "numpol";
    private static final String MONUMENTOS_IDNOTES = "idnotes";
    private static final String MONUMENTOS_CODVIA = "codvia";
    private static final String MONUMENTOS_TELEFONO = "telefono";
    private static final String MONUMENTOS_RUTA = "ruta";
    private static final String MONUMENTOS_COORDINATES = "coordinates";
    private static final String MONUMENTOS_IMAGES = "images";
    private static final String MONUMENTOS_IMG = "img";

    public static Monumento jsonToMonument(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        Monumento monumento = new Monumento();

        if(object.has(MONUMENTOS_PROPERTIES)){
            JSONObject properties = object.getJSONObject(MONUMENTOS_PROPERTIES);
            if (properties.has(MONUMENTOS_NOMBRE)){
                String name = properties.getString(MONUMENTOS_NOMBRE);
                name = name.replace("'", "*");
                monumento.setName(name);
            }
            if (properties.has(MONUMENTOS_NUMPOL)){
                monumento.setNumPol(properties.getString(MONUMENTOS_NUMPOL));
            }
            if (properties.has(MONUMENTOS_IDNOTES)){
                monumento.setIdNotes(Integer.parseInt(properties.getString(MONUMENTOS_IDNOTES)));
            }
            if (properties.has(MONUMENTOS_CODVIA)){
                monumento.setCodVia(Integer.parseInt(properties.getString(MONUMENTOS_CODVIA)));
            }
            if (properties.has(MONUMENTOS_TELEFONO)){
                monumento.setTelefono(properties.getString(MONUMENTOS_TELEFONO));
            }
            if (properties.has(MONUMENTOS_RUTA)){
                monumento.setRuta(Integer.parseInt(properties.getString(MONUMENTOS_RUTA)));
            }
            if (properties.has(MONUMENTOS_IMG)){
                //monumento.setImagen(properties.getString(MONUMENTOS_IMG));
            }
        }
        if (object.has((MONUMENTOS_GEOMETRY))){
            JSONObject geometry = object.getJSONObject(MONUMENTOS_GEOMETRY);
            if (geometry.has(MONUMENTOS_COORDINATES)){
                JSONArray coordinates = geometry.getJSONArray(MONUMENTOS_COORDINATES);
                //monumento.setLatitud(coordinates.getLong(0));
                //monumento.setLongitud(coordinates.getLong(1));
            }
        }
        return monumento;
    }

    public static ArrayList<Monumento> jsonToMonumetList(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        ArrayList<Monumento> monumentos = new ArrayList<>();
        if(object.has(MONUMENTOS_FEATURES)){
            //creo un array de features
            JSONArray features = object.getJSONArray(MONUMENTOS_FEATURES);
            for (int i=0; i<features.length(); i++){
                JSONObject monumento = (JSONObject) features.get(i);
                Monumento m = jsonToMonument(monumento.toString());
                monumentos.add(m);
            }
        }
        return monumentos;
    }

    public static ArrayList<Monumento> jsonToImageMonumentsList(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        ArrayList<Monumento> monumentos = new ArrayList<>();
        if (object.has(MONUMENTOS_IMAGES)){
            JSONArray images = object.getJSONArray(MONUMENTOS_IMAGES);
            for (int i=0; i<images.length(); i++){
                JSONObject monumento = (JSONObject) images.get(i);
                Monumento m = jsonToMonument(monumento.toString());
                monumentos.add(m);
            }
        }
        return monumentos;
    }

}

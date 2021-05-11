package com.example.proyecto_android.api.tiempo;

import com.example.proyecto_android.api.deserialized.MyDeserializer;
import com.example.proyecto_android.model.City;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClima {

    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String BASE_ICONS = "http://openweathermap.org/img/w/";
    public static final String EXTENSION_ICONS = ".png";

    private static Retrofit retrofit = null;

    public static final String APPKEY = "f7b92b81412e33b76dab58a3cfc436de";

    public static Retrofit getApi(){
        if (retrofit == null){
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(City.class, new MyDeserializer());

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(builder.create()))
                    .build();
        }
        return retrofit;
    }

}

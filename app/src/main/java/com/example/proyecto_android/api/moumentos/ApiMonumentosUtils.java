package com.example.proyecto_android.api.moumentos;

import com.example.proyecto_android.api.deserialized.DeserializaMonumento;
import com.example.proyecto_android.model.Monumento;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiMonumentosUtils {

    private static final String URL = "http://rpicrespuig.ddns.net:81/";
    //private static final String URL = "http://192.168.0.19:8081/";

    private static ApiMonumetosService apiMonumetosService;

    public static ApiMonumetosService getClient() {
        if(apiMonumetosService == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Monumento.class, new DeserializaMonumento());

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
            apiMonumetosService = retrofit.create(ApiMonumetosService.class);
        }
        return apiMonumetosService;
    }

}

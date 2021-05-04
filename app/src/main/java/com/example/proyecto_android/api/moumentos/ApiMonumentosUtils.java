package com.example.proyecto_android.api.moumentos;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiMonumentosUtils {

    private static final String URL = "http://192.168.0.19:8081/";

    private static ApiMonumetosService apiMonumetosService;

    public static ApiMonumetosService getClient() {
        if(apiMonumetosService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiMonumetosService = retrofit.create(ApiMonumetosService.class);
        }
        return apiMonumetosService;
    }

}

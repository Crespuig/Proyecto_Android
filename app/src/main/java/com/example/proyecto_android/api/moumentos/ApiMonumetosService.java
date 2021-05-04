package com.example.proyecto_android.api.moumentos;

import com.example.proyecto_android.model.Monumento;
import com.example.proyecto_android.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiMonumetosService {

    @GET("monumentos")
    Call<List<Monumento>> getMonumentos();

    @POST("usuarios/login")
    Call<Usuario> login(@Body Usuario usuario);
}

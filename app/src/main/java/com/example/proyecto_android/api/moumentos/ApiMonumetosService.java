package com.example.proyecto_android.api.moumentos;

import com.example.proyecto_android.model.Monumento;
import com.example.proyecto_android.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiMonumetosService {

    @GET("monumentos")
    Call<List<Monumento>> getMonumentos();

    @POST("usuarios/login")
    Call<Usuario> login(@Body Usuario usuario);

    @POST("usuarios/registro")
    Call<Usuario> registrar(@Body Usuario usuario);

    @GET("monumentos/{id}")
    Monumento oneMonumment(@Query("idnotes") int id);

    @GET("usuarios")
    Call<List<Usuario>> getUsuarios();
}

package com.example.proyecto_android.api.moumentos;

import com.example.proyecto_android.model.Favorito;
import com.example.proyecto_android.model.Monumento;
import com.example.proyecto_android.model.Usuario;
import com.google.gson.annotations.Expose;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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
    Monumento oneMonumment(@Path("idnotes") int id);

    @GET("usuarios")
    Call<List<Usuario>> getUsuarios();

    @GET("favoritos/usuario/{idUsuario}")
    Call<List<Favorito>> getFavoritos(@Path("idUsuario") int idUsuario);

    @POST("favoritos")
    Call<Favorito> anyadirFavoritoUsuario(@Body Favorito favorito);

    @DELETE("favoritos/{idMonumento}/usuario/{idUsuario}")
    Call<Void> borrarFavorito(@Path("idMonumento") int idMonumento, @Path("idUsuario") int idUsuario);
}

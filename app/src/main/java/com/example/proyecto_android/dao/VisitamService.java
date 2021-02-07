package com.example.proyecto_android.dao;

import com.example.proyecto_android.model.Monumento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VisitamService {

    @GET("lanzadera/opendata/Monumentos-turisticos/JSON")
    Call<List<Monumento>> listRepos(@Path("user") String user);

}

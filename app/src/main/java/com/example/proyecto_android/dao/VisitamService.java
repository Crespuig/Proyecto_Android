package com.example.proyecto_android.dao;

import com.example.proyecto_android.model.Monumento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VisitamService {

    @GET("{idnotes}/Monumentos-turisticos/JSON")
    Call<Monumento> getMonumento(@Path("idnotes") int idNotes);

}

package com.example.proyecto_android.api.tiempo;

import com.example.proyecto_android.model.City;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    //weather?q=Valencia,es&appid=f7b92b81412e33b76dab58a3cfc436de
    @GET("weather")
    Call<City> getCity(@Query("q") String city, @Query("appid") String key);

    @GET("weather")
    Call<City> getCity(@Query("q") String city, @Query("appid") String key, @Query("units") String value);

    @GET("weather")
    Call<City> getCity(@Query("q") String city, @Query("appid") String key, @Query("units") String value, @Query("lang") String lang);
}

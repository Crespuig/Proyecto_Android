package com.example.proyecto_android.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_android.R;
import com.example.proyecto_android.api.tiempo.ApiClima;
import com.example.proyecto_android.api.tiempo.WeatherService;
import com.example.proyecto_android.model.City;
import com.google.android.gms.common.api.Api;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClimaFragment extends Fragment {

    private EditText editTextSearch;
    private TextView textViewCity;
    private TextView textViewDescription;
    private TextView textViewTemp;
    private ImageView img;
    private Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clima, container, false);

        textViewCity = (TextView) view.findViewById(R.id.textViewCity);
        textViewDescription = (TextView) view.findViewById(R.id.textViewDescription);
        textViewTemp = (TextView) view.findViewById(R.id.textViewTemp);
        img = (ImageView) view.findViewById(R.id.imgClima);

        WeatherService service = ApiClima.getApi().create(WeatherService.class);
        
        Call<City> cityCall = service.getCity("Valencia,ES", ApiClima.APPKEY, "metric", "es");
        
        cityCall.enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                //objeto de tipo city
                City city = response.body();
                setResult(city);
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void setResult(City city){
        textViewCity.setText(city.getName());
        textViewDescription.setText(city.getDescription());
        textViewTemp.setText(city.getTemperature() + "ºC");
        Picasso.with(getContext()).load(ApiClima.BASE_ICONS + city.getIcon() + ApiClima.EXTENSION_ICONS).into(img);
    }
}
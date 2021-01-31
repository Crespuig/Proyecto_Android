package com.example.proyecto_android.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyecto_android.R;
import com.example.proyecto_android.activitys.LoginActivity;
import com.example.proyecto_android.activitys.MainActivity;
import com.example.proyecto_android.activitys.MostrarMonumentoActivity;
import com.example.proyecto_android.model.MapaViewModel;
import com.example.proyecto_android.model.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MapaFragment extends Fragment  {

    private MapaViewModel slideshowViewModel;
    MapaFragment context = this;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);

        Button btnMostrar = (Button) view.findViewById(R.id.btnMapa);
        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MostrarMonumentoActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
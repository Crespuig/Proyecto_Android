package com.example.proyecto_android.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.proyecto_android.R;

public class InicioFragment extends Fragment /*implements AdapterView.OnItemClickListener*/ {

    private ImageButton btnLista;
    ConstraintLayout constraintLayout;
    ListaFragment listaFragment;

    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);

       /* constraintLayout = (ConstraintLayout) view.findViewById(R.id.fragmentInicio);
        btnLista = view.findViewById(R.id.btnLista);
        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiaFragment(R.id.contenedor, new ListaFragment());
                constraintLayout.setVisibility(View.GONE);
            }
        });*/


        return view;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       return true;
    }

}
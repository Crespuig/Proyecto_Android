package com.example.proyecto_android.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyecto_android.R;
import com.example.proyecto_android.adapters.ListaMonumentosAdapter;
import com.example.proyecto_android.model.InicioViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class InicioFragment extends Fragment /*implements AdapterView.OnItemClickListener*/ {

    private InicioViewModel homeViewModel;
    private ImageButton btnLista;
    ConstraintLayout constraintLayout;
    ListaFragment listaFragment;

    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(InicioViewModel.class);
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);

        constraintLayout = (ConstraintLayout) view.findViewById(R.id.fragmentInicio);
        btnLista = view.findViewById(R.id.btnLista);
        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiaFragment(R.id.contenedor, new ListaFragment());
                constraintLayout.setVisibility(View.GONE);
            }
        });


        return view;
    }

    public void cambiaFragment(int id, Fragment fragment) {
        //Sustituye un fragment por otro
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(id, fragment)
                //.addToBackStack(fragment.getTag())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FragmentTransaction transaction = null;
        switch (item.getItemId()){
            case R.id.btnLista:
                transaction.replace(R.id.contenedor, listaFragment);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
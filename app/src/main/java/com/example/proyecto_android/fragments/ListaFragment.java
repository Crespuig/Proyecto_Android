package com.example.proyecto_android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyecto_android.R;
import com.example.proyecto_android.model.ListaViewModel;

public class ListaFragment extends Fragment {

    private ListaViewModel listaViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listaViewModel = ViewModelProviders.of(this).get(ListaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_lista, container, false);

        return root;
    }
}
package com.example.proyecto_android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_android.R;
import com.example.proyecto_android.activitys.MainActivity;
import com.example.proyecto_android.adapters.ListaMonumentosAdapter;
import com.example.proyecto_android.bbdd.MiAppOperacional;
import com.example.proyecto_android.bbdd.MiBD;
import com.example.proyecto_android.dao.VisitamService;
import com.example.proyecto_android.dialogos.DialogoListaMonumentos;
import com.example.proyecto_android.model.ListaViewModel;
import com.example.proyecto_android.model.Monumento;
import com.example.proyecto_android.model.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ListaFragment extends Fragment {
    private ListView listaMonumentos;
    private MiAppOperacional mappo;
    ListaFragment context = this;
    private ListaViewModel listaViewModel;

    private List<Monumento> monumentos;
    VisitamService service;

    private RecyclerView mRecyclerView;
    // Puede ser declarado como 'RecyclerView.Adapter' o como la clase adaptador 'ListaMonumentosAdapter'
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_lista, container, false);
        monumentos = this.getAllMonumentos();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(context.getContext());


       // getMonumentos();
        //mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Usuario u = ((MainActivity)context.getActivity()).getUsuario();
        mAdapter = new ListaMonumentosAdapter(monumentos, u,  R.layout.recycler_view_item, new ListaMonumentosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Monumento monumento, int position) {
                Toast.makeText(context.getContext(), monumento + "-" + position, Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        listaViewModel = ViewModelProviders.of(this).get(ListaViewModel.class);


        FloatingActionButton btnCalcularRuta = view.findViewById(R.id.btnCalcularRuta);
        btnCalcularRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        return view;
    }

    private void getMonumentos(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://mapas.valencia.es/").
                addConverterFactory(GsonConverterFactory.create(gson)).
                build();

        service = retrofit.create(VisitamService.class);
        Call<List<Monumento>> call = service.getMonumentos();
        call.enqueue(new Callback<List<Monumento>>() {
            @Override
            public void onResponse(Call<List<Monumento>> call, Response<List<Monumento>> response) {
                if (!response.isSuccessful()){
                    Log.i("******************", response.toString());
                    return;
                }

                List<Monumento> monumentos = response.body();

                /*mAdapter = new ListaMonumentosAdapter(monumentos, R.layout.recycler_view_item, new ListaMonumentosAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Monumento monumento, int position) {
                        //Toast.makeText(context.getContext(), monumento + "-" + position, Toast.LENGTH_SHORT).show();
                    }
                });*/
            }

            @Override
            public void onFailure(Call<List<Monumento>> call, Throwable t) {
                Log.i("******************", t.toString());
            }
        });
    }

    private List<Monumento> getAllMonumentos(){
        return MiBD.getInstance(getContext()).recuperarMonumentos();
    }

    public void mostrarDetalle(/*Monumento m*/){


    }
}
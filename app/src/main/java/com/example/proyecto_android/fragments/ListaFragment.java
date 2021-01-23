package com.example.proyecto_android.fragments;

import android.os.Bundle;
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
import com.example.proyecto_android.adapters.ListaMonumentosAdapter;
import com.example.proyecto_android.bbdd.MiAppOperacional;
import com.example.proyecto_android.dialogos.DialogoListaMonumentos;
import com.example.proyecto_android.model.ListaViewModel;
import com.example.proyecto_android.model.Monumento;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;



public class ListaFragment extends Fragment {
    private ListView listaMonumentos;
    private MiAppOperacional mappo;
    ListaFragment context = this;
    private ListaViewModel listaViewModel;

    private List<Monumento> monumentos;

    private RecyclerView mRecyclerView;
    // Puede ser declarado como 'RecyclerView.Adapter' o como la clase adaptador 'ListaMonumentosAdapter'
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_lista, container, false);
        monumentos = this.getAllMonumentos();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(context.getContext());
        mAdapter = new ListaMonumentosAdapter(monumentos, R.layout.recycler_view_item, new ListaMonumentosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Monumento monumento, int position) {
                //Toast.makeText(context.getContext(), monumento + "-" + position, Toast.LENGTH_SHORT).show();
            }
        });

        //mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.setItemAnimator(new DefaultItemAnimator());

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

    private List<Monumento> getAllMonumentos(){
        return new ArrayList<Monumento>(){{
            add(new Monumento("Ciutat de les arts i les ciències", R.drawable.ciutatdelesarts));
            add(new Monumento("Catedral de València", R.drawable.catedral_valencia));
            add(new Monumento("Iglesia Santos Juanes", R.drawable.iglesia_santos_juanes));
            add(new Monumento("Lonja Seda", R.drawable.lonja_seda));
            add(new Monumento("Mercado Central", R.drawable.mercado_central));
            add(new Monumento("Torre de Serrà", R.drawable.torre_serranos));
        }};
    }

    public void mostrarDetalle(/*Monumento m*/){


    }
}
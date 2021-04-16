package com.example.proyecto_android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_android.R;
import com.example.proyecto_android.activitys.MainActivity;
import com.example.proyecto_android.adapters.ListaMonumentosAdapter;
import com.example.proyecto_android.model.Monumento;
import com.example.proyecto_android.model.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class ListaFragment extends Fragment implements SearchView.OnQueryTextListener {

    private SearchView searchView;

    private List<Monumento> monumentos;

    private RecyclerView mRecyclerView;
    // Puede ser declarado como 'RecyclerView.Adapter' o como la clase adaptador 'ListaMonumentosAdapter'
    private ListaMonumentosAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_lista, container, false);
        monumentos = this.getMonumentos();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getContext());

        searchView = (SearchView) view.findViewById(R.id.searchViewLista);
        initListener();

        Usuario u = ((MainActivity) getActivity()).getUsuario();
        mAdapter = new ListaMonumentosAdapter(monumentos, u,  R.layout.recycler_view_item, new ListaMonumentosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Monumento monumento, int position) {
                Toast.makeText(getContext(), monumento + "-" + position, Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

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

    //TODO: conectar a api y obtener lista mmonumentos
    private List<Monumento> getMonumentos(){
        //RETROFIT GET
      return null;
    }

    private void initListener(){
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        mAdapter.filter(s);
        return false;
    }
}
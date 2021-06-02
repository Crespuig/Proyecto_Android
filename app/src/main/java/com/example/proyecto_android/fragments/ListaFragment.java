package com.example.proyecto_android.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_android.R;
import com.example.proyecto_android.activitys.LoginActivity;
import com.example.proyecto_android.activitys.MainActivity;
import com.example.proyecto_android.adapters.ListaMonumentosAdapter;
import com.example.proyecto_android.api.moumentos.ApiMonumentosUtils;
import com.example.proyecto_android.api.moumentos.ApiMonumetosService;
import com.example.proyecto_android.model.ComunicaFragments;
import com.example.proyecto_android.model.Monumento;
import com.example.proyecto_android.model.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListaFragment extends Fragment implements SearchView.OnQueryTextListener {

    private SearchView searchView;
    private ApiMonumetosService apiMonumetosService;
    private RecyclerView mRecyclerView;
    // Puede ser declarado como 'RecyclerView.Adapter' o como la clase adaptador 'ListaMonumentosAdapter'
    private ListaMonumentosAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //Referencias para comunicar fragments
    Activity activity;
    ComunicaFragments interfaceComunicaFragments;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_lista, container, false);

        apiMonumetosService = ApiMonumentosUtils.getClient();
        getMonumentos();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getContext());

        Usuario u = ((MainActivity) getActivity()).getUsuario();
        List<Monumento> m = ((MainActivity) getActivity()).getMonumentos();
        mAdapter = new ListaMonumentosAdapter(m, u,  R.layout.recycler_view_item, new ListaMonumentosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Monumento monumento, int position) {
                /*new AlertDialog.Builder(getContext())
                        .setTitle(monumento.getName())
                        .setMessage("CodVia - " + monumento.getCodVia()
                                + "\nRuta - " + monumento.getRuta()
                                + "\nTeléfono - " + monumento.getTelefono())
                        .show();*/

                //interfaceComunicaFragments.enviarMonumento(m.get(mRecyclerView.getChildAdapterPosition(view)));

                /*DetalleMonumentoFragment detalleMonumentoFragment = new DetalleMonumentoFragment();
                FragmentTransaction transaction = getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.nav_host_fragment, detalleMonumentoFragment).commit();*/

            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        searchView = (SearchView) view.findViewById(R.id.searchViewLista);
        initListener();

        /*FloatingActionButton btnCalcularRuta = view.findViewById(R.id.btnCalcularRuta);
        btnCalcularRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        return view;
    }

    //TODO: conectar a api y obtener lista mmonumentos
    private void getMonumentos(){
                //TODO: consultar fav user
                //apiMonumetosService.getFavoritosUsuario().enqueue(new Callback<List<Monumento>>()
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            this.activity = (Activity) context;
            interfaceComunicaFragments = (ComunicaFragments) this.activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
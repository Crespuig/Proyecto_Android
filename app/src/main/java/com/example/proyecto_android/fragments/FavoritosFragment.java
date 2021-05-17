package com.example.proyecto_android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_android.R;
import com.example.proyecto_android.activitys.MainActivity;
import com.example.proyecto_android.adapters.FavoritosAdapter;
import com.example.proyecto_android.model.Favorito;
import com.example.proyecto_android.model.Monumento;
import com.example.proyecto_android.model.Usuario;

import java.util.ArrayList;
import java.util.List;


public class FavoritosFragment extends Fragment {

    private FavoritosAdapter favoritosAdapter;
    private RecyclerView fRecyclerView;
    private RecyclerView.LayoutManager fLayoutManager;

    public FavoritosFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);

        fRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewFav);
        fLayoutManager = new LinearLayoutManager(getContext());

        List<Favorito> favoritos = ((MainActivity) getActivity()).getFavoritos();


        Usuario u = ((MainActivity) getActivity()).getUsuario();
        favoritosAdapter = new FavoritosAdapter(favoritos, u, R.layout.recycler_view_item_fav, new FavoritosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Monumento monumento, int position) {
                Toast.makeText(getContext(), monumento + "-" + position, Toast.LENGTH_SHORT).show();
            }
        });

        fRecyclerView.setLayoutManager(fLayoutManager);
        fRecyclerView.setAdapter(favoritosAdapter);

        return view;
    }

    //TODO: get favoritos api by id usuario
    private List<Favorito> getFavoritosUsuario(){
    return null;
    }
}
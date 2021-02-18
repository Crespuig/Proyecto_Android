package com.example.proyecto_android.fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.proyecto_android.R;
import com.example.proyecto_android.activitys.MainActivity;
import com.example.proyecto_android.adapters.FavoritosAdapter;
import com.example.proyecto_android.adapters.ListaMonumentosAdapter;
import com.example.proyecto_android.bbdd.MiBD;
import com.example.proyecto_android.dao.FavoritoDAO;
import com.example.proyecto_android.dao.MonumentoDAO;
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

        List<Favorito> favoritos = getFavoritosUsuario();
        List<Monumento> monumentosList = new ArrayList<>();

        for (Favorito f: favoritos) {
            MonumentoDAO monumentoDAO = new MonumentoDAO(getContext());
            monumentoDAO = monumentoDAO.abrir();
            Cursor c = monumentoDAO.getRegistro(f.getIdMonumento());
            String nombre = c.getString(1);
            String numPol = c.getString(2);
            int codVia = c.getInt(3);
            String telefono = c.getString(4);
            int ruta = c.getInt(5);
            float latitud = c.getFloat(6);
            float longitud = c.getFloat(7);
            String imagen = c.getString(8);

            Monumento m = new Monumento(f.getIdMonumento(),nombre, numPol, codVia, telefono, ruta, latitud, longitud, imagen);
            monumentosList.add(m);
        }
        Usuario u = ((MainActivity) getActivity()).getUsuario();
        favoritosAdapter = new FavoritosAdapter(monumentosList, u, R.layout.recycler_view_item_fav, new FavoritosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Monumento monumento, int position) {
                Toast.makeText(getContext(), monumento + "-" + position, Toast.LENGTH_SHORT).show();
            }
        });

        fRecyclerView.setLayoutManager(fLayoutManager);
        fRecyclerView.setAdapter(favoritosAdapter);

        return view;
    }

    private List<Favorito> getFavoritosUsuario(){
        Usuario u = ((MainActivity) getActivity()).getUsuario();
        FavoritoDAO favoritoDAO = new FavoritoDAO();
        return favoritoDAO.getAllByUsuario(u.getId());
    }
}
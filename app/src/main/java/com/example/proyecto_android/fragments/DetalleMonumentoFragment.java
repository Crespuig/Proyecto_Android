package com.example.proyecto_android.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyecto_android.R;
import com.example.proyecto_android.activitys.MainActivity;
import com.example.proyecto_android.model.Monumento;

import java.util.concurrent.TimeoutException;

public class DetalleMonumentoFragment extends Fragment {

    ImageView imgDetalle;
    TextView detalleIdNotes;
    TextView detalleNombre;
    TextView detalleLatitud;
    TextView detalleLongitud;
    TextView detalleNumPol;
    TextView detalleCodvia;
    TextView detalleTelefono;
    TextView detalleRuta;

    private Context context;

    //private Button btnVolver;
    private ListaFragment listaFragment = new ListaFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_detalle_monumento, container, false);

        imgDetalle = view.findViewById(R.id.imgDetalle);
        detalleIdNotes = view.findViewById(R.id.detalleIdNotes);
        detalleNombre = view.findViewById(R.id.detalleNombre);
        detalleLatitud = view.findViewById(R.id.detalleLatitud);
        detalleLongitud = view.findViewById(R.id.detalleLongitud);
        detalleNumPol = view.findViewById(R.id.detalleNumPol);
        detalleCodvia = view.findViewById(R.id.detalleCodvia);
        detalleTelefono = view.findViewById(R.id.detalleTelefono);
        detalleRuta = view.findViewById(R.id.detalleRuta);

        //Crear objeto bundler para recibir el objeto enviado por argumentos
        Bundle objetoMonumento = getArguments();
        Monumento monumento = null;
        //Validar para verificar si existen argumentos para mostrar
        if (objetoMonumento != null){
            monumento = (Monumento) objetoMonumento.getSerializable("objeto");
            //Establecer los datos en las vistas
            if (monumento.getImagen() != null) {
                int resId = context.getResources().getIdentifier(monumento.getImagen(), "drawable", context.getPackageName());
                imgDetalle.setImageResource(resId);
            }
            detalleIdNotes.setText(monumento.getIdNotes());
            detalleNombre.setText(monumento.getName());
            detalleLatitud.setText((int) monumento.getX());
            detalleLongitud.setText((int) monumento.getY());
            detalleNumPol.setText(monumento.getNumPol());
            detalleCodvia.setText(monumento.getCodVia());
            detalleTelefono.setText(monumento.getTelefono());
            detalleRuta.setText(monumento.getRuta());
        }

        //btnVolver = view.findViewById(R.id.btnDetalleVolver);

        /*btnVolver.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.fragment_detalle_monumento, listaFragment)
                        .addToBackStack(null)
                        .commit();
            }


        });*/

        return  view;
    }


}
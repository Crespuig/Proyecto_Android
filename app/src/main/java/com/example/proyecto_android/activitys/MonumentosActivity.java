package com.example.proyecto_android.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_android.R;
import com.example.proyecto_android.adapters.GestionMonumentoAdapter;
import com.example.proyecto_android.model.Constantes;
import com.example.proyecto_android.model.Usuario;

public class MonumentosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Usuario usuario;
    private ListView lista;
    GestionMonumentoAdapter gestionMonumentoAdapter;
    private TextView v_txtSinDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monumentos);

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");

        lista = (ListView) findViewById(R.id.lista);
        lista.setOnItemClickListener(this);

        //TODO: Get Monumentos
        // crear adapter pasando la lista de monumentos

        //gestionMonumentoAdapter = new GestionMonumentoAdapter(this, lista);

            lista.setAdapter(gestionMonumentoAdapter);
            //RELLENAR CAMPOS CON DATOS JSON

            //SI ES VACIO
            v_txtSinDatos = (TextView) findViewById(R.id.txtSinDatos);
            v_txtSinDatos.setVisibility(View.INVISIBLE);
            v_txtSinDatos.invalidate();

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        // Creamos el intent para abrir el formulario de hipotecas
        Intent i = new Intent(MonumentosActivity.this, GestionMonumentosActivity.class);

        // Le pasamos que el modo en que lo vamos a abrir es solo de visualizacion
        i.putExtra(Constantes.C_MODO, Constantes.C_VISUALIZAR);

        // Le pasamos el Monumento
        //i.putExtra(MonumentoDAO.C_COLUMNA_MONUMENTOS_IDNOTES, id);

        i.putExtra("usuario", usuario);

        // Iniciamos la actividad esperando un resultado, que en este caso no nos importa cual sea
        startActivityForResult(i, Constantes.C_VISUALIZAR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_monumentos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent i;
        switch (item.getItemId()) {
            case R.id.menu_crear:
                i = new Intent(MonumentosActivity.this, GestionMonumentosActivity.class);
                i.putExtra(Constantes.C_MODO, Constantes.C_CREAR);
                i.putExtra("usuario", usuario);
                startActivityForResult(i, Constantes.C_CREAR);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //
        // Nos aseguramos que es la petición que hemos realizado
        //
        switch(requestCode) {
            case Constantes.C_CREAR:
                if (resultCode == RESULT_OK)
                    //recargar_lista();
                    break;
            case Constantes.C_VISUALIZAR:
                if (resultCode == RESULT_OK)
                    //recargar_lista();
                    break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /*private void recargar_lista() {
        MonumentoDAO monumentoDAO = new MonumentoDAO(getBaseContext());
        monumentoDAO.abrir();
        GestionMonumentoAdapter gestionMonumentoAdapter = new GestionMonumentoAdapter(this, monumentoDAO.getCursor());
        lista.setAdapter(gestionMonumentoAdapter);
    }*/
}
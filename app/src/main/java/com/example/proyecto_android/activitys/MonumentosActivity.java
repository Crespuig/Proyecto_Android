package com.example.proyecto_android.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_android.R;
import com.example.proyecto_android.adapters.GestionMonumentoAdapter;
import com.example.proyecto_android.bbdd.Constantes;
import com.example.proyecto_android.dao.MonumentoDAO;
import com.example.proyecto_android.model.Usuario;

public class MonumentosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Usuario usuario;
    MonumentoDAO monumentoDAO;
    private Cursor cursor;
    private ListView lista;
    GestionMonumentoAdapter gestionMonumentoAdapter;
    private TextView v_txtSinDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monumentos);

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");

        lista = (ListView) findViewById(R.id.lista);
        monumentoDAO = new MonumentoDAO(this);
        lista.setOnItemClickListener(this);

        lista = (ListView) findViewById(R.id.lista);
        // Creamos la clase que nos permitira acceder a las operaciones de la db
        monumentoDAO = new MonumentoDAO(this);

        lista.setOnItemClickListener(this);

        try {
            monumentoDAO.abrir();

            cursor = monumentoDAO.getCursor();

            startManagingCursor(cursor);

            gestionMonumentoAdapter = new GestionMonumentoAdapter(this, cursor);

            lista.setAdapter(gestionMonumentoAdapter);
            // Si hay datos no se muestra la etiqueta de Sin Datos
            if (cursor.getCount() > 0) {
                v_txtSinDatos = (TextView) findViewById(R.id.txtSinDatos);
                v_txtSinDatos.setVisibility(View.INVISIBLE);
                v_txtSinDatos.invalidate();
            }
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Se ha producido un error al abrir la base de datos.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        // Creamos el intent para abrir el formulario de hipotecas
        Intent i = new Intent(MonumentosActivity.this, GestionMonumentosActivity.class);

        // Le pasamos que el modo en que lo vamos a abrir es solo de visualizacion
        i.putExtra(Constantes.C_MODO, Constantes.C_VISUALIZAR);

        // Le pasamos el valor del identificador de la hipoteca
        i.putExtra(MonumentoDAO.C_COLUMNA_MONUMENTOS_IDNOTES, id);

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
                    recargar_lista();
            case Constantes.C_VISUALIZAR:
                if (resultCode == RESULT_OK)
                    recargar_lista();
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void recargar_lista() {
        MonumentoDAO monumentoDAO = new MonumentoDAO(getBaseContext());
        monumentoDAO.abrir();
        GestionMonumentoAdapter gestionMonumentoAdapter = new GestionMonumentoAdapter(this, monumentoDAO.getCursor());
        lista.setAdapter(gestionMonumentoAdapter);
    }
}
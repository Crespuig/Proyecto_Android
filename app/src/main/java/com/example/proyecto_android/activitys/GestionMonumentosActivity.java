package com.example.proyecto_android.activitys;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_android.R;
import com.example.proyecto_android.model.Constantes;
import com.example.proyecto_android.model.Monumento;
import com.example.proyecto_android.model.Usuario;

public class GestionMonumentosActivity extends AppCompatActivity {

    private Cursor cursor;
    private int modo;
    private long id;
    private EditText nombre;
    private EditText numPol;
    private EditText codVia;
    private EditText telefono;
    private EditText ruta;
    private EditText latitud;
    private EditText longitud;
    private EditText img;

    private Button boton_guardar;
    private Button boton_cancelar;
    private Usuario usuario;
    private Monumento monumento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_monumentos);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        if (extra == null) return;

        nombre = (EditText) findViewById(R.id.nombre);
        numPol = (EditText) findViewById(R.id.numPol);
        codVia = (EditText) findViewById(R.id.codVia);
        telefono = (EditText) findViewById(R.id.telefono);
        ruta = (EditText) findViewById(R.id.ruta);
        img = (EditText) findViewById(R.id.img);

        boton_guardar = (Button) findViewById(R.id.boton_guardar);
        boton_cancelar = (Button) findViewById(R.id.boton_cancelar);

        //TODO: GET Monumento que le pasamos del Intent anterior

        establecerModo(extra.getInt(Constantes.C_MODO));

        boton_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
        boton_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });
    }

    private void establecerModo(int m) {
        this.modo = m;
        // Si estamos solamente visualizando establecemos el modo edicion desactivado a todo el formulario
        if (modo == Constantes.C_VISUALIZAR) {
            this.setTitle(nombre.getText().toString());
            this.setEdicion(false);
        } else if (modo == Constantes.C_CREAR) {
            this.setTitle(R.string.monumento_crear_titulo);
            this.setEdicion(true);
        } else if (modo == Constantes.C_EDITAR) {
            this.setTitle(R.string.monumento_editar_titulo);
            this.setEdicion(true);
        }
    }

    //TODO: RELLENAR TEXTOS A PARTIR DEL MONUMENTO
    private void rellenaDatos(long id) {
        nombre.setText(monumento.getName());
        //numPol.setText(cursor.getString(cursor.getColumnIndex(MonumentoDAO.C_COLUMNA_NUMPOL)));
        //codVia.setText(cursor.getString(cursor.getColumnIndex(MonumentoDAO.C_COLUMNA_CODVIA)));
        //telefono.setText(cursor.getString(cursor.getColumnIndex(MonumentoDAO.C_COLUMNA_TELEFONO)));
        //ruta.setText(cursor.getString(cursor.getColumnIndex(MonumentoDAO.C_COLUMNA_RUTA)));
        //coordenadas.setText(cursor.getString(cursor.getColumnIndex(MonumentoDAO.C_COLUMNA_COORDENADAS)));

    }

    private void setEdicion(boolean opcion) {
        nombre.setEnabled(opcion);
        numPol.setEnabled(opcion);
        codVia.setEnabled(opcion);
        telefono.setEnabled(opcion);
        ruta.setEnabled(opcion);
        img.setEnabled(opcion);
    }

    //TODO: POST con el Monumento a guardar
    private void guardar() {
        Monumento m;

        //m.setName( nombre.getText().toString());
        //reg.put(MonumentoDAO.C_COLUMNA_NUMPOL, numPol.getText().toString());
        //reg.put(MonumentoDAO.C_COLUMNA_CODVIA, codVia.getText().toString());
        //reg.put(MonumentoDAO.C_COLUMNA_TELEFONO, telefono.getText().toString());
        //reg.put(MonumentoDAO.C_COLUMNA_RUTA, ruta.getText().toString());
        //reg.put(MonumentoDAO.C_COLUMNA_IMAGEN, img.getText().toString());

        if (modo == Constantes.C_CREAR) {
            //POST
            Toast.makeText(GestionMonumentosActivity.this, R.string.monumento_crear_confirmacion,
                    Toast.LENGTH_SHORT).show();
        }else if (modo == Constantes.C_EDITAR) {
            //PUT
            Toast.makeText(GestionMonumentosActivity.this, R.string.monumento_editar_confirmacion,
                    Toast.LENGTH_SHORT).show();
        }

        //
        // Devolvemos el control a MainActivity
        //
        setResult(RESULT_OK);
        finish();
    }

    private void cancelar() {
        setResult(RESULT_CANCELED, null);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        if (modo == Constantes.C_VISUALIZAR)
            getMenuInflater().inflate(R.menu.menu_gestion_monumentos, menu);
        else
            getMenuInflater().inflate(R.menu.menu_monumentos_editar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_editar:
                establecerModo(Constantes.C_EDITAR);
                return true;
            case R.id.menu_eliminar:
                borrar(id);
                return true;
            case R.id.menu_cancelar:
                cancelar();
                return true;
            case R.id.menu_guardar:
                guardar();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //TODO: DELETE a la api con id monumento
    private void borrar(final long id)
    {

    }

}
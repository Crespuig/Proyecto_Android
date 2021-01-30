package com.example.proyecto_android.activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import com.example.proyecto_android.R;
import com.example.proyecto_android.bbdd.Constantes;
import com.example.proyecto_android.dao.MonumentoDAO;
import com.example.proyecto_android.model.Usuario;

public class GestionMonumentosActivity extends AppCompatActivity {

    private Cursor cursor;
    private MonumentoDAO monumentoDAO;
    private int modo;
    private long id;
    private EditText nombre;
    private EditText numPol;
    private EditText codVia;
    private EditText telefono;
    private EditText ruta;
    private EditText coordenadas;

    private Button boton_guardar;
    private Button boton_cancelar;
    private Usuario usuario;

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
        coordenadas = (EditText) findViewById(R.id.coordenada);

        boton_guardar = (Button) findViewById(R.id.boton_guardar);
        boton_cancelar = (Button) findViewById(R.id.boton_cancelar);

        monumentoDAO = new MonumentoDAO(this);
        try {
            monumentoDAO.abrir();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (extra.containsKey(MonumentoDAO.C_COLUMNA_MONUMENTOS_IDNOTES)) {
            id = extra.getLong(MonumentoDAO.C_COLUMNA_MONUMENTOS_IDNOTES);
            consultar(id);
        }

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

    private void consultar(long id) {
        cursor = monumentoDAO.getRegistro(id);
        nombre.setText(cursor.getString(cursor.getColumnIndex(MonumentoDAO.C_COLUMNA_NOMBRE)));
        numPol.setText(cursor.getString(cursor.getColumnIndex(MonumentoDAO.C_COLUMNA_NUMPOL)));
        codVia.setText(cursor.getString(cursor.getColumnIndex(MonumentoDAO.C_COLUMNA_CODVIA)));
        telefono.setText(cursor.getString(cursor.getColumnIndex(MonumentoDAO.C_COLUMNA_TELEFONO)));
        ruta.setText(cursor.getString(cursor.getColumnIndex(MonumentoDAO.C_COLUMNA_RUTA)));
        coordenadas.setText(cursor.getString(cursor.getColumnIndex(MonumentoDAO.C_COLUMNA_COORDENADAS)));

    }

    private void setEdicion(boolean opcion) {
        nombre.setEnabled(opcion);
        numPol.setEnabled(opcion);
        codVia.setEnabled(opcion);
        telefono.setEnabled(opcion);
        ruta.setEnabled(opcion);
        coordenadas.setEnabled(opcion);
    }

    private void guardar() {
        ContentValues reg = new ContentValues();

        if (modo == Constantes.C_EDITAR) {
            reg.put(MonumentoDAO.C_COLUMNA_MONUMENTOS_IDNOTES, id);
        }

        reg.put(MonumentoDAO.C_COLUMNA_NOMBRE, nombre.getText().toString());
        reg.put(MonumentoDAO.C_COLUMNA_NUMPOL, numPol.getText().toString());
        reg.put(MonumentoDAO.C_COLUMNA_CODVIA, codVia.getText().toString());
        reg.put(MonumentoDAO.C_COLUMNA_TELEFONO, telefono.getText().toString());
        reg.put(MonumentoDAO.C_COLUMNA_RUTA, ruta.getText().toString());
        reg.put(MonumentoDAO.C_COLUMNA_COORDENADAS, coordenadas.getText().toString());

        if (modo == Constantes.C_CREAR) {
            monumentoDAO.insert(reg);
            Toast.makeText(GestionMonumentosActivity.this, R.string.monumento_crear_confirmacion,
                    Toast.LENGTH_SHORT).show();
        }else if (modo == Constantes.C_EDITAR) {
            Toast.makeText(GestionMonumentosActivity.this, R.string.monumento_editar_confirmacion,
                    Toast.LENGTH_SHORT).show();
            monumentoDAO.update(reg);
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

    private void borrar(final long id)
    {
        /**
         * Borramos el registro con confirmación
         */
        AlertDialog.Builder dialogEliminar = new AlertDialog.Builder(this);
        dialogEliminar.setIcon(android.R.drawable.ic_dialog_alert);
        dialogEliminar.setTitle(getResources().getString(R.string.monumento_eliminar_titulo));
        dialogEliminar.setMessage(getResources().getString(R.string.monumento_eliminar_mensaje));
        dialogEliminar.setCancelable(false);
        dialogEliminar.setPositiveButton(getResources().getString(android.R.string.ok), new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int boton) {
                        monumentoDAO.delete(id);
                        Toast.makeText(GestionMonumentosActivity.this, R.string.monumento_eliminar_confirmacion,
                                Toast.LENGTH_SHORT).show();
                        /**
                         * Devolvemos el control
                         */
                        setResult(RESULT_OK);
                        finish();
                    }
                });
        dialogEliminar.setNegativeButton(android.R.string.no, null);
        dialogEliminar.show();
    }

}
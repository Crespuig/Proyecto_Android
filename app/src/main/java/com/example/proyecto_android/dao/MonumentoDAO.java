package com.example.proyecto_android.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyecto_android.bbdd.MiBD;


public class MonumentoDAO  {

    public static final String C_TABLA = "monumentos";

    public static final String C_COLUMNA_MONUMENTOS_IDNOTES = "idNotes";
    public static final String C_COLUMNA_NOMBRE = "nombre";
    public static final String C_COLUMNA_NUMPOL = "numPol";
    public static final String C_COLUMNA_CODVIA = "codVia";
    public static final String C_COLUMNA_TELEFONO = "telefono";
    public static final String C_COLUMNA_RUTA = "ruta";
    public static final String C_COLUMNA_COORDENADAS = "coordenadas";

    private Context contexto;
    private MiBD miBD;
    private SQLiteDatabase db;

    private String[] columnas = new String[]{C_COLUMNA_MONUMENTOS_IDNOTES, C_COLUMNA_NOMBRE, C_COLUMNA_NUMPOL,
            C_COLUMNA_CODVIA, C_COLUMNA_TELEFONO, C_COLUMNA_RUTA, C_COLUMNA_COORDENADAS};

    public MonumentoDAO(Context context) {
        this.contexto = context;
    }

    public MonumentoDAO abrir() {
        miBD = new MiBD(contexto);
        db = miBD.getWritableDatabase();
        return this;
    }

    public void cerrar() {
        miBD.close();
    }

    public Cursor getCursor() {
        Cursor c = db.query(true, C_TABLA, columnas, null, null, null, null, null, null);
        return c;
    }

    public Cursor getRegistro(long id){
        String condicion = C_COLUMNA_MONUMENTOS_IDNOTES + "=" + id;
        Cursor c = db.query( true, C_TABLA, columnas, condicion, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public long insert(ContentValues reg){
        if (db == null)
            abrir();
        return db.insert(C_TABLA, null, reg);
    }

    public long update(ContentValues reg){
        long result = 0;
        if (db == null)
            abrir();
        if (reg.containsKey(C_COLUMNA_MONUMENTOS_IDNOTES)) {
            long id = reg.getAsLong(C_COLUMNA_MONUMENTOS_IDNOTES);
            reg.remove(C_COLUMNA_MONUMENTOS_IDNOTES);
            result = db.update(C_TABLA, reg, "idNotes=" + id, null);
        }
        return result;
    }

    public void delete(long idNotes){
        String condicion = C_COLUMNA_MONUMENTOS_IDNOTES + "=" + idNotes;
        MiBD.getDB().delete("monumentos", condicion, null);
    }

    public Cursor getAll(){
        Cursor cursor = MiBD.getDB().query("monumentos", columnas, null, null, null, null, null);
        return cursor;
    }
}

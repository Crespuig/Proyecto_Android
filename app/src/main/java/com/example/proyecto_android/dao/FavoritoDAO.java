package com.example.proyecto_android.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.example.proyecto_android.bbdd.MiBD;
import com.example.proyecto_android.model.Favorito;
import com.example.proyecto_android.model.Usuario;

import java.util.ArrayList;

public class FavoritoDAO implements PojoDAO {

    @Override
    public long add(Object obj) {
        ContentValues contentValues = new ContentValues();
        Favorito f = (Favorito) obj;
        contentValues.put("idUsuario", f.getIdUsuario());
        contentValues.put("idMonumento", f.getIdMonumento());
        return MiBD.getDB().insert("favoritos", null, contentValues);
    }

    @Override
    public int update(Object obj) {

        ContentValues contentValues = new ContentValues();
        Favorito f = (Favorito) obj;
        contentValues.put("idUsuario", f.getIdUsuario());
        contentValues.put("idMonumento", f.getIdMonumento());
        contentValues.put("idFavoritos", f.getIdFavoritos());

        String condicion = "_id=" + String.valueOf(f.getIdFavoritos());

        int resultado = MiBD.getDB().update("favoritos", contentValues, condicion, null);

        return resultado;
    }

    @Override
    public void delete(Object obj) {
        Favorito f = (Favorito) obj;
        String condicion = "_id=" + String.valueOf(f.getIdFavoritos());

        //Se borra el cliente indicado en el campo de texto
        MiBD.getDB().delete("favoritos", condicion, null);
    }

    @Override
    public Object search(Object obj) {
        Favorito f = (Favorito) obj;
        String condicion = "";
        if (f.getIdUsuario() >= 1) {
            condicion = "idUsuario=" + f.getIdUsuario();
        }
        if (f.getIdMonumento() >= 1) {
            condicion = "idMonumento=" + f.getIdMonumento();
        }

        String[] columnas = {
                "_id", "idUsuario", "idMonumento"
        };
        Cursor cursor = MiBD.getDB().query("favoritos", columnas, condicion, null, null, null, null);
        Favorito nuevoFavorito = null;
        if (cursor.moveToFirst()) {
            nuevoFavorito = new Favorito();
            nuevoFavorito.setIdFavoritos(cursor.getInt(0));
            nuevoFavorito.setIdUsuario(cursor.getInt(1));
            nuevoFavorito.setIdMonumento(cursor.getInt(2));

            // Obtenemos la lista de cuentas que tiene el cliente
            //c.setListaCuentas(MiBD.getInstance(null).getCuentaDAO().getCuentas(c));


        }
        return nuevoFavorito;
    }

    @Override
    public ArrayList getAll() {
        ArrayList<Favorito> listaFavoritos = new ArrayList<Favorito>();
        String[] columnas = {
                "_id", "idUsuario", "idMonumentos"
        };
        Cursor cursor = MiBD.getDB().query("favoritos", columnas, null, null, null, null, null);
        //CuentaDAO cuentaDAO = new CuentaDAO();
        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                Favorito nuevoFavorito = null;
                nuevoFavorito = new Favorito();
                nuevoFavorito.setIdFavoritos(cursor.getInt(0));
                nuevoFavorito.setIdUsuario(cursor.getInt(1));
                nuevoFavorito.setIdMonumento(cursor.getInt(2));

                //c.setListaCuentas(MiBD.getInstance(null).getCuentaDAO().getCuentas(c));
                listaFavoritos.add(nuevoFavorito);

            } while (cursor.moveToNext());
        }
        return listaFavoritos;
    }

}

package com.example.proyecto_android.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.example.proyecto_android.bbdd.MiBD;
import com.example.proyecto_android.model.Usuario;

import java.util.ArrayList;

public class UsuarioDAO implements PojoDAO{

    @Override
    public long add(Object obj) {
        ContentValues contentValues = new ContentValues();
        Usuario u = (Usuario) obj;
        contentValues.put("nombre", u.getNombre());
        contentValues.put("apellidos", u.getApellidos());
        contentValues.put("claveSeguridad", u.getClaveSeguridad());
        contentValues.put("email", u.getEmail());
        return MiBD.getDB().insert("usuarios", null, contentValues);
    }

    @Override
    public int update(Object obj) {

        ContentValues contentValues = new ContentValues();
        Usuario u = (Usuario) obj;
        contentValues.put("nif" , u.getNif());
        contentValues.put("nombre", u.getNombre());
        contentValues.put("apellidos", u.getApellidos());
        contentValues.put("claveSeguridad", u.getClaveSeguridad());
        contentValues.put("email", u.getEmail());

        String condicion = "id=" + String.valueOf(u.getId());

        int resultado = MiBD.getDB().update("usuarios", contentValues, condicion, null);

        return resultado;
    }

    @Override
    public void delete(Object obj) {
        Usuario u = (Usuario) obj;
        String condicion = "id=" + String.valueOf(u.getId());

        //Se borra el cliente indicado en el campo de texto
        MiBD.getDB().delete("usuarios", condicion, null);
    }

    @Override
    public Object search(Object obj) {
        Usuario c = (Usuario) obj;
        String condicion = "";
        if(TextUtils.isEmpty(c.getNif())){
            condicion = "id=" + String.valueOf(c.getId());
        }else{
            condicion = "nif=" + "'" + c.getNif() + "'";
        }

        String[] columnas = {
                "id","nif","nombre","apellidos","claveseguridad","email"
        };
        Cursor cursor = MiBD.getDB().query("usuarios", columnas, condicion, null, null, null, null);
        Usuario nuevoUsuario = null;
        if (cursor.moveToFirst()) {
            nuevoUsuario = new Usuario();
            nuevoUsuario.setId(cursor.getInt(0));
            nuevoUsuario.setNif(cursor.getString(1));
            nuevoUsuario.setNombre(cursor.getString(2));
            nuevoUsuario.setApellidos(cursor.getString(3));
            nuevoUsuario.setClaveSeguridad(cursor.getString(4));
            nuevoUsuario.setEmail(cursor.getString(5));

            // Obtenemos la lista de cuentas que tiene el cliente
            //c.setListaCuentas(MiBD.getInstance(null).getCuentaDAO().getCuentas(c));


        }
        return nuevoUsuario;
    }

    @Override
    public ArrayList getAll() {
        ArrayList<Usuario> listaClientes = new ArrayList<Usuario>();
        String[] columnas = {
                "id","nombre","apellidos","claveseguridad","email"
        };
        Cursor cursor = MiBD.getDB().query("usuarios", columnas, null, null, null, null, null);
        //CuentaDAO cuentaDAO = new CuentaDAO();
        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                Usuario c = new Usuario();
                c.setId(cursor.getInt(0));
                c.setNif(cursor.getString(1));
                c.setNombre(cursor.getString(2));
                c.setApellidos(cursor.getString(3));
                c.setClaveSeguridad(cursor.getString(4));
                c.setEmail(cursor.getString(5));
                //c.setListaCuentas(MiBD.getInstance(null).getCuentaDAO().getCuentas(c));
                listaClientes.add(c);

            } while(cursor.moveToNext());
        }
        return listaClientes;
    }

}

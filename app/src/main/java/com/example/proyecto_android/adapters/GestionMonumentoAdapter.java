package com.example.proyecto_android.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.proyecto_android.dao.MonumentoDAO;

public class GestionMonumentoAdapter extends CursorAdapter {

    private MonumentoDAO monumentoDAO = null;

    public GestionMonumentoAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        monumentoDAO = new MonumentoDAO(context);
        monumentoDAO.abrir();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        return view;
    }

    // El método bindView se utiliza para enlazar todos los datos de una vista determinada
    // como la creación del texto en una TextView . En este caso obtenemos el nombre y lo
    // asignamos al textview.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Como hemos inflado simple_dropdown_item_1line solo tenemos un TextView que lo obtenemos
        TextView tv = (TextView) view;
        // Obtenemos el indice de la columna
        int i = cursor.getColumnIndex(MonumentoDAO.C_COLUMNA_NOMBRE);
        // Asignamos el valor
        tv.setText(cursor.getString(i));
    }

}

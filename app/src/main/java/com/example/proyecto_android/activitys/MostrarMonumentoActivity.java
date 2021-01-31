package com.example.proyecto_android.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.proyecto_android.R;
import com.example.proyecto_android.model.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MostrarMonumentoActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_monumento);


        LinearLayout botonera = (LinearLayout) findViewById(R.id.layoutMostrarMonumento);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        try {
            String jsonFileContent = Utils.leerJson(this, "opendata1259927732622104222.JSON");
            JSONArray jsonArray = new JSONArray(jsonFileContent);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("idnotes");
                String name = jsonObject.getString("nombre");
                Log.i("NOMBRE: ", name);

                //CEAR BOTON
                Button button = new Button(this);
                button.setLayoutParams(layoutParams);
                button.setText(name);
                button.setId(id);
                button.setOnClickListener(this);

                botonera.addView(button);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {

    }
}

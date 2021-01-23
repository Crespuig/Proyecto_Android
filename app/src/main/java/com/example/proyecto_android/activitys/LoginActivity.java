package com.example.proyecto_android.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_android.R;
import com.example.proyecto_android.bbdd.MiAppOperacional;
import com.example.proyecto_android.model.Usuario;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText usuario;
    EditText password;
    MiAppOperacional api;
    Button btnAcceder;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = (EditText)findViewById(R.id.passworAnterior);
        password = (EditText)findViewById(R.id.passwordNuevo);


        api = MiAppOperacional.getInstance(this);

        btnAcceder = (Button)findViewById(R.id.btnAcceder);
        btnAcceder.setOnClickListener(this);

        usuario.setText("11111111A");
        password.setText("1234");

        TextView olvidar = (TextView) findViewById(R.id.olvidar);
        olvidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Los datos no coinciden con ningún usuario", Toast.LENGTH_SHORT).show();
            }
        });

        prefs = getSharedPreferences("preferenciasUsuario", Context.MODE_PRIVATE);

    }

    private void saveOnPreferences(String usuario, String password) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("usuario", usuario);
        editor.putString("password", password);
        editor.apply();
    }

    @Override
    public void onClick(View view) {

        /*Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);*/

        Usuario u = new Usuario();
        u.setNif(usuario.getText().toString());

        u.setClaveSeguridad(password.getText().toString());
        u = api.login(u);
        Toast.makeText(LoginActivity.this, "Bienvenido/a " + u.getNombre(), Toast.LENGTH_SHORT).show();

        if (u == null) {
            Toast.makeText(LoginActivity.this, "Los datos no coinciden con ningún usuario", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("usuario", u);
            startActivity(intent);
            saveOnPreferences(usuario.getText().toString(), password.getText().toString());
        }
    }

}
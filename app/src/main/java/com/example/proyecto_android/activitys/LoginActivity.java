package com.example.proyecto_android.activitys;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_android.R;
import com.example.proyecto_android.api.moumentos.ApiMonumentosUtils;
import com.example.proyecto_android.api.moumentos.ApiMonumetosService;
import com.example.proyecto_android.model.Monumento;
import com.example.proyecto_android.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {


    Button btnRegistrase;
    Button btnLogin;
    CheckBox checkRegistro;
    EditText usuario;
    EditText password;
    Button btnAcceder;

    private SharedPreferences prefs;
    private TextView texRegistro;
    private ApiMonumetosService apiMonumetosService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnAcceder);

        texRegistro = findViewById(R.id.textRegistro);

        texRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });

        apiMonumetosService = ApiMonumentosUtils.getClient();

        usuario = (EditText) findViewById(R.id.loginEmail);
        password = (EditText) findViewById(R.id.loginPaswword);

        usuario.setText("admin@admin.com");
        password.setText("admin");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = usuario.getText().toString();
                String pass = password.getText().toString();

                if (!email.isEmpty() && !pass.isEmpty()) {
                    login(email, pass);
                } else {
                    Toast.makeText(LoginActivity.this, "Login fallido amigo", Toast.LENGTH_SHORT).show();

                }
            }
        });

        TextView olvidar = (TextView) findViewById(R.id.olvidar);
        olvidar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick (View view){
        Toast.makeText(LoginActivity.this, "Los datos no coinciden con ningún usuario", Toast.LENGTH_SHORT).show();
    }
    });

    prefs = getSharedPreferences("preferenciasUsuario",Context.MODE_PRIVATE);

}


    //TODO: Conectar POST A API PARA HACER LOGIN
    private void login(String email, String pass) {
       // HACER LLAMADA API
        Usuario u = new Usuario();
        u.setEmail(email);
        u.setPassword(pass);
        apiMonumetosService.login(u).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Log.d("LOg", response.body().toString());
                Usuario u = response.body();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("usuario", u);
                saveOnPreferences(u.getEmail(), u.getPassword());
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.d("LOg", t.toString());
                Toast.makeText(LoginActivity.this, "Login fallido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveOnPreferences(String usuario, String password) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("usuario", usuario);
        editor.putString("password", password);
        editor.apply();
    }

}
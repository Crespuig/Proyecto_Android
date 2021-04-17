package com.example.proyecto_android.activitys;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_android.R;
import com.example.proyecto_android.model.Usuario;

import retrofit2.Call;
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

    @SuppressLint("SetTextI18n")
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("192.168.0.10:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Usuario usuarioService = retrofit.create(Usuario.class);
        //Call<Usuario> userCall = usuarioService.getEmail();

        // SI LOGIN OK
        Usuario u = new Usuario();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("usuario", u);
            saveOnPreferences(u.getEmail(), password.getText().toString());
            startActivity(intent);
        // SI LOGIN KO
            Toast.makeText(LoginActivity.this, "Login fallido", Toast.LENGTH_SHORT).show();

    }

    private void saveOnPreferences(String usuario, String password) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("usuario", usuario);
        editor.putString("password", password);
        editor.apply();
    }

}
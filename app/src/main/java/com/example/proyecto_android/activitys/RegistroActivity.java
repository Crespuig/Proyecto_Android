package com.example.proyecto_android.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_android.R;
import com.example.proyecto_android.api.moumentos.ApiMonumentosUtils;
import com.example.proyecto_android.api.moumentos.ApiMonumetosService;
import com.example.proyecto_android.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private EditText registerEmail;
    private EditText registerNombre;
    private EditText registerApellidos;
    private EditText registerPassword;
    private EditText registerRepeatPassword;
    private Button registerButton;
    private Button goLoginButton;
    private ApiMonumetosService apiMonumetosService;
    private List<Usuario> usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        apiMonumetosService = ApiMonumentosUtils.getClient();

        registerEmail = findViewById(R.id.registerEmail);
        registerNombre = findViewById(R.id.registerNombre);
        registerApellidos = findViewById(R.id.registerApellidos);
        registerPassword = findViewById(R.id.registerPassword);
        registerRepeatPassword = findViewById(R.id.repeatPassword);
        registerButton = findViewById(R.id.registerButton);
        goLoginButton = findViewById(R.id.registerGoLoginButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = registerNombre.getText().toString();
                String apellidos = registerApellidos.getText().toString();
                String email = registerEmail.getText().toString();
                String password = registerPassword.getText().toString();
                String repeatPassword = registerRepeatPassword.getText().toString();

                if (password.equals(repeatPassword) && !email.isEmpty() && !password.isEmpty() && !repeatPassword.isEmpty()) {
                    if (password.length() < 6) {
                        register(email, nombre, apellidos, password);
                        Toast.makeText(RegistroActivity.this, "La contraseña debe contener al menos 6 carácteres", Toast.LENGTH_SHORT).show();
                    }
                    register(email, nombre, apellidos, password);
                } else {
                    Toast.makeText(RegistroActivity.this, "Rellena todos los campos para poder registrarte", Toast.LENGTH_SHORT).show();

                }
            }
        });

        goLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    //TODO: POST CON LOS DATOS DEL NUEVO USUARIO
    private void register(String email, String nombre, String apellidos, String password) {
        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setApellidos(apellidos);
        u.setEmail(email);
        u.setPassword(password);
        apiMonumetosService.registrar(u).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()){
                    Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                    Toast.makeText(RegistroActivity.this, "Registro finalizado", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else{
                    Toast.makeText(RegistroActivity.this, "Registro fallido", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(RegistroActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
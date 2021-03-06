package com.example.proyecto_android.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_android.R;
import com.example.proyecto_android.bbdd.MiAppOperacional;
import com.example.proyecto_android.dao.UsuarioDAO;
import com.example.proyecto_android.model.Usuario;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mfirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    Button btnRegistrase;
    Button btnLogin;
    public static final int REQUEST_CODE = 54654;
    CheckBox checkRegistro;

    List<AuthUI.IdpConfig> provider = Arrays.asList(
            new AuthUI.IdpConfig.FacebookBuilder().build()
            //new AuthUI.IdpConfig.GoogleBuilder().build()
    );

    EditText usuario;
    EditText password;
    MiAppOperacional api;
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
        mfirebaseAuth = FirebaseAuth.getInstance();

        usuario = (EditText) findViewById(R.id.loginEmail);
        password = (EditText) findViewById(R.id.loginPaswword);

        api = MiAppOperacional.getInstance(getApplicationContext());

        usuario.setText("manolo@cabesa.com");
        password.setText("123456");

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


        /*mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null){
                    Toast.makeText(LoginActivity.this, "Inicio sesion OK", Toast.LENGTH_SHORT).show();
                }else{
                    startActivityForResult(AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(provider)
                            .setIsSmartLockEnabled(false)
                            .build(), REQUEST_CODE
                    );
                }
            }
        };*/


        TextView olvidar = (TextView) findViewById(R.id.olvidar);
        olvidar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick (View view){
        Toast.makeText(LoginActivity.this, "Los datos no coinciden con ningún usuario", Toast.LENGTH_SHORT).show();
    }
    });

    prefs = getSharedPreferences("preferenciasUsuario",Context.MODE_PRIVATE);

}


    private void login(String email, String pass) {
        mfirebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            UsuarioDAO usuarioDAO = new UsuarioDAO();
                            Usuario u = new Usuario();
                            u.setEmail(usuario.getText().toString());
                            Usuario existeUsuario = (Usuario) usuarioDAO.search(u);
                            if (existeUsuario == null){
                                usuarioDAO.add(u);
                            }

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("usuario", u);
                            saveOnPreferences(u.getEmail(), password.getText().toString());
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Login fallido", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveOnPreferences(String usuario, String password) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("usuario", usuario);
        editor.putString("password", password);
        editor.apply();
    }

    /*@Override
    public void onClick(View view) {
        String email = usuario.getText().toString();
        String pass = password.getText().toString();

        Usuario u = new Usuario();
        u.setEmail(usuario.getText().toString());
        u.setClaveSeguridad(password.getText().toString());
        u = api.login(u);

        if (u == null) {
            Toast.makeText(LoginActivity.this, "Los datos no coinciden con ningún usuario", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("usuario", u);
            startActivity(intent);
            saveOnPreferences(usuario.getText().toString(), password.getText().toString());
        }
    }*/

    /*@Override
    protected void onResume() {
        super.onResume();
        mfirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mfirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }*/

}
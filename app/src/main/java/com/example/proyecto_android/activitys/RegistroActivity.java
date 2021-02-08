package com.example.proyecto_android.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto_android.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

public class RegistroActivity extends AppCompatActivity {

    private EditText registerEmail;
    private EditText registerNombre;
    private EditText registerApellidos;
    private EditText registerPassword;
    private EditText registerRepeatPassword;
    private Button registerButton;
    private Button goLoginButton;
    private Button registroFacebook;

    private FirebaseAuth mfirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    public static final int REQUEST_CODE = 54654;

    List<AuthUI.IdpConfig> provider = Arrays.asList(
            new AuthUI.IdpConfig.FacebookBuilder().build()
            //new AuthUI.IdpConfig.GoogleBuilder().build()
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mfirebaseAuth = FirebaseAuth.getInstance();

        registerEmail = findViewById(R.id.registerEmail);
        //registerNombre = findViewById(R.id.registerNombre);
        //registerApellidos = findViewById(R.id.registerApellidos);
        registerPassword = findViewById(R.id.registerPassword);
        registerRepeatPassword = findViewById(R.id.repeatPassword);
        registerButton = findViewById(R.id.registerButton);
        goLoginButton = findViewById(R.id.registerGoLoginButton);
        registroFacebook = findViewById(R.id.registerFacebook);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = registerEmail.getText().toString();
                String password = registerPassword.getText().toString();
                String repeatPassword = registerRepeatPassword.getText().toString();

                if (password.equals(repeatPassword) && !email.isEmpty() && !password.isEmpty() && !repeatPassword.isEmpty()) {
                    if (password.length() < 6) {
                        Toast.makeText(RegistroActivity.this, "La contraseña debe contener al menos 6 carácteres", Toast.LENGTH_SHORT).show();
                    }
                    register(email, password);
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

        registroFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(provider)
                        .setIsSmartLockEnabled(false)
                        .build(), REQUEST_CODE
                );
            }
        });

    }

    private void register(String email, String password) {
        try {
            mfirebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(RegistroActivity.this, "Error de registro", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(RegistroActivity.this, "Error de registro", Toast.LENGTH_SHORT).show();
        }


    }


}
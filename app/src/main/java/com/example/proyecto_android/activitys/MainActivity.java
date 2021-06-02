package com.example.proyecto_android.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.proyecto_android.R;
import com.example.proyecto_android.adapters.ListaMonumentosAdapter;
import com.example.proyecto_android.api.moumentos.ApiMonumentosUtils;
import com.example.proyecto_android.api.moumentos.ApiMonumetosService;
import com.example.proyecto_android.fragments.ClimaFragment;
import com.example.proyecto_android.fragments.DetalleMonumentoFragment;
import com.example.proyecto_android.fragments.FavoritosFragment;
import com.example.proyecto_android.fragments.ListaFragment;
import com.example.proyecto_android.fragments.MapaFragment;
import com.example.proyecto_android.model.ComunicaFragments;
import com.example.proyecto_android.model.Favorito;
import com.example.proyecto_android.model.Monumento;
import com.example.proyecto_android.model.Usuario;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener, ComunicaFragments {


    private AppBarConfiguration mAppBarConfiguration;
    private ListaFragment listaFragment;
    private MapaFragment mapaFragment;
    private FavoritosFragment favoritosFragment;
    private ClimaFragment climaFragment;
    private Usuario usuario;
    private List<Monumento> monumentos;
    private List<Favorito> favoritos;
    private DrawerLayout drawer;
    private ApiMonumetosService apiMonumetosService;
    private ImageView imagenUsuario;
    private TextView nombreUsuario;
    private TextView emailUsuario;

    DetalleMonumentoFragment detalleMonumentoFragment;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiMonumetosService = ApiMonumentosUtils.getClient();

        listaFragment = new ListaFragment();
        mapaFragment = new MapaFragment();
        favoritosFragment = new FavoritosFragment();
        climaFragment = new ClimaFragment();

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        
        imagenUsuario = findViewById(R.id.imagenUsuario);
        nombreUsuario = findViewById(R.id.nombreUsuario);
        emailUsuario = findViewById(R.id.emailUsuario);

        /*if(usuario != null){
            nombreUsuario.setText(usuario.getNombre());
            emailUsuario.setText(usuario.getEmail());
        }*/

        /*imagenUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("/image");
                startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicación"), 10);
            }
        });*/



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        apiMonumetosService = ApiMonumentosUtils.getClient();
        apiMonumetosService.getMonumentos().enqueue(new Callback<List<Monumento>>() {
            @Override
            public void onResponse(Call<List<Monumento>> call, Response<List<Monumento>> response) {
                monumentos = response.body();
                cambiaFragment(R.id.nav_host_fragment, listaFragment);
                apiMonumetosService.getFavoritos(usuario.getId()).enqueue(new Callback<List<Favorito>>() {
                    @Override
                    public void onResponse(Call<List<Favorito>> call, Response<List<Favorito>> response) {
                        favoritos = response.body();
                        if (favoritos != null){
                            for (Monumento m : monumentos) {
                                for (Favorito f : favoritos) {
                                    if (m.getIdNotes() == f.getMonumento().getIdNotes()){
                                        m.setFavorito(true);
                                    }
                                }
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Favorito>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "fallido", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Monumento>> call, Throwable t) {
                Log.d("LOg", t.toString());
                Toast.makeText(getApplicationContext(), "fallido", Toast.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (usuario.getEmail().equals("admin@admin.com")){
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void cambiaFragment(int id, Fragment fragment){
        //Sustituye un fragment por otro
        getSupportFragmentManager().beginTransaction()
                .replace(id, fragment)
                .addToBackStack(fragment.getTag())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            /*case R.id.action_configuracion:
                Intent intent4 = new Intent(this, PreferenceActivity.class);
                intent4.putExtra("usuario", usuario);
                startActivityForResult(intent4, 0);*/
            case R.id.action_gestion_monumentos:
                Intent intent5 = new Intent(this, MonumentosActivity.class);
                intent5.putExtra("usuario", usuario);
                startActivityForResult(intent5, 0);
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public void cerrarSesion(View view){
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:

                break;
            case R.id.nav_lista:
                apiMonumetosService.getFavoritos(usuario.getId()).enqueue(new Callback<List<Favorito>>() {
                    @Override
                    public void onResponse(Call<List<Favorito>> call, Response<List<Favorito>> response) {
                        favoritos = response.body();
                        if (favoritos != null){
                            for (Monumento m : monumentos) {
                                for (Favorito f : favoritos) {
                                    if (m.getIdNotes() == f.getMonumento().getIdNotes()){
                                        m.setFavorito(true);
                                    }
                                }
                            }
                        }
                        cambiaFragment(R.id.nav_host_fragment, listaFragment);
                    }

                    @Override
                    public void onFailure(Call<List<Favorito>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "fallido", Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case R.id.nav_mapa:
                cambiaFragment(R.id.nav_host_fragment, mapaFragment);
                break;
            case R.id.nav_favoritos:
                apiMonumetosService.getFavoritos(usuario.getId()).enqueue(new Callback<List<Favorito>>() {
                    @Override
                    public void onResponse(Call<List<Favorito>> call, Response<List<Favorito>> response) {
                        favoritos = response.body();
                        if (favoritos != null){
                            for (Monumento m : monumentos) {
                                for (Favorito f : favoritos) {
                                    if (m.getIdNotes() == f.getMonumento().getIdNotes()){
                                        m.setFavorito(true);
                                    }
                                }
                            }
                        }
                        cambiaFragment(R.id.nav_host_fragment, favoritosFragment);
                    }

                    @Override
                    public void onFailure(Call<List<Favorito>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "fallido", Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case R.id.nav_clima:
                cambiaFragment(R.id.nav_host_fragment, climaFragment);
                break;
            case R.id.menu_opcion01:
                Intent intent4 = new Intent(this, PreferenceActivity.class);
                intent4.putExtra("usuario", usuario);
                startActivityForResult(intent4, 0);
                break;
            case R.id.menu_opcion02:
                AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<Monumento> getMonumentos() {
        return monumentos;
    }

    public List<Favorito> getFavoritos() {
        return favoritos;
    }

    @Override
    public void enviarMonumento(Monumento monumento) {
        detalleMonumentoFragment = new DetalleMonumentoFragment();
        //objeto bundle para transportar informacion
        Bundle bundleEnvio = new Bundle();
        //Enviar oibjetoi con serializable
        bundleEnvio.putSerializable("objeto", monumento);
        detalleMonumentoFragment.setArguments(bundleEnvio);
        //abrir fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, detalleMonumentoFragment);
        fragmentTransaction.commit();
    }
}
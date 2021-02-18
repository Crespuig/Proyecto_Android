package com.example.proyecto_android.activitys;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_android.R;

import com.example.proyecto_android.adapters.GestionMonumentoAdapter;
import com.example.proyecto_android.bbdd.MiBD;
import com.example.proyecto_android.dao.MonumentoDAO;
import com.example.proyecto_android.dao.UsuarioDAO;
import com.example.proyecto_android.fragments.FavoritosFragment;
import com.example.proyecto_android.fragments.ListaFragment;
import com.example.proyecto_android.fragments.MapaFragment;
import com.example.proyecto_android.model.JsonToObject;
import com.example.proyecto_android.model.Monumento;
import com.example.proyecto_android.model.Usuario;
import com.example.proyecto_android.model.Utils;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.internal.NavigationMenuItemView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener{


    private AppBarConfiguration mAppBarConfiguration;
    private ListaFragment listaFragment;
    private MapaFragment mapaFragment;
    private FavoritosFragment favoritosFragment;
    Usuario usuario;
    private DrawerLayout drawer;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaFragment = new ListaFragment();
        mapaFragment = new MapaFragment();
        favoritosFragment = new FavoritosFragment();

        usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuario = (Usuario) usuarioDAO.search(usuario);
        /*TextView nombreUsuario = (TextView) findViewById(R.id.nombreUsuario);
        nombreUsuario.setText(u.getNombre());
        TextView emailusuario = (TextView) findViewById(R.id.emailUsuario);
        emailusuario.setText(u.getEmail());*/

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_lista, R.id.nav_mapa, R.id.nav_favoritos, R.id.nav_clima, R.id.nav_transporte, R.id.nav_estadisticas, R.id.menu_opcion01,
                R.id.menu_opcion02)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        NavigationMenuItemView itemCerrarSesion = (NavigationMenuItemView) findViewById(R.id.menu_opcion02);*/


        try {
            String jsonString = Utils.leerJson(this, "opendata1259927732622104222.JSON");
            ArrayList<Monumento> monumentos = JsonToObject.jsonToMonumetList(jsonString);
            for (Monumento m: monumentos){
                MonumentoDAO monumentoDAO = new MonumentoDAO(this);
                ContentValues reg = new ContentValues();
                reg.put(MonumentoDAO.C_COLUMNA_MONUMENTOS_IDNOTES, m.getIdNotes());
                reg.put(MonumentoDAO.C_COLUMNA_NOMBRE, m.getName());
                reg.put(MonumentoDAO.C_COLUMNA_NUMPOL,m.getNumPol());
                reg.put(MonumentoDAO.C_COLUMNA_CODVIA, m.getCodVia());
                reg.put(MonumentoDAO.C_COLUMNA_TELEFONO, m.getTelefono());
                reg.put(MonumentoDAO.C_COLUMNA_RUTA, m.getRuta());
                double lat = m.getLatitud() * Math.PI / 180.0;
                reg.put(MonumentoDAO.C_COLUMNA_LATITUD,lat);
                double lon = m.getLatitud() * Math.PI / 180.0;
                reg.put(MonumentoDAO.C_COLUMNA_LONGITUD,lon);

                monumentoDAO.insert(reg);
            }
            String jsonImg = Utils.leerJson(this, "monument_imgs.json");
            ArrayList<Monumento> imgMonumentos = JsonToObject.jsonToImageMonumentsList(jsonImg);
            for (Monumento m: imgMonumentos){
                MonumentoDAO monumentoDAO = new MonumentoDAO(this);
                Cursor c = monumentoDAO.getRegistro(m.getIdNotes());
                String nombre = c.getString(1);
                String numPol = c.getString(2);
                int codVia = c.getInt(3);
                String telefono = c.getString(4);
                int ruta = c.getInt(5);
                float latitud = c.getFloat(6);
                float longitud = c.getFloat(7);
                ContentValues reg = new ContentValues();
                reg.put(MonumentoDAO.C_COLUMNA_MONUMENTOS_IDNOTES, m.getIdNotes());
                reg.put(MonumentoDAO.C_COLUMNA_NOMBRE,nombre);
                reg.put(MonumentoDAO.C_COLUMNA_NUMPOL,numPol);
                reg.put(MonumentoDAO.C_COLUMNA_CODVIA, codVia);
                reg.put(MonumentoDAO.C_COLUMNA_TELEFONO, telefono);
                reg.put(MonumentoDAO.C_COLUMNA_RUTA, ruta);
                reg.put(MonumentoDAO.C_COLUMNA_LATITUD, latitud);
                reg.put(MonumentoDAO.C_COLUMNA_LONGITUD, longitud);
                reg.put(MonumentoDAO.C_COLUMNA_IMAGEN, m.getImagen());

                monumentoDAO.update(reg);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        cambiaFragment(R.id.nav_host_fragment, listaFragment);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            case R.id.action_configuracion:
                Intent intent4 = new Intent(this, PreferenceActivity.class);
                intent4.putExtra("usuario", usuario);
                startActivityForResult(intent4, 0);
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
                cambiaFragment(R.id.nav_host_fragment, listaFragment);
                break;
            case R.id.nav_mapa:
                cambiaFragment(R.id.nav_host_fragment, mapaFragment);
                break;
            case R.id.nav_favoritos:
                cambiaFragment(R.id.nav_host_fragment, favoritosFragment);
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
}
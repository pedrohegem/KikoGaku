package com.example.proyecto;

import android.content.Intent;
import static java.lang.Thread.sleep;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.proyecto.Json.JsonSingleton;
import com.example.proyecto.Json.Montana;
import com.example.proyecto.Json.Municipio;
import com.example.proyecto.Room.DAO.UsuarioDAO;
import com.example.proyecto.Room.Modelo.Usuario;
import com.example.proyecto.databinding.ActivityMainBinding;
import com.example.proyecto.ui.Eventos.CrearEventoActivity;
import com.example.proyecto.ui.Localizaciones.LocalizacionesActivity;
import com.google.gson.stream.JsonReader;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.proyecto.Room.AppDatabase;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        validarConexion();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        // Cargamos la base de datos (en la primera vez, se crea)
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database.db").build();

        // Cargamos los JSON en la base de datos
        cargarJSON_en_Singleton();

        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio,R.id.nav_eventos, R.id.nav_perfil, R.id.nav_ajustes)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CrearEventoActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.localizaciones) {
            // Actividad de localizaciones
            startActivity(new Intent(this, LocalizacionesActivity.class));
            return true;
        }
        else if (id == R.id.cerrarSesion) {
            // Se invoca un metodo del DAO usuario que se encarga de modificar el estadoConectado a false del usuario. De esta forma, al
            UsuarioDAO usuarioDAO = AppDatabase.getInstance(getApplicationContext()).usuarioDAO();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    AppDatabase.getInstance(getApplicationContext()).usuarioDAO().activarEstadoConexion(false, AppDatabase.getUsuario().getIdu());
                    // Se pone a null el usuario del singleton AppDataBase
                    AppDatabase.setUsuario(null);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Se ha cerrado sesión", Toast.LENGTH_SHORT).show();
                            // Se inicia la actividad Main para comprobar que, efectivamente, el usuario ha cerrado sesión
                            startActivity(new Intent(getApplicationContext(), InicioSesion.class));
                        }
                    });

                }
            }).start();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void cargarJSON_en_Singleton(){
        // CÓDIGO DE MONTAÑAS
        JsonReader readerMontanas = new JsonReader(new InputStreamReader(getResources().openRawResource(R.raw.codmontanas)));
        List<Montana> montanaList = Arrays.asList(new Gson().fromJson(readerMontanas, Montana[].class));

        for (Montana m: montanaList) {
            JsonSingleton.getInstance().montanaMap.put(m.getNombre(), new Montana(m.getLatitud(), m.getLongitud(), m.getNombre()));
        }

        // CÓDIGO DE MUNICIPIOS
        JsonReader readerMunicipios = new JsonReader(new InputStreamReader(getResources().openRawResource(R.raw.codmunicipios)));
        List<Municipio> municipioList = Arrays.asList(new Gson().fromJson(readerMunicipios, Municipio[].class));

        for (Municipio m: municipioList) {
            JsonSingleton.getInstance().municipioMap.put(m.getMunicipio(), new Municipio(m.getCodigo(), m.getMunicipio(), m.getProvincia()));
        }
    }

    public void validarConexion(){
        AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
        final UsuarioDAO usuarioDAO = appDatabase.usuarioDAO();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Usuario usuario = usuarioDAO.usuarioConectado(true);
                // Si no hay ningun usuario con el campo 'conectado' a true, entonces nos dirigimos al iniciar sesion
                if(usuario == null){
                    startActivity(new Intent(MainActivity.this, InicioSesion.class));
                }
                else{
                    // Si existe un usuario conectado a la aplicacion, entonces se añade en el Singleton
                    runOnUiThread(() -> AppDatabase.getInstance(getApplicationContext()).setUsuario(usuario));

                }
            }
        }).start();
    }

    public void setDayLight(){
        // Para obtener la configuracion que el usuario ha introducido previamente en la app, se obtiene el objeto SharedPreferences
        SharedPreferences sp = getSharedPreferences("preferences", this.MODE_PRIVATE);
        int tema = sp.getInt("Theme", 1);
        Log.d("NUMERO MODO", String.valueOf(tema));
        if(tema == 0){ // Modo claro
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO); // método que da error
        }
        else{ // Modo oscuro
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Se llama al comienzo de la actividad al setDayLight() para saber si el modo claro está activado
        setDayLight();
    }
}
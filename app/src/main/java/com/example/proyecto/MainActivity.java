package com.example.proyecto;

import android.content.Context;
import android.content.Intent;


import android.content.SharedPreferences;
import android.os.Bundle;


import com.example.proyecto.repository.UserRepository;
import com.example.proyecto.utils.JsonSingleton;
import com.example.proyecto.models.Montana;
import com.example.proyecto.models.Municipio;
import com.example.proyecto.repository.room.DAO.UsuarioDAO;
import com.example.proyecto.models.Usuario;
import com.example.proyecto.databinding.ActivityMainBinding;
import com.example.proyecto.ui.Eventos.CrearEventoActivity;
import com.example.proyecto.ui.Localizaciones.LocalizacionesActivity;

import com.example.proyecto.viewmodels.BorrarPerfilViewModel;
import com.example.proyecto.viewmodels.MainUsuarioViewModel;
import com.google.gson.stream.JsonReader;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.proyecto.repository.room.AppDatabase;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatDelegate;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private Context mContext;

    private MainUsuarioViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();

        AppContainer appContainer = ((MyApplication) mContext.getApplicationContext()).appContainer;
        mViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.mainUsuarioViewModelFactory).get(MainUsuarioViewModel.class);

        final Observer<Usuario> observer = new Observer<Usuario>() {
            @Override
            public void onChanged(final Usuario user) {
                Log.d(TAG, "Data changed on observer...");
                if(user == null) {
                    startActivity(new Intent(MainActivity.this, InicioSesion.class));
                }
            }
        };

        mViewModel.getUser().observeForever(observer);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        // Cargamos los JSON en la base de datos
        cargarJSON_en_Singleton();

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
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

            new Thread(new Runnable() {
                @Override
                public void run() {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mViewModel.activarEstadoConection(false, mViewModel.getUsuarioConectado().getIdu());
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
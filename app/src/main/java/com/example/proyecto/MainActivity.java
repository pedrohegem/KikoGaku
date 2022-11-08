package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;

import com.example.proyecto.Json.JsonSingleton;
import com.example.proyecto.Json.Montana;
import com.example.proyecto.Json.Municipio;
import com.example.proyecto.Room.DAO.UsuarioDAO;
import com.example.proyecto.Room.Modelo.Usuario;
import com.google.gson.stream.JsonReader;

import android.util.Log;
import android.view.View;
import android.view.Menu;

import com.example.proyecto.Room.AppDatabase;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.proyecto.databinding.ActivityMainBinding;
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
        // -- La primera comprobación antes de hacer nada es ver si un usuario está conectado a la aplicación --
        Log.d("UNO", "----------- INICIO MAIN ACTIVITY---------------");


        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        // Cargamos los JSON en la base de datos
        cargarJSON_en_Singleton();

        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
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
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Log.d("PITO",timestamp.toString());

        // -- CÓDIGO ENCARGADO DE CARGAR EL JSON CON LOS CÓDIGOS DE LAS MONTAÑAS
        JsonSingleton jsonSingleton = JsonSingleton.getInstance();

        // CÓDIGO DE MONTAÑAS
        JsonReader readerMontanas = new JsonReader(new InputStreamReader(getResources().openRawResource(R.raw.codmontanas)));
        List<Montana> montanaList = Arrays.asList(new Gson().fromJson(readerMontanas, Montana[].class));
        Map<String,String> montanaMap = new TreeMap<String, String>();

        for (Montana m: montanaList) {
            montanaMap.put(m.getNombre(), m.getCodigo());
        }

        // CÓDIGO DE MUNICIPIOS
        JsonReader readerMunicipios = new JsonReader(new InputStreamReader(getResources().openRawResource(R.raw.codmunicipios)));
        List<Municipio> municipioList = Arrays.asList(new Gson().fromJson(readerMunicipios, Municipio[].class));
        Map<String,Municipio> municipioMap = new TreeMap<String, Municipio>();

        for (Municipio m: municipioList) {
            municipioMap.put(m.getMunicipio(), new Municipio(m.getCodigo(), m.getMunicipio(), m.getProvincia()));
        }
        Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
        Log.d("PITO2",timestamp2.toString());
    }

}
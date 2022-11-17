package com.example.proyecto.ui.Eventos;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.proyecto.R;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.proyecto.databinding.ActivityCrearEventoBinding;

public class CrearEventoActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityCrearEventoBinding binding;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCrearEventoBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_backarrow_foreground);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_crear_evento);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_crear_evento);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setDayLight(){
        // Para obtener la configuracion que el usuario ha introducido previamente en la app, se obtiene el objeto SharedPreferences
        SharedPreferences sp = getSharedPreferences("preferences", this.MODE_PRIVATE);
        int tema = sp.getInt("Theme", 1);
        Log.d("NUMERO MODO", String.valueOf(tema));
        if(tema == 0){ // Modo claro
            Log.d("DENTRO CLARO", "AAAAAAAAAAAAAAAAAAAAAAA");
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
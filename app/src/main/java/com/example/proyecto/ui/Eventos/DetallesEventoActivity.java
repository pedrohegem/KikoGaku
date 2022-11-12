package com.example.proyecto.ui.Eventos;

import android.app.ActionBar;
import android.os.Bundle;

import com.example.proyecto.databinding.ActivityDetallesEventoBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.example.proyecto.R;

public class DetallesEventoActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityDetallesEventoBinding binding;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetallesEventoBinding.inflate(getLayoutInflater());
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

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_borrate);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_borrate);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public int getIdEvento(){
        return getIntent().getIntExtra("idEvento", 0);
    }

    public String getUbicacion() {return getIntent().getStringExtra("ubicacionEvento");}

}
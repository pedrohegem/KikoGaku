package com.example.proyecto.ui.Localizaciones;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto.R;
import com.example.proyecto.databinding.ActivityDetalleLocalizacionBinding;

public class DetalleLocalizacionActivity extends AppCompatActivity {

    private ActivityDetalleLocalizacionBinding binding;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_localizacion);


        // Se establece el contexto
        mContext = getApplicationContext();

        // Se establece la toolbar y el comportamiento del back

        binding = ActivityDetalleLocalizacionBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        getSupportActionBar().setTitle("Tiempo en " + intent.getStringExtra("municipio"));

        setContentView(binding.getRoot());
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
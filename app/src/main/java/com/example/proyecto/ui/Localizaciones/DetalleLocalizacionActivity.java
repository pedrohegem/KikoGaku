package com.example.proyecto.ui.Localizaciones;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;

import com.example.proyecto.R;
import com.example.proyecto.models.Evento;
import com.example.proyecto.models.Location;
import com.example.proyecto.models.Weather;
import com.example.proyecto.databinding.ActivityDetalleLocalizacionBinding;
import com.example.proyecto.repository.LocationRepository;
import com.example.proyecto.repository.networking.APIManager;
import com.example.proyecto.repository.networking.APIManagerDelegate;
import com.example.proyecto.repository.room.AppDatabase;

public class DetalleLocalizacionActivity extends AppCompatActivity implements APIManagerDelegate {
    private String TAG = "DetallesLocationActivity";


    private ActivityDetalleLocalizacionBinding binding;
    private Context mContext;
    private TextView textViewTemp, temperaturaMaxMin, localidadTiempo, descripcionTiempo, viento, humedad, sensTermica;
    ImageView iconoTiempo;
    private LocationRepository locationRepository;
    private String ubicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_localizacion);

        mContext = getApplicationContext();

        // Se establece la toolbar y el comportamiento del back

        binding = ActivityDetalleLocalizacionBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        ubicacion = intent.getStringExtra("ubicacion");
        getSupportActionBar().setTitle("Tiempo en " + ubicacion);

        setContentView(binding.getRoot());
        textViewTemp = binding.textViewTemperatura;
        temperaturaMaxMin = binding.textViewTemperaturas;
        localidadTiempo = binding.textViewUbicacion;
        descripcionTiempo = binding.textViewDescD;
        viento = binding.textViewVientoP;
        humedad = binding.textViewHumedadP;
        sensTermica = binding.textViewSensTermP;
        iconoTiempo = binding.image2;

        this.locationRepository = LocationRepository.getInstance(AppDatabase.getInstance(mContext).locationDAO());

        final Observer<Location> observer = new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                Log.d(TAG, "Data changed on observer...");
                if(location != null) {
                    updateUI(location);
                }
            }
        };

        locationRepository.getLocation(ubicacion).observeForever(observer);
    }

    public void updateUI (Location location) {
        runOnUiThread(() -> {
            textViewTemp.setText(String.valueOf(location.getTemperatura()));
            temperaturaMaxMin.setText(location.getTempMinima() +"º / "+ location.getTempMaxima() +"º");
            localidadTiempo.setText(location.getUbicacion());
            descripcionTiempo.setText(location.getDescEstadoTiempo());
            viento.setText(String.valueOf(location.getVelocidadViento()));
            humedad.setText(String.valueOf(location.getHumedad()));
            sensTermica.setText(location.getSensTermica() + "º");

            switch (location.getGifResource()){
                case 0://Error
                    Log.e("Error Weather", "onGetWeatherSuccess: No se ha obtenido el estado del tiempo correctamente");
                    break;
                case 1://Tormenta
                    iconoTiempo.setImageResource(R.drawable.itormenta);
                    break;
                case 2://Llovizna
                    iconoTiempo.setImageResource(R.drawable.illovizna);
                    break;
                case 3://Lluvia
                    iconoTiempo.setImageResource(R.drawable.illuvia);
                    break;
                case 4://Nieve
                    iconoTiempo.setImageResource(R.drawable.inieve);
                    break;
                case 5://Niebla
                    iconoTiempo.setImageResource(R.drawable.iniebla);
                    break;
                case 6://Nubes
                    iconoTiempo.setImageResource(R.drawable.inubes);
                    break;
                case 7://Sol
                    iconoTiempo.setImageResource(R.drawable.isol);
                    break;
                default:
                    iconoTiempo.setImageResource(R.drawable.isol);
                    break;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onGetWeatherSuccess(Weather weather) {

    }

    @Override
    public void onGetWeatherFailure() {
        String noPerms = "No se ha podido acceder a la API. Revisa tu conexión a internet.";
        Toast.makeText(mContext, noPerms, Toast.LENGTH_LONG).show();
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
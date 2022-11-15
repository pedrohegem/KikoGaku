package com.example.proyecto.ui.Localizaciones;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto.R;
import com.example.proyecto.Room.Modelo.Weather;
import com.example.proyecto.databinding.ActivityDetalleLocalizacionBinding;
import com.example.proyecto.utils.APIManager;
import com.example.proyecto.utils.APIManagerDelegate;

public class DetalleLocalizacionActivity extends AppCompatActivity implements APIManagerDelegate {

    private ActivityDetalleLocalizacionBinding binding;
    private Context mContext;
    private APIManager apiManager;
    private TextView textViewTemp, temperaturaMaxMin, localidadTiempo, descripcionTiempo, viento, humedad, sensTermica;
    ImageView iconoTiempo;

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
        getSupportActionBar().setTitle("Tiempo en " + intent.getStringExtra("ubicacion"));

        setContentView(binding.getRoot());

        textViewTemp = binding.textViewTemperatura;
        temperaturaMaxMin = binding.textViewTemperaturas;
        localidadTiempo = binding.textViewUbicacion;
        descripcionTiempo = binding.textViewDescD;
        viento = binding.textViewVientoP;
        humedad = binding.textViewHumedadP;
        sensTermica = binding.textViewSensTermP;
        iconoTiempo = binding.image2;

        apiManager = new APIManager(this);
        apiManager.getEventWeather(getIntent().getStringExtra("ubicacion"));
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onGetWeatherSuccess(Weather weather) {
        runOnUiThread(() -> {
            switch (weather.gifResource){
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
            textViewTemp.setText(String.valueOf(weather.temperatura));
            temperaturaMaxMin.setText(weather.tempMinima +"º / "+ weather.tempMaxima +"º");
            localidadTiempo.setText(weather.ciudad);
            descripcionTiempo.setText(weather.descEstadoTiempo);
            viento.setText(String.valueOf(weather.velocidadViento));
            humedad.setText(String.valueOf(weather.humedad));
            sensTermica.setText(weather.sensTermica + "º");
        });
    }

    @Override
    public void onGetWeatherFailure() {
        String noPerms = "No se ha podido acceder a la API. Revisa tu conexión a internet.";
        Toast.makeText(mContext, noPerms, Toast.LENGTH_LONG).show();
    }
}
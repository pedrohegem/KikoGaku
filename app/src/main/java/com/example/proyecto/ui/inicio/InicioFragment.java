package com.example.proyecto.ui.inicio;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.os.IResultReceiver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.proyecto.MainActivity;
import com.example.proyecto.databinding.FragmentInicioBinding;
import com.example.proyecto.ui.Eventos.CrearEventoActivity;
import com.example.proyecto.ui.Eventos.DetallesEventoActivity;
import com.example.proyecto.utils.APIManagerDelegate;
import com.example.proyecto.R;
import com.example.proyecto.Room.Modelo.Weather;
import com.example.proyecto.ui.ListaEventos.ListaEventosFragment;
import com.example.proyecto.utils.APIManager;

import pl.droidsonroids.gif.GifImageView;

public class InicioFragment extends Fragment implements APIManagerDelegate {

    public TextView textViewCiudad, textViewTemp, textViewTempMaxMin, textViewDesc;
    GifImageView gifImage;
    private Double latitud;
    private Double longitud;

    private FragmentInicioBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        textViewCiudad = binding.textViewCiudad;
        textViewTemp = binding.textViewTemp;
        textViewDesc = binding.textViewDesc;
        textViewTempMaxMin = binding.textViewTempMaxMin;
        gifImage = binding.gifImageView;
        APIManager apiManager = new APIManager(this);
        getLocationCoords();
        apiManager.getEventWeather(latitud, longitud);



        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Fragment childFragment = new ListaEventosFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_ListaEventos, childFragment).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void getLocationCoords() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            LocationManager lm = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if(location == null) { // Network provider no funciona. Se establecen las coordenadas de Madrid por defecto
                String noPerms = "No se ha podido acceder a tu localización. Se ha establecido Madrid por defecto. Compruebe el estado del GPS.";
                Toast.makeText(getContext(), noPerms, Toast.LENGTH_LONG).show();
                latitud = 40.4165;
                longitud = -3.7026;
            } else {
                latitud = location.getLatitude();
                longitud = location.getLongitude();
            }
        }
    }

    @Override
    public void onGetWeatherSuccess(Weather weather) {

        switch (weather.gifResource){
            case 0://Error
                Log.e("Error Weather", "onGetWeatherSuccess: No se ha obtenido el estado del tiempo correctamente");
                break;
            case 1://Tormenta
                gifImage.setImageResource(R.drawable.gif_tormenta);
                break;
            case 2://Llovizna
                gifImage.setImageResource(R.drawable.gif_lluvia);
                break;
            case 3://Lluvia
                gifImage.setImageResource(R.drawable.gif_lluvia);
                break;
            case 4://Nieve
                break;
            case 5://Niebla
                gifImage.setImageResource(R.drawable.gif_sol_niebla);
                break;
            case 6://Nubes
                gifImage.setImageResource(R.drawable.gif_sol_nubes);
                break;
            case 7://Sol
                gifImage.setImageResource(R.drawable.gif_sol);
                break;
            default:
                gifImage.setImageResource(R.drawable.gif_sol);
                break;
        }
        textViewCiudad.setText(weather.ciudad);
        Log.d("TEMP", Integer.toString(weather.temperatura));
        textViewTemp.setText(weather.temperatura +"º");
        textViewDesc.setText(weather.getDescEstadoTiempoMay());//Estado del tiempo con ña primera letra en Mayusculas de cada palabra
        textViewTempMaxMin.setText(weather.tempMaxima + "º / " + weather.tempMinima + "º");
    }

    @Override
    public void onGetWeatherFailure() {
        String noPerms = "No se ha podido acceder al tiempo de la localización actual. Comprueba tu conexión a Internet";
        Toast.makeText(getContext(), noPerms, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity cea = (MainActivity) getActivity();
        cea.setDayLight();
    }
}
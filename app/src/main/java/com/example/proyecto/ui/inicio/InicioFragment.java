package com.example.proyecto.ui.inicio;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.proyecto.MainActivity;
import com.example.proyecto.databinding.FragmentInicioBinding;
import com.example.proyecto.ui.Eventos.DetallesEventoActivity;
import com.example.proyecto.utils.APIManagerDelegate;
import com.example.proyecto.R;
import com.example.proyecto.Room.Modelo.Weather;
import com.example.proyecto.ui.ListaEventos.ListaEventosFragment;
import com.example.proyecto.utils.APIManager;

public class InicioFragment extends Fragment implements APIManagerDelegate {

    private TextView textViewCiudad;
    private TextView textViewTemp;
    private TextView textViewTempMin;
    private TextView textViewTempMax;
    private TextView textViewTempDesc;

    private Double latitud;
    private Double longitud;


    private FragmentInicioBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        APIManager apiManager = new APIManager(this);
        getLocationCoords();
        apiManager.getEventWeather(latitud, longitud);
        textViewCiudad = binding.textViewCiudad;
        textViewTemp = binding.textViewTemp;
        textViewTempMin = binding.textViewTempMin;
        textViewTempMax = binding.textViewTempMax;
        textViewTempDesc = binding.textViewDesc;
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

        Log.d("WEATHER", weather.ciudad);
        textViewCiudad.setText(weather.ciudad);
        textViewTemp.setText(weather.temperatura + "º");
        textViewTempMin.setText(weather.tempMinima + "º /");
        textViewTempMax.setText(weather.tempMaxima + "º");
        textViewTempDesc.setText(weather.descEstadoTiempo);
    }

    @Override
    public void onGetWeatherFailure() {
        String noPerms = "No se ha podido acceder al tiempo de la localización actual. Comprueba tu conexión a Internet";
        Toast.makeText(getContext(), noPerms, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity ma = (MainActivity) getActivity();
        ma.setDayLight();
    }
}
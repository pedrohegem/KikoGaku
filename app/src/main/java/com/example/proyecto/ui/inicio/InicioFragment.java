package com.example.proyecto.ui.inicio;



import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyecto.APIManagerDelegate;
import com.example.proyecto.R;
import com.example.proyecto.Json.Montana;
import com.example.proyecto.Room.Modelo.Weather;
import com.example.proyecto.databinding.FragmentInicioBinding;
import com.example.proyecto.ui.ListaEventos.EventoFragment;
import com.example.proyecto.utils.APIManager;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class InicioFragment extends Fragment implements APIManagerDelegate {

    private TextView textViewCiudad;
    private TextView textViewTemp;
    private TextView textViewTempMin;
    private TextView textViewTempMax;
    private TextView textViewTempDesc;

    private FragmentInicioBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        APIManager apiManager = new APIManager(this);
        apiManager.getEventWeather(getLocality(getLocationCoords()));


        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Fragment childFragment = new EventoFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_ListaEventos, childFragment).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public Double[] getLocationCoords() {

        // Obtain latitude and longitude from current location
        LocationManager lm = (LocationManager)getActivity().getSystemService(getContext().LOCATION_SERVICE);
        @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        return new Double[] {location.getLatitude(), location.getLongitude()};
    }

    public String getLocality(Double[] coords) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(coords[0], coords[1], 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert addresses != null;
        return addresses.get(0).getLocality();
    }

    @Override
    public void onGetWeatherSuccess(Weather weather) {
        Log.d("CALLBACK", weather.ciudad);

        textViewCiudad = binding.textViewCiudad;
        textViewTemp = binding.textViewTemp;
        textViewTempMin = binding.textViewTempMin;
        textViewTempMax = binding.textViewTempMax;
        textViewTempDesc = binding.textViewDesc;

        textViewCiudad.setText(weather.ciudad);
        textViewTemp.setText(weather.temperatura + "º");
        textViewTempMin.setText(weather.tempMinima + "º /");
        textViewTempMax.setText(weather.tempMaxima + "º");
        textViewTempDesc.setText(weather.descEstadoTiempo);
    }
}
package com.example.proyecto.ui.inicio;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.proyecto.AppContainer;
import com.example.proyecto.MainActivity;
import com.example.proyecto.MyApplication;
import com.example.proyecto.databinding.FragmentInicioBinding;
import com.example.proyecto.models.Location;
import com.example.proyecto.repository.LocationRepository;
import com.example.proyecto.repository.networking.APIManagerDelegate;
import com.example.proyecto.R;
import com.example.proyecto.models.Weather;
import com.example.proyecto.repository.room.AppDatabase;
import com.example.proyecto.ui.ListaEventos.ListaEventosFragment;
import com.example.proyecto.repository.networking.APIManager;
import com.example.proyecto.viewmodels.ModificarEventoViewModel;
import com.example.proyecto.viewmodels.TiempoActualViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

public class InicioFragment extends Fragment implements APIManagerDelegate {

    private Context mContext;
    public TextView textViewCiudad, textViewTemp, textViewTempMaxMin, textViewDesc;
    GifImageView gifImage;
    private Double latitud;
    private Double longitud;

    private String TAG = "InicioFragment";

    private LocationRepository locationRepository;

    private FragmentInicioBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

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
        apiManager.getEventWeather(getUbicacionActual());


        Fragment childFragment = new ListaEventosFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_ListaEventos, childFragment).commit();


        //---------------------REFACTORIZACION------------------
        AppContainer appContainer = ((MyApplication) mContext.getApplicationContext()).appContainer;
        TiempoActualViewModel mViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.tiempoActualViewModelFactory).get(TiempoActualViewModel.class);

        mViewModel.getLocation(getUbicacionActual()).observe(getViewLifecycleOwner(), new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                Log.d(TAG, "Data changed on observer...");
                if(location != null) {
                    updateUI(location);
                }
            }
        });

        return root;
    }

    public void updateUI(Location location) {

        switch (location.getGifResource()){
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
        textViewCiudad.setText(location.getUbicacion());
        Log.d("TEMP", Integer.toString(location.getTemperatura()));
        textViewTemp.setText(location.getTemperatura() +"º");
        textViewDesc.setText(location.getDescEstadoTiempoMay());//Estado del tiempo con ña primera letra en Mayusculas de cada palabra
        textViewTempMaxMin.setText(location.getTempMaxima() + "º / " + location.getTempMinima() + "º");
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
            android.location.Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

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

    public String getUbicacionActual () {
        getLocationCoords();

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getContext(), Locale.getDefault());
        String city = "";
        try {
            addresses = geocoder. getFromLocation(latitud, longitud, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            city = addresses.get(0).getLocality();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return city;
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
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public void onGetWeatherFailure() {
        String noPerms = "No se ha podido acceder al tiempo de la localización actual. Comprueba tu conexión a Internet";
        Toast.makeText(mContext, noPerms, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity cea = (MainActivity) getActivity();
        cea.setDayLight();
    }
}
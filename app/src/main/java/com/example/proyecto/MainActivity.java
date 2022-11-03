package com.example.proyecto;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.proyecto.Json.JsonSingleton;
import com.example.proyecto.Json.Montana;
import com.example.proyecto.Json.Municipio;
import com.google.gson.stream.JsonReader;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.proyecto.Room.AppDatabase;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.proyecto.databinding.ActivityMainBinding;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    public String locality;

    // Código para gestionar el callback
    private final int PERMISSIONS_REQUEST = 0;

    // Array con todos los permisos necesarios por la app
    private String[] requiredPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private List<String> missingPermissions = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Gestión de permisos (antes de cargar la UI)
        fillMissingPermissions();
        if(!missingPermissions.isEmpty()) { // Si hay permisos sin conceder
            requestMissingPermissions();
        }
        //----------------------------------------------

        // Caso de uso: Obtener tiempo ubicación actual
        // TODO Obtener el tiempo de la API
        this.locality = getLocality(getLocationCoords());
        Log.d("Locality", "Locality: " + locality);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        // Cargamos la base de datos (en la primera vez, se crea)
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database.db").build();

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
                R.id.nav_inicio, R.id.nav_eventos, R.id.nav_perfil, R.id.nav_ajustes)
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

    public void fillMissingPermissions() {
        this.missingPermissions = new ArrayList<String>();
        for( String requiredPermission : this.requiredPermissions) {
            if(ActivityCompat.checkSelfPermission(this, requiredPermission) != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(requiredPermission);
            }
        }
    }

    public void requestMissingPermissions () {
        ActivityCompat.requestPermissions(this, missingPermissions.toArray(new String[missingPermissions.size()]), PERMISSIONS_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_REQUEST) {
            // Checking whether user granted the permission or not.
            if (permissions.length == this.missingPermissions.size() && grantResults.length == this.missingPermissions.size()) {
                boolean allPermissionsGranted = true;
                for( int grantResult : grantResults) {
                    if(grantResult == PackageManager.PERMISSION_DENIED) {
                        allPermissionsGranted = false;
                        break;
                    }
                }
            }
            else {
                String noPerms = "No se han concedido todos los permisos necesarios para el correcto funcionamiento de la aplicación.";
                Toast toastNoPerms = Toast.makeText(getApplicationContext(), noPerms, Toast.LENGTH_LONG);
                toastNoPerms.show();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void cargarJSON_en_Singleton() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Log.d("PITO", timestamp.toString());

        // -- CÓDIGO ENCARGADO DE CARGAR EL JSON CON LOS CÓDIGOS DE LAS MONTAÑAS
        JsonSingleton jsonSingleton = JsonSingleton.getInstance();

        // CÓDIGO DE MONTAÑAS
        JsonReader readerMontanas = new JsonReader(new InputStreamReader(getResources().openRawResource(R.raw.codmontanas)));
        List<Montana> montanaList = Arrays.asList(new Gson().fromJson(readerMontanas, Montana[].class));
        Map<String, String> montanaMap = new TreeMap<String, String>();

        for (Montana m : montanaList) {
            montanaMap.put(m.getNombre(), m.getCodigo());
        }

        // CÓDIGO DE MUNICIPIOS
        JsonReader readerMunicipios = new JsonReader(new InputStreamReader(getResources().openRawResource(R.raw.codmunicipios)));
        List<Municipio> municipioList = Arrays.asList(new Gson().fromJson(readerMunicipios, Municipio[].class));
        Map<String, Municipio> municipioMap = new TreeMap<String, Municipio>();

        for (Municipio m : municipioList) {
            municipioMap.put(m.getMunicipio(), new Municipio(m.getCodigo(), m.getMunicipio(), m.getProvincia()));
        }
        Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
        Log.d("PITO2", timestamp2.toString());
    }

    public Double[] getLocationCoords() {

        // Obtain latitude and longitude from current location
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        return new Double[] {location.getLatitude(), location.getLongitude()};
    }

    public String getLocality(Double[] coords) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(coords[0], coords[1], 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert addresses != null;
        return addresses.get(0).getLocality();
    }


}
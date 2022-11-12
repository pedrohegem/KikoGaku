package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LaunchActivity extends AppCompatActivity {

    private final int PERMISSIONS_REQUEST = 0;

    private String[] requiredPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private List<String> missingPermissions = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fillMissingPermissions();
        if(!missingPermissions.isEmpty()) {
            requestMissingPermissions();
        } else {
            showMainActivity();
        }
    }

    /**
     * Rellena el array missingPermissions con los permisos no concedidos pero necesarios para el funcionamiento de la app
     */
    public void fillMissingPermissions() {
        this.missingPermissions = new ArrayList<String>();
        for( String requiredPermission : this.requiredPermissions) {
            if(ActivityCompat.checkSelfPermission(this, requiredPermission) != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(requiredPermission);
            }
        }
    }

    /**
     * Solicita al usuario los permisos almacenados en missingPermissions
     */
    public void requestMissingPermissions () {
        ActivityCompat.requestPermissions(this, missingPermissions.toArray(new String[missingPermissions.size()]), PERMISSIONS_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST) {
            if (permissions.length == grantResults.length) {
                boolean allPermissionsGranted = true;
                for( int grantResult : grantResults) {
                    if(grantResult == PackageManager.PERMISSION_DENIED) {
                        allPermissionsGranted = false;
                        break;
                    }
                }
                if(allPermissionsGranted){ // Se han otorgado todos los permisos necesarios para el funcionamiento de la app
                    showMainActivity();

                } else {
                    String noPerms = "No se han concedido todos los permisos necesarios para el correcto funcionamiento de la aplicación.";
                    Toast.makeText(getApplicationContext(), noPerms, Toast.LENGTH_LONG).show();
                    finish();
                }

            }
            else {
                Log.e("Permissions", "ERROR: onRequestPermissionsResult");
            }
        }
    }

    /**
     * Intención que arranca la actividad principal de la aplicación
     */
    private void showMainActivity() {
        Log.d("LaunchActivity", "showMainActivity");
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
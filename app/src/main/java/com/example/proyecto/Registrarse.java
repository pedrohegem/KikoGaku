package com.example.proyecto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.proyecto.repository.UserRepository;
import com.example.proyecto.repository.room.AppDatabase;
import com.example.proyecto.repository.room.DAO.UsuarioDAO;
import com.example.proyecto.models.Usuario;
import com.example.proyecto.viewmodels.IniciarSesionViewModel;
import com.example.proyecto.viewmodels.RegistrarseViewModel;

public class Registrarse extends AppCompatActivity {

    EditText username, password;
    Button bRegistrarse;

    private Context mContext;
    private String TAG = "RegistrarseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        mContext = getApplicationContext();
        AppContainer appContainer = ((MyApplication) mContext.getApplicationContext()).appContainer;

        RegistrarseViewModel mViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.registrarseViewModelFactory).get(RegistrarseViewModel.class);

        bRegistrarse = findViewById(R.id.bRegistrarse);

        // Se encarga del registro de usuario.
        bRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Primero, se crea el objeto usuario con los credenciales
                final Usuario usuario = new Usuario(0, username.getText().toString(), password.getText().toString(), false);

                // Validamos los credenciales
                if(usuario.getUsername().isEmpty() || usuario.getPassword().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Rellena todos los campos de las credenciales", Toast.LENGTH_SHORT).show();
                }
                else{
                    // Obtenemos la base de datos
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // Creamos un nuevo hilo y registramos en la base de datos el usuario

                            mViewModel.registerUser(usuario);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Registrarse.this, "Usuario " + usuario.getUsername() + " registrado correctamente", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Registrarse.this, InicioSesion.class));
                                }
                            });

                        }
                    }).start();

                }
            }
        });
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
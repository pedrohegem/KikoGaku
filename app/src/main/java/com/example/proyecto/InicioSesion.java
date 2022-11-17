package com.example.proyecto;

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

import com.example.proyecto.Room.AppDatabase;
import com.example.proyecto.Room.DAO.UsuarioDAO;
import com.example.proyecto.Room.Modelo.Usuario;

public class InicioSesion extends AppCompatActivity {

    EditText username, password;
    Button bInicioSesion, bRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        bInicioSesion = findViewById(R.id.bIniciarSesion);
        bRegistrarse = findViewById(R.id.bRegistrarse);

        // Se encarga de iniciar sesión en la aplicación con unos credenciales
        bInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usernameText = username.getText().toString();
                final String passwordText = password.getText().toString();

                if(usernameText.isEmpty() || passwordText.isEmpty()){
                    Toast.makeText(InicioSesion.this, "Rellena todos los campos de las credenciales", Toast.LENGTH_SHORT).show();
                }
                else{
                    // Obtenemos la base de datos
                    AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
                    final UsuarioDAO usuarioDAO = appDatabase.usuarioDAO();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Usuario usuario = usuarioDAO.login(usernameText, passwordText);
                            // Comprobamos si está logueado en la aplicación
                            if (usuario == null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(InicioSesion.this, "Inválidas credenciales", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else{
                                // Insertamos una instancia de usuario en el Singleton de AppDatabase
                                usuario.setConectado(true);
                                appDatabase.setUsuario(usuario);

                                // Modificamos el estado 'conectado' del usuario en la base de datos a 'true' para controlar cuando se mantiene iniciada la sesión
                                usuarioDAO.activarEstadoConexion(true, usuario.getIdu());

                                // Iniciamos la actividad principal Main
                                runOnUiThread(() -> startActivity(new Intent(InicioSesion.this, MainActivity.class)));

                            }
                        }
                    }).start();
                }
            }
        });

        // Se encarga de iniciar una nueva actividad de Registro si se pulsa el botón de registrarse
        bRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InicioSesion.this, Registrarse.class));
            }
        });
    }

    // De esta forma evitamos que al hacer un back, se inicie la mainactivity con el inicio o la mainactivity con el perfil (Puesto que MainActivity es la main).
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
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
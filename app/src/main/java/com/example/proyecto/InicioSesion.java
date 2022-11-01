package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto.Room.AppDatabase;
import com.example.proyecto.Room.DAO.UsuarioDAO;
import com.example.proyecto.Room.Modelo.Usuario;

import java.util.logging.Logger;

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
                    Log.d("ELSE", "DENTRO ELSE CREDENCIALES CORRECTOS");
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
                                Log.d("HILO1", "DENTRO HILO ELSE USUARIO NOT NULL");
                                // Insertamos una instancia de usuario en el Singleton de AppDatabase
                                usuario.setConectado(true);
                                AppDatabase.setUsuario(usuario);

                                // Modificamos el estado 'conectado' del usuario en la base de datos a 'true' para controlar cuando se mantiene iniciada la sesión
                                usuarioDAO.activarEstadoConexion(true, usuario.getIdu());

                                // Iniciamos la actividad principal Main
                                startActivity(new Intent(InicioSesion.this, MainActivity.class));
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
}
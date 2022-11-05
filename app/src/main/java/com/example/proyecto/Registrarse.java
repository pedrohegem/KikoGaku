package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto.Room.AppDatabase;
import com.example.proyecto.Room.DAO.UsuarioDAO;
import com.example.proyecto.Room.Modelo.Usuario;

public class Registrarse extends AppCompatActivity {

    EditText username, password;
    Button bRegistrarse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
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
                    AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
                    final UsuarioDAO usuarioDAO = appDatabase.usuarioDAO();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // Creamos un nuevo hilo y registramos en la base de datos el usuario
                            usuarioDAO.registerUser(usuario);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Registrarse.this, "Usuario " + usuario.getUsername() + " registrado correctamente", Toast.LENGTH_SHORT).show();
                                }
                            });
                            startActivity(new Intent(Registrarse.this, InicioSesion.class));
                        }
                    }).start();
                }
            }
        });
    }
}
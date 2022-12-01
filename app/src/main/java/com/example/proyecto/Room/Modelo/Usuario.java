package com.example.proyecto.Room.Modelo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Usuario {

    public static final String IDU="idu";
    public static final String USERNAME="username";
    public static final String PASSWORD="password";
    public static final String CONECTADO="conectado";


    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int idu;

    private String username;

    private String password;

    private boolean conectado;

    public Usuario(int idu, String username, String password, boolean conectado) {
        this.idu = idu;
        this.username = username;
        this.password = password;
        this.conectado = conectado;
    }

    @Ignore
    public Usuario() {

    }

    public  int getIdu() {
        return idu;
    }

    public void setIdu(int idu) {
        this.idu = idu;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConectado(boolean conectado) {this.conectado = conectado;}

    public boolean isConectado() {return conectado;}
}

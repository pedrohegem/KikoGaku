package com.example.proyecto.Room.Modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Usuario {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    @NonNull
    private int idu;

    @ColumnInfo
    private String username;

    @ColumnInfo
    private String password;


    public Usuario(int idu, String username, String password) {
        this.idu = idu;
        this.username = username;
        this.password = password;
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

}

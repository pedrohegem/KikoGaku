package com.example.proyecto.Room;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import androidx.annotation.NonNull;

@Entity
public class UsuarioEntity {
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo
    @NonNull
    private int idu;

    @ColumnInfo
    private String username;

    @ColumnInfo
    private String password;


    public UsuarioEntity(){
        this.idu = 0;
        this.username = "";
        this.password = "";
    }

    public UsuarioEntity(int idu, String username, String password) {
        this.idu = idu;
        this.username = username;
        this.password = password;
    }

    private int getIdu() {
        return idu;
    }

    private void setIdu(int idu) {
        this.idu = idu;
    }

    private String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    private String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }
}

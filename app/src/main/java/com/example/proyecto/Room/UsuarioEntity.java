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




    public UsuarioEntity(int idu, String username, String password) {
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

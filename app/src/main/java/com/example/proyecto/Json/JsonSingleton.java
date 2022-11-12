package com.example.proyecto.Json;

import com.example.proyecto.Room.Modelo.Evento;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class JsonSingleton {
    private static JsonSingleton instance;

    public TreeMap<String, Municipio> municipioMap;
    public TreeMap<String, Montana> montanaMap;

    private JsonSingleton() {
        municipioMap = new TreeMap<String, Municipio>();
        montanaMap = new TreeMap<String, Montana>();
    }

    public static JsonSingleton getInstance() {
        if (instance == null) {
            instance = new JsonSingleton();
        }
        return instance;
    }

    public boolean buscarMunicipio(String nombreMunicipio){
        for (Map.Entry<String,Municipio> mun: municipioMap.entrySet()) {
            if (Objects.equals(mun.getKey(),nombreMunicipio)){
                return true;
            }
        }
        return false;
    }

}

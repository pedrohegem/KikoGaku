package com.example.proyecto.utils;

import com.example.proyecto.models.Montana;
import com.example.proyecto.models.Municipio;

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

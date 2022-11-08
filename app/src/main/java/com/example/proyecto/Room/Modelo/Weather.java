package com.example.proyecto.Room.Modelo;

import org.json.JSONException;
import org.json.JSONObject;

public class Weather {

    public String ciudad;
    public int temperatura;
    public int sensTermica;
    public int tempMinima;
    public int tempMaxima;
    public int presion;
    public int humedad;
    public double velocidadViento;
    public String estadoTiempo;
    public String descEstadoTiempo;
    public String nombreIcono;

    public Weather() {
    }

    public static Weather fromJson(JSONObject object) throws JSONException {
        Weather weather = new Weather();
        weather.ciudad = object.getString("name");
        weather.temperatura = (int) Math.round(object.getJSONObject("main").getDouble("temp") - 273.15);
        weather.sensTermica = (int) Math.round(object.getJSONObject("main").getDouble("feels_like") - 273.15);
        weather.tempMinima = (int) Math.round(object.getJSONObject("main").getDouble("temp_min") - 273.15);
        weather.tempMaxima = (int) Math.round(object.getJSONObject("main").getDouble("temp_max") - 273.15);
        weather.presion = object.getJSONObject("main").getInt("pressure");
        weather.humedad = object.getJSONObject("main").getInt("humidity");
        weather.velocidadViento = object.getJSONObject("wind").getDouble("speed");
        weather.estadoTiempo = object.getJSONArray("weather").getJSONObject(0).getString("main");
        weather.descEstadoTiempo = object.getJSONArray("weather").getJSONObject(0).getString("description");
        // TODO Set Nombre Icono based on range

        return weather;
    }

}

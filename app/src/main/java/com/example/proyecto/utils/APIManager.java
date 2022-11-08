package com.example.proyecto.utils;

import android.util.Log;

import com.example.proyecto.APIManagerDelegate;
import com.example.proyecto.Room.Modelo.Weather;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class APIManager {

    // Interfaz que maneja la delegación de los resultados de las llamadas a la API
    public APIManagerDelegate delegate = null;

    private final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
    private final String API_KEY = "3c37b8776fd8ba64116ac542be924ff1";

    public APIManager(APIManagerDelegate delegate) {
        this.delegate = delegate;
    }

    // Método sobrecargado que parametriza la llamada al tiempo actual en base a la CIUDAD
    public void getEventWeather(String city) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("q", city);
        requestParams.put("appid", API_KEY);
        weatherNetworking(requestParams);
    }

    // Método sobrecargado que parametriza la llamada al tiempo actual en base a la LATITUD y LONGITUD
    public void getEventWeather(double lat, double lon) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("lat", lat);
        requestParams.put("lon", lon);
        requestParams.put("appid", API_KEY);
        weatherNetworking(requestParams);
    }


    public void weatherNetworking(RequestParams requestParams) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess (int statusCode, Header[] headers, JSONObject response) {
                Log.d("Clima", "onSuccess: " + response.toString());
                try {
                    delegate.onGetWeatherSuccess(Weather.fromJson(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Log.e("Clima", "onFailure: " + e.toString());
            }
        });
    }
}

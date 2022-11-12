package com.example.proyecto.Room.javadb;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp){
        return timestamp == null ? null: new Date(timestamp);
    }
    @TypeConverter
    public static Date toDate(String timestamp){
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date myDate = new Date();
        try {
            myDate = df.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return myDate;
    }
    @TypeConverter
    public static Long toTimestamp(Date date){
        return date == null ? null : date.getTime();
    }
    @TypeConverter
    public static String toString(Date date){
        String cadena = date.getDate() + "/" + (date.getMonth() + 1) + "/" + (1900 + date.getYear());
        return cadena;
    }
}

package com.example.proyecto.ui.ajustes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.proyecto.MainActivity;
import com.example.proyecto.R;

public class AjustesFragment extends PreferenceFragmentCompat {

    private Context mContext;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        // -- Obtenemos vista del checkbox
        CheckBoxPreference swi = findPreference("pref_nightmode");

        // -- Obtenemos las sp de la mainactivity que serán modificadas al hacer clic en el checkbox
        MainActivity ma = (MainActivity) getActivity();
        SharedPreferences sp = ma.getSharedPreferences("preferences", ma.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        // -- Para acompasar el checkbox con la elección del modo oscuro o claro del usuario
        int tema = sp.getInt("Theme", 1);
        if(tema == 1){
            swi.setChecked(false);
        }
        else{
            swi.setChecked(true);
        }

        // -- Con el editor, se actualiza el sharedPreferences para la actividad main

        swi.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {

                if(swi.isChecked()){
                    // 0 es que se ha activado el checkbox para poner modo claro
                    editor.putInt("Theme", 0).apply();
                }
                else{
                    editor.putInt("Theme", 1).apply();
                }
                // Se modifica el theme de la actividad
                ma.setDayLight();

                return true;
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

}
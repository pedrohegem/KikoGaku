package com.example.proyecto.ui.Eventos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto.Json.JsonSingleton;
import com.example.proyecto.Json.Montana;
import com.example.proyecto.MainActivity;
import com.example.proyecto.R;
import com.example.proyecto.Room.AppDatabase;
import com.example.proyecto.Room.DAO.EventoDAO;
import com.example.proyecto.Room.Modelo.Evento;
import com.example.proyecto.Room.Modelo.Weather;
import com.example.proyecto.Room.javadb.DateConverter;
import com.example.proyecto.databinding.FragmentDetallesEventoBinding;
import com.example.proyecto.utils.APIManager;
import com.example.proyecto.utils.APIManagerDelegate;

import java.util.List;


public class DetallesEventoFragment extends Fragment implements APIManagerDelegate {

    private DetallesEventoActivity main;
    private APIManager apiManager;
    private Context mContext;

    private TextView nombreEvento, localidadEvento, fechaEvento, descripcionEvento;
    private TextView textViewTemp, temperaturaMaxMin, localidadTiempo, descripcionTiempo, viento, humedad, sensTermica;
    private Button botonModificar, botonBorrar;
    ImageView iconoTiempo;

    private FragmentDetallesEventoBinding binding;

    private Evento evento;

    public DetallesEventoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetallesEventoBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        nombreEvento = binding.EtiquetaDetalles;
        localidadEvento = binding.DetallesLocalidad;
        fechaEvento = binding.DetallesFechaDeInicio;
        descripcionEvento = binding.DetallesDescripcion;

        textViewTemp = binding.textViewTemperatura;
        temperaturaMaxMin = binding.textViewTemperaturas;
        localidadTiempo = binding.textViewUbicacion;
        descripcionTiempo = binding.textViewDescD;
        viento = binding.textViewVientoP;
        humedad = binding.textViewHumedadP;
        sensTermica = binding.textViewSensTermP;
        iconoTiempo = binding.image2;
        botonModificar = binding.BotonModificar;
        botonBorrar = binding.BotonEliminar;

        apiManager = new APIManager(this);

        // Gestión de llamadas a la API
        if(main.esMunicipio()) {
            int diaEvento = main.getDiaEvento();
            if(diaEvento < 1) {
                apiManager.getEventWeather(main.getUbicacion());
            } else if (diaEvento > 0 && diaEvento < 5) {
                apiManager.getEventForecast(main.getUbicacion(), diaEvento);
            }
        } else { // Evento de Montaña
            Montana m = JsonSingleton.getInstance().montanaMap.get(main.getUbicacion());
            Log.d("MONT", "onCreateView: " + m.getLatitud() + " " + m.getLongitud());
            int diaEvento = main.getDiaEvento();
            if(diaEvento < 1) {
                apiManager.getEventWeather(m.getLatitud(),m.getLongitud());
            } else if (diaEvento > 0 && diaEvento < 5) {
                apiManager.getEventForecast(m.getLatitud(),m.getLongitud(), diaEvento);
            }

        }

        int idEvento = main.getIdEvento();


        Log.d("IdEvento", idEvento+"");

        EventoDAO eventoDao = AppDatabase.getInstance(mContext).eventoDAO();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Evento> eventos = eventoDao.getEvent(idEvento);
                    getActivity().runOnUiThread(() -> {
                        if (eventos.isEmpty() != true) {
                            evento = eventos.get(0);

                            nombreEvento.setText(evento.getTitulo());
                            if (!main.esMunicipio()) {
                                localidadTiempo.setText(evento.getUbicacion());
                            }
                            localidadEvento.setText(evento.getUbicacion());
                            Log.i("Fecha", "year: " + evento.getFecha());

                            fechaEvento.setText(DateConverter.toString(evento.getFecha()));

                            if (evento.getDescripcion().isEmpty()) {
                                descripcionEvento.setText("Sin descripción");
                            } else {
                                descripcionEvento.setText(evento.getDescripcion());
                            }
                            botonModificar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("idEvento", idEvento);
                                    if (evento.getEsMunicipio() == true) {
                                        NavHostFragment.findNavController(DetallesEventoFragment.this).navigate(R.id.action_detallesEvento_to_nav_modificar_evento, bundle);
                                    } else {
                                        NavHostFragment.findNavController(DetallesEventoFragment.this).navigate(R.id.action_nav_detalles_evento_to_nav_modificar_evento_montana, bundle);
                                    }
                                }
                            });
                        }
                    });
                }
            }).start();

        botonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ide = idEvento;
                DeleteEventDialog deleteDialogFragment = new DeleteEventDialog();

                Bundle bundle = new Bundle();
                bundle.putInt("idEvento", ide);
                deleteDialogFragment.setArguments(bundle);

                deleteDialogFragment.show(getChildFragmentManager(), "DeleteDialogFragment");
            }
        });

        return root;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        main = (DetallesEventoActivity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public void onGetWeatherSuccess(Weather weather) {

        switch (weather.gifResource){
            case 0://Error
                Log.e("Error Weather", "onGetWeatherSuccess: No se ha obtenido el estado del tiempo correctamente");
                break;
            case 1://Tormenta
                iconoTiempo.setImageResource(R.drawable.itormenta);
                break;
            case 2://Llovizna
                iconoTiempo.setImageResource(R.drawable.illovizna);
                break;
            case 3://Lluvia
                iconoTiempo.setImageResource(R.drawable.illuvia);
                break;
            case 4://Nieve
                iconoTiempo.setImageResource(R.drawable.inieve);
                break;
            case 5://Niebla
                iconoTiempo.setImageResource(R.drawable.iniebla);
                break;
            case 6://Nubes
                iconoTiempo.setImageResource(R.drawable.inubes);
                break;
            case 7://Sol
                iconoTiempo.setImageResource(R.drawable.isol);
                break;
            default:
                iconoTiempo.setImageResource(R.drawable.isol);
                break;
        }
        textViewTemp.setText(String.valueOf(weather.temperatura));
        temperaturaMaxMin.setText(weather.tempMinima +"º / "+ weather.tempMaxima +"º");
        if(main.esMunicipio()){
            localidadTiempo.setText(weather.ciudad);
        }
        descripcionTiempo.setText(weather.descEstadoTiempo);
        viento.setText(String.valueOf(weather.velocidadViento));
        humedad.setText(String.valueOf(weather.humedad));
        sensTermica.setText(weather.sensTermica + "º");

    }

    @Override
    public void onGetWeatherFailure() {
        String noPerms = "No es posible consultar el tiempo a partir de los próximos 5 días. (FREE API SADGE)";
        Toast.makeText(getContext(), noPerms, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        DetallesEventoActivity cea = (DetallesEventoActivity) getActivity();
        cea.setDayLight();
    }

}
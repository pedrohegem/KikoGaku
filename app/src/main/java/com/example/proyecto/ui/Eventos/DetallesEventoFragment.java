package com.example.proyecto.ui.Eventos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto.AppContainer;
import com.example.proyecto.MyApplication;
import com.example.proyecto.repository.EventRepository;
import com.example.proyecto.utils.JsonSingleton;
import com.example.proyecto.models.Montana;
import com.example.proyecto.MainActivity;
import com.example.proyecto.R;
import com.example.proyecto.repository.room.AppDatabase;
import com.example.proyecto.repository.room.DAO.EventoDAO;
import com.example.proyecto.models.Evento;
import com.example.proyecto.models.Weather;
import com.example.proyecto.utils.DateConverter;
import com.example.proyecto.databinding.FragmentDetallesEventoBinding;
import com.example.proyecto.repository.networking.APIManager;
import com.example.proyecto.repository.networking.APIManagerDelegate;
import com.example.proyecto.viewmodels.DetallesEventoViewModel;
import com.example.proyecto.viewmodels.ListaEventosViewModel;

import java.util.List;


public class DetallesEventoFragment extends Fragment {

    private DetallesEventoActivity main;
    private APIManager apiManager;
    private Context mContext;

    private String TAG = "DetallesEventoFragment";
    private TextView nombreEvento, localidadEvento, fechaEvento, descripcionEvento;
    private TextView textViewTemp, temperaturaMaxMin, localidadTiempo, descripcionTiempo, viento, humedad, sensTermica;
    private Button botonModificar, botonBorrar;
    ImageView iconoTiempo;
    private EventRepository eventRepository;
    private int idEvento;

    private FragmentDetallesEventoBinding binding;

    private Evento eventazo;

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


        // ---------------------- Refactorizacion --------------------------------
        AppContainer appContainer = ((MyApplication) mContext.getApplicationContext()).appContainer;
        DetallesEventoViewModel mViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.detallesEventoViewModelFactory).get(DetallesEventoViewModel.class);
        this.eventRepository = EventRepository.getInstance(AppDatabase.getInstance(mContext).eventoDAO());

        final Observer<Evento> observer = new Observer<Evento>() {
            @Override
            public void onChanged(final Evento evento) {
                Log.d(TAG, "Data changed on observer...");
                if(evento != null) {
                    updateUI(evento);
                }
            }
        };

        this.idEvento = main.getIdEvento();
        mViewModel.getEventByID(idEvento).observeForever(observer);

        //--------------------------------------------------------------------------


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

    public void updateUI(Evento evento) {
        nombreEvento.setText(evento.getTitulo());
        if (!evento.getEsMunicipio()) {
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

        switch (evento.getGifResource()){
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
            //Sol
            default:
                iconoTiempo.setImageResource(R.drawable.isol);
                break;
        }
        textViewTemp.setText(String.valueOf(evento.getTemperatura()));
        temperaturaMaxMin.setText(evento.getTempMinima() +"º / "+ evento.getTempMaxima() +"º");

        if(evento.getEsMunicipio()){
            localidadTiempo.setText(evento.getUbicacion());
        }

        descripcionTiempo.setText(evento.getDescEstadoTiempo());
        viento.setText(String.valueOf(evento.getVelocidadViento()));
        humedad.setText(String.valueOf(evento.getHumedad()));
        sensTermica.setText(evento.getSensTermica() + "º");

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

    @Override
    public void onAttach(@NonNull Context context) {
        main = (DetallesEventoActivity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        DetallesEventoActivity cea = (DetallesEventoActivity) getActivity();
        cea.setDayLight();
    }

}
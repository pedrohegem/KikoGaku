package com.example.proyecto.ui.Eventos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.proyecto.R;
import com.example.proyecto.Room.AppDatabase;
import com.example.proyecto.Room.DAO.EventoDAO;
import com.example.proyecto.Room.Modelo.Evento;
import com.example.proyecto.Room.javadb.DateConverter;
import com.example.proyecto.databinding.FragmentDetallesEventoBinding;

import java.util.List;


public class DetallesEventoFragment extends Fragment {

    private DetallesEventoActivity main;

    private Context mContext;

    TextView nombreEvento, localidadEvento, fechaEvento, descripcionEvento;
    private Button botonModificar, botonBorrar;

    private FragmentDetallesEventoBinding binding;

    private Evento evento;

    public DetallesEventoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if(getArguments() != null){
            idEvento = getArguments().getInt("idEvento");
        }
        else{
            Log.d("No", "Tristeza");
        }*/
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

        botonModificar = binding.BotonModificar;
        botonBorrar = binding.BotonEliminar;

        int idEvento = main.getIdEvento();

        Log.d("IdEvento", idEvento+"");

        EventoDAO eventoDao = AppDatabase.getInstance(mContext).eventoDAO();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Evento> eventos = eventoDao.getEvent(idEvento);
                    if (eventos.isEmpty() == true) {
                        //todo gestionar error
                    } else {
                        evento = eventos.get(0);

                        nombreEvento.setText(evento.getTitulo());

                        //todo buscar localidad con el JSON
                        localidadEvento.setText(evento.getUbicacion());
                        Log.i("Fecha", "year: "+evento.getFecha());

                        fechaEvento.setText(DateConverter.toString(evento.getFecha()));

                        if(evento.getDescripcion().isEmpty()){
                            descripcionEvento.setText("Sin descripción");
                        }
                        else {
                            descripcionEvento.setText(evento.getDescripcion());
                        }

                        botonModificar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bundle bundle = new Bundle();
                                bundle.putInt("idEvento",idEvento);
                                if(evento.getEsMunicipio() == true) {
                                    NavHostFragment.findNavController(DetallesEventoFragment.this).navigate(R.id.action_detallesEvento_to_nav_modificar_evento,bundle);
                                }else{
                                    NavHostFragment.findNavController(DetallesEventoFragment.this).navigate(R.id.action_nav_detalles_evento_to_nav_modificar_evento_montana,bundle);
                                }
                            }
                        });
                    }
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
}
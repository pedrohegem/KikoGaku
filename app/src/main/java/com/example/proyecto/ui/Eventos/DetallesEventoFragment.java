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
    private ImageButton botonModificar, botonBorrar;

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

                        fechaEvento.setText(DateConverter.toString(evento.getFecha()));
                        descripcionEvento.setText(evento.getDescripcion());
                        botonModificar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                /*ModificarEventoMunicipioFragment modificarEvento = new ModificarEventoMunicipioFragment();
                                Bundle bundle = new Bundle();
                                bundle.putInt("idEvento", idEvento);

                                modificarEvento.setArguments(bundle);
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, modificarEvento).commit();*/

                                if(evento.getEsMunicipio() == true) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("idEvento",idEvento);
                                    NavHostFragment.findNavController(DetallesEventoFragment.this).navigate(R.id.action_detallesEvento_to_nav_modificar_evento,bundle);
                                }
                                else{
                                    NavHostFragment.findNavController(DetallesEventoFragment.this).navigate(R.id.action_nav_detalles_evento_to_nav_modificar_evento_montana);
                                }

                                //Navigation.findNavController(main, R.id.nav_host_fragment_content_main).navigate();
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

    /*
        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            nombreEvento = binding.EtiquetaDetalles;
            localidadEvento = binding.DetallesLocalidad;
            fechaEvento = binding.DetallesFechaDeInicio;
            descripcionEvento = binding.DetallesDescripcion;

            botonModificar = binding.BotonModificar;
            botonBorrar = binding.BotonEliminar;

            nombreEvento.setText(evento.getTitulo());

            //todo buscar localidad con el JSON
            localidadEvento.setText(evento.getUbicacion());

            fechaEvento.setText(DateConverter.toString(evento.getFecha()));
            descripcionEvento.setText(evento.getDescripcion());

            botonModificar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ModificarEventoMunicipioFragment modificarEvento = new ModificarEventoMunicipioFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("idEvento", idEvento);
                    /*bundle.putString("nombreEvento", evento.getTitulo());

                    //todo obtener localidad con el JSON
                    bundle.putString("localidadEvento", evento.getUbicacionCode());

                    bundle.putString("descripcionEvento", evento.getDescripcion());
                    bundle.putBoolean("esMunicipio", evento.getEsMunicipio());
                    bundle.putString("fechaEvento", DateConverter.toString(evento.getFecha()));

                    modificarEvento.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, modificarEvento).commit();

                    //Navigation.findNavController(main, R.id.nav_host_fragment_content_main).navigate();
                }
            });

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
        }
        */
    @Override
    public void onAttach(@NonNull Context context) {
        main = (DetallesEventoActivity) context;
        mContext = context;
        super.onAttach(context);
    }
}
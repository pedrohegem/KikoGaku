package com.example.proyecto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyecto.models.Usuario;
import com.example.proyecto.repository.UserRepository;
import com.example.proyecto.ui.ListaEventos.ListaEventosFragment;

/**
 * {@link ViewModel} for {@link ListaEventosFragment}
 */
public class MainUsuarioViewModel extends ViewModel {

    private final UserRepository userRepository;
    private LiveData<Usuario> mUsuario;

    public MainUsuarioViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<Usuario> getUser() {
        mUsuario = userRepository.getUser();
        return mUsuario;
    }

    public void activarEstadoConection(boolean conectado, int idUsuario){
        userRepository.activarEstadoConex(conectado, idUsuario);
    }

    public Usuario getUsuarioConectado(){
        return userRepository.getUserConectado();
    }
}

package com.example.proyecto.ui.ajustes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyecto.databinding.FragmentAjustesBinding;

public class AjustesFragment extends Fragment {

    private FragmentAjustesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AjustesViewModel ajustesViewModel =
                new ViewModelProvider(this).get(AjustesViewModel.class);

        binding = FragmentAjustesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAjustes;
        ajustesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
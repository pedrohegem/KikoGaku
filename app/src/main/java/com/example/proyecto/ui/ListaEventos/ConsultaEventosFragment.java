package com.example.proyecto.ui.ListaEventos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.proyecto.MainActivity;
import com.example.proyecto.R;
import com.example.proyecto.databinding.FragmentConsultarEventosBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConsultaEventosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsultaEventosFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private TabLayout tabLayout;
    private MyViewPagerAdapter myViewPagerAdapter;
    private Spinner spinnerFiltrado;
    Bundle bundle;

    private FragmentConsultarEventosBinding binding;
    public ConsultaEventosFragment() {
        // Required empty public constructor
    }


    public static ConsultaEventosFragment newInstance(String param1, String param2) {
        ConsultaEventosFragment fragment = new ConsultaEventosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = new Bundle();
        bundle.clear();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConsultarEventosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        spinnerFiltrado = binding.spinnerFiltrado;
        tabLayout = binding.tabEventos;

        myViewPagerAdapter= new MyViewPagerAdapter(requireActivity());

        ArrayList<String> opciones = new ArrayList<String>();
        opciones.add("Creación");
        opciones.add("Fecha");

        ArrayAdapter ad = new ArrayAdapter(getContext(),R.layout.spinner_list,opciones);

        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFiltrado.setAdapter(ad);
        spinnerFiltrado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bundle.putInt("PosicionTab",tabLayout.getSelectedTabPosition());
                bundle.putInt("PosicionSpinner",spinnerFiltrado.getSelectedItemPosition());
                Fragment childFragment = new ListaEventosFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                childFragment.setArguments(bundle);
                transaction.replace(R.id.child_ConsultarEventos, childFragment).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                bundle.putInt("PosicionTab",tab.getPosition());
                bundle.putInt("PosicionSpinner",spinnerFiltrado.getSelectedItemPosition());
                Fragment childFragment = new ListaEventosFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                childFragment.setArguments(bundle);
                transaction.replace(R.id.child_ConsultarEventos, childFragment).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        bundle.putInt("PosicionTab", tabLayout.getSelectedTabPosition());
        bundle.putInt("PosicionSpinner",spinnerFiltrado.getSelectedItemPosition());
        Fragment childFragment = new ListaEventosFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        childFragment.setArguments(bundle);
        transaction.replace(R.id.child_ConsultarEventos, childFragment).commit();
        return root;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
        bundle.putInt("PosicionSpinner",pos);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    public class MyViewPagerAdapter extends FragmentStateAdapter{

        public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @Override
        public int getItemCount() {
            return 0;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return new ListaEventosFragment();
                case 1:
                    return new ListaEventosFragment();
            }
            return null;
        }


    }
    @Override
    public void onResume() {
        super.onResume();
        MainActivity cea = (MainActivity) getActivity();
        cea.setDayLight();
    }
}
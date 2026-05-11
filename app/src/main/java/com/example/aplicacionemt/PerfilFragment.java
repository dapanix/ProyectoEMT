package com.example.aplicacionemt;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PerfilFragment extends Fragment {

    public PerfilFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflamos (cargamos) el diseño XML del fragmento
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }
}
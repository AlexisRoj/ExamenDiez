package com.innovagenesis.aplicaciones.android.examendiez.fragments;


import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.innovagenesis.aplicaciones.android.examendiez.R;
import com.innovagenesis.aplicaciones.android.examendiez.cubo.MyRenderer;

/**
 * A simple {@link Fragment} subclass.
 */
public class GraficoFragment extends Fragment {


    public GraficoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_grafico, container, false);


        GLSurfaceView lienzo = new GLSurfaceView(getContext());
        lienzo.setRenderer(new MyRenderer(getContext()));
        // Instancia un linear layout donde se van a mostrar la animacion
        LinearLayout LinearRemplazar = (LinearLayout) view.findViewById(R.id.contenedor_grafica);
        LinearRemplazar.addView(lienzo);

        return view;
    }

}

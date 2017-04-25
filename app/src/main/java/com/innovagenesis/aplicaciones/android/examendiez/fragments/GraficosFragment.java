package com.innovagenesis.aplicaciones.android.examendiez.fragments;


import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.innovagenesis.aplicaciones.android.examendiez.R;
import com.innovagenesis.aplicaciones.android.examendiez.cubo.MyRenderer;

/**
 * A simple {@link Fragment} subclass.
 */
public class GraficosFragment extends Fragment {


    private static GLSurfaceView lienzo;


    public GraficosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     return inflater.inflate(R.layout.fragment_graficos, container, false);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

}

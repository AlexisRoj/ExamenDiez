package com.innovagenesis.aplicaciones.android.examendiez.fragments;


import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innovagenesis.aplicaciones.android.examendiez.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnimacionesFragment extends Fragment {

    View view;

    public AnimacionesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_animaciones, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {

                /*Inicia la animacion del */

                View estrella = view.findViewById(R.id.estrella);
                Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.animation_estrella);
                estrella.startAnimation(animation);

            }
        });

        return view;
    }


}

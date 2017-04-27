package com.innovagenesis.aplicaciones.android.examendiez.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

import com.innovagenesis.aplicaciones.android.examendiez.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment implements View.OnClickListener {

    Button buttonAbrirVideo;
    Button buttonGuardarVideo;
    VideoView videoView;

    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_video, container, false);

        buttonAbrirVideo = (Button) view.findViewById(R.id.abrir_video);
        buttonGuardarVideo = (Button) view.findViewById(R.id.capturar_video);

        buttonAbrirVideo.setOnClickListener(this);
        buttonGuardarVideo.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.abrir_video:{
                break;
            }
            case R.id.capturar_video:{
                break;
            }
        }

    }
}

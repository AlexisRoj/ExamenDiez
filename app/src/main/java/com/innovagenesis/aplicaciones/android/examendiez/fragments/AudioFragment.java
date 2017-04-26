package com.innovagenesis.aplicaciones.android.examendiez.fragments;


import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.innovagenesis.aplicaciones.android.examendiez.R;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AudioFragment extends Fragment implements View.OnClickListener {

    View view;
    Boolean reproducir = true;

    static final int Pick_song=1;
    public AudioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_audio, container, false);

        Button buttonReproducir = (Button) view.findViewById(R.id.reproducir_audio);
        Button buttonGrabar = (Button) view.findViewById(R.id.capturar_audio);
        Button buttonAbrir = (Button) view.findViewById(R.id.abrir_audio);

        buttonReproducir.setOnClickListener(this);
        buttonGrabar.setOnClickListener(this);
        buttonAbrir.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {



        switch (v.getId()) {

            case R.id.abrir_audio: {

                Intent intent = new Intent();
                intent.setType("audio/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Selecciona un audio"), Pick_song);
                break;
            }

            case R.id.reproducir_audio: {
                break;
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Pick_song:
                if (resultCode == RESULT_OK) {
                    String patch = data.getDataString();

                    try {
                        MediaPlayer audio;
                        audio = new MediaPlayer();
                        audio.setDataSource(getContext().getApplicationContext(), Uri.parse(patch));
                    /*    audio.prepare();
                        audio.start();*/

                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Erro al ejecutar el audio", Toast.LENGTH_SHORT).show();

                    }
                }
        }

    }
}

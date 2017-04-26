package com.innovagenesis.aplicaciones.android.examendiez.fragments;


import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.innovagenesis.aplicaciones.android.examendiez.R;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AudioFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Boolean reproducir = true;
    private MediaPlayer audio = null;
    private Button buttonReproducir;
    private Button buttonGrabar;
    private static String nombreAudio = null;
    private MediaRecorder mediaRecorder = null;
    private MediaPlayer mediaPlayer = null;
    private int aux = 0;


    static final int Pick_song = 1;
    private boolean abrio_file = false;

    public AudioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_audio, container, false);


        buttonReproducir = (Button) view.findViewById(R.id.reproducir_audio);
        buttonGrabar = (Button) view.findViewById(R.id.capturar_audio);
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
                abrio_file = true;
                break;
            }

            case R.id.reproducir_audio: {

                onPlay(verificacion2);
                if (verificacion2) {
                    buttonReproducir.setText("Detener reproducción");
                } else {
                    buttonReproducir.setText("Reproducir");
                }
                verificacion2 = !verificacion2;
                abrio_file = false;


                /*if (reproducir){
                    *//** Reproduce el audio*//*
                    audio.seekTo(aux);
                    audio.start();
                    buttonReproducir.setText(getString(R.string.reproducir_audio));
                    reproducir = false;

                }else{
                    *//** Pausa el audio*//*
                    audio.pause();
                    aux = audio.getCurrentPosition();
                    buttonReproducir.setText(getString(R.string.pausar));
                    reproducir = true;
                }*/
                break;
            }

            case R.id.capturar_audio: {

                /** Nombre el fichero*/
                nombreAudio = Environment.getExternalStorageDirectory() + "/audio.3gp";
                grabando(verificacion);
                if (verificacion) {
                    buttonGrabar.setText(getString(R.string.detenerG));
                } else {
                    buttonGrabar.setText(R.string.iniciarG);
                }
                verificacion = !verificacion;
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
                        audio = new MediaPlayer();
                        audio.setDataSource(getContext().getApplicationContext(), Uri.parse(patch));
                        audio.prepare();

                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Erro al ejecutar el audio", Toast.LENGTH_SHORT).show();

                    }
                }
        }

    }

    private void detenerGrabacion() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;

        Toast.makeText(getContext(),
                "Se ha guardado el audio en:\n" + Environment.getExternalStorageDirectory()
                        + "/audio.3gp", Toast.LENGTH_LONG).show();
    }

    private void grabando(boolean comenzado) {
        if (comenzado) {
            comenzarGrabacion();
        } else {
            detenerGrabacion();
        }
    }

    private void comenzarReproduccion() {
        mediaPlayer = new MediaPlayer();
        try {
            if (!abrio_file)
                mediaPlayer.setDataSource(nombreAudio);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Toast.makeText(getContext(),
                    "Ha ocurrido un error en la reproducción", Toast.LENGTH_SHORT).show();
        }
    }

    private void detenerReproduccion() {
        mediaPlayer.release();
        mediaPlayer = null;
    }

    private void onPlay(boolean comenzarRep) {
        if (comenzarRep) {
            comenzarReproduccion();
        } else {
            detenerReproduccion();
        }
    }

    private void comenzarGrabacion() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(nombreAudio);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            Toast.makeText(getContext(), "No se grabará correctamente", Toast.LENGTH_SHORT).show();
        }


    }

    boolean verificacion = true;
    boolean verificacion2 = true;

    @Override
    public void onPause() {
        super.onPause();

        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

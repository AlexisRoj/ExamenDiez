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

    private MediaPlayer audio = null;
    private Button buttonReproducir;
    private Button buttonGrabar;
    private static String nombreAudio = null;
    private MediaRecorder mediaRecorder = null;
    private int aux = 0;

    boolean grabarAudio = true;
    boolean reproducirAudio = true;


    static final int Pick_song = 1;
    private boolean abrio_archivo = false;

    public AudioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_audio, container, false);


        buttonReproducir = (Button) view.findViewById(R.id.reproducir_audio);
        buttonGrabar = (Button) view.findViewById(R.id.capturar_audio);
        Button buttonAbrir = (Button) view.findViewById(R.id.abrir_audio);

        buttonReproducir.setOnClickListener(this);
        buttonGrabar.setOnClickListener(this);
        buttonAbrir.setOnClickListener(this);


        return view;
    }

    /***
     * Selector de botones
     * */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.abrir_audio: {
                /*
                 * Boton de abrir audio
                 * */
                Intent intent = new Intent();
                intent.setType("audio/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Selecciona un audio"), Pick_song);
                break;
            }
            case R.id.reproducir_audio: {
                /*
                * Boton de reproducir audio
                * */
                mReproducirAudio(reproducirAudio);
                if (reproducirAudio) {
                    buttonReproducir.setText(getString(R.string.detener_rep));
                } else {
                    buttonReproducir.setText(getString(R.string.reproducir));
                }
                reproducirAudio = !reproducirAudio;
                break;
            }
            case R.id.capturar_audio: {
                /*
                 * Boton que graba el audio
                 * */
                nombreAudio = Environment.getExternalStorageDirectory() + "/audio.3gp";// Nombre el fichero
                mGrabando(grabarAudio);
                if (grabarAudio) {
                    buttonGrabar.setText(getString(R.string.detenerG));
                    abrio_archivo = false;
                } else {
                    buttonGrabar.setText(R.string.iniciarG);
                }
                grabarAudio = !grabarAudio;
            }
        }

    }

    /**
     * Trae la ruta del archivo abierto
     * */
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
                        abrio_archivo = true;

                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Erro al ejecutar el audio", Toast.LENGTH_SHORT).show();

                    }
                }
        }

    }

    /**
     * Ejecuta el proceso cuando se detiene la grabacion
     */
    private void mDetenerGrabacion() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        abrio_archivo = false;

        Toast.makeText(getContext(),
                "Se ha guardado el audio en:\n" + Environment.getExternalStorageDirectory()
                        + "/audio.3gp", Toast.LENGTH_LONG).show();
    }

    /**
     * identifica el estado del proceso
     */
    private void mGrabando(boolean comenzado) {
        if (comenzado) {
            mComenzarGrabacion();
        } else {
            mDetenerGrabacion();
        }
    }

    /**
     * Inicia el proceso de grabación del audio
     */
    private void mComenzarReproduccion() {

        try {
            if (!abrio_archivo) {
                audio = new MediaPlayer();
                audio.setDataSource(nombreAudio);
                audio.prepare();
            } else {
                //Continua la reproducion
                audio.seekTo(aux);
            }
            audio.start();
        } catch (IOException e) {
            Toast.makeText(getContext(),
                    "Ha ocurrido un error en la reproducción", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Detiene la grabación del audio
     */
    private void mDetenerReproduccion() {
        if (abrio_archivo) {
            //Pausa la reproducion
            audio.pause();
            aux = audio.getCurrentPosition();
        } else {
            //Libera el audio
            audio.release();
            audio = null;
        }
    }
    /**
     * Inicia la reproducion de un audio grabado o almacenado
     */
    private void mReproducirAudio(boolean comenzarRep) {
        if (comenzarRep) {
            mComenzarReproduccion();
        } else {
            mDetenerReproduccion();
        }
    }
    /**
     * Inicia la grabacion
     */
    private void mComenzarGrabacion() {
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

    @Override
    public void onPause() {
        super.onPause();

        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }

        if (audio != null) {
            audio.release();
            audio = null;
        }
    }
}

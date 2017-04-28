package com.innovagenesis.aplicaciones.android.examendiez.fragments;


import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.innovagenesis.aplicaciones.android.examendiez.R;

import java.io.File;

import static android.app.Activity.RESULT_OK;
import static com.innovagenesis.aplicaciones.android.examendiez.R.string.audio;
import static com.innovagenesis.aplicaciones.android.examendiez.R.string.video;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment implements View.OnClickListener {

    Button buttonAbrirVideo;
    Button buttonGuardarVideo;
    private VideoView videoView;
    private Uri videoUri;
    private static int REQUEST_CODE_VIDEO = 1;
    static final int Pick_video = 1;

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
        videoView = (VideoView)view.findViewById(R.id.videoView_video);


        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.abrir_video: {

                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Selecciona un video"), Pick_video);

                break;
            }
            case R.id.capturar_video: {
                /*
                 * Encargado de grabar video y reproducirlo
                 * */
                Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                File videosFolder = new File(Environment.getExternalStorageDirectory(), "VideosNextU");
                videosFolder.mkdirs();
                File video = new File(videosFolder, "video.mp4");
                videoUri = Uri.fromFile(video);
                videoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
                startActivityForResult(videoIntent, REQUEST_CODE_VIDEO);
                break;
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

  /*      switch (requestCode) {
            case Pick_video:
                if (resultCode == RESULT_OK) {
                    //String patch = data.getDataString();

                    Uri uri = data.getData();

                    try {
                        videoView = new VideoView(getContext());


                        MediaController mediaController = new MediaController(getContext());
                        videoView.setMediaController(mediaController);
                        videoView.setVideoURI(uri);
                        //videoView.setVideoPath(patch);
                        mediaController.setAnchorView(videoView);

                        videoView.start();



                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Erro al ejecutar el audio", Toast.LENGTH_SHORT).show();
                        videoView.start();
                    }
                }
        }*/

        mGrabarVideo(requestCode, resultCode);
    }

    /**
     * Método encargado de grabar los videos y almacenarlos
     * */
    private void mGrabarVideo(int requestCode, int resultCode) {
        if (requestCode == REQUEST_CODE_VIDEO && resultCode == RESULT_OK) {
            Toast.makeText(getContext(),
                    "Se guardó el video en:\n" + Environment.getExternalStorageDirectory() +
                            "/VideosNextU/video.mp4", Toast.LENGTH_LONG).show();

            MediaController mediaController = new MediaController(getContext());
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(videoUri);
            videoView.start();

            mediaController.setAnchorView(videoView);

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //videoView.start();
                }
            });

        } else {
            Toast.makeText(getContext(),
                    "Ha ocurrido un error al guardar el video", Toast.LENGTH_SHORT).show();
        }
    }
}



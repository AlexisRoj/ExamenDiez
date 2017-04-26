package com.innovagenesis.aplicaciones.android.examendiez.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.innovagenesis.aplicaciones.android.examendiez.R;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CamaraFragment extends Fragment implements View.OnClickListener {

    private static int REQUEST_CODE_IMAGEN = 2;
    private static int REQUEST_CODE_TOMAR_FOTO = 3;

    Bitmap bitmap = null;
    ImageView imageView;
    View view;

    public CamaraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_camara, container, false);

        //Declaraciones para los eleementos del fragment a utilizar
        imageView = (ImageView) view.findViewById(R.id.imageView_foto);
        Button buttonAbrir = (Button) view.findViewById(R.id.abrir_foto);
        Button buttonCaptaurar = (Button) view.findViewById(R.id.capturar_foto);

        buttonAbrir.setOnClickListener(this);
        buttonCaptaurar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id){

            case R.id.abrir_foto:{

                Intent intentAbrirFoto =
                        new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentAbrirFoto,REQUEST_CODE_IMAGEN);
                break;
            }

            case R.id.capturar_foto:{

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File imagenFolder = new File(Environment.getExternalStorageDirectory(),"CamaraFolder");
                imagenFolder.mkdirs();
                File imagen = new File(imagenFolder , "foto.jpg");
                Uri uriImagen = Uri.fromFile(imagen);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriImagen);
                startActivityForResult(cameraIntent,REQUEST_CODE_TOMAR_FOTO);
                break;
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGEN && data != null){
            mCargarFoto(data);
        }

        if (requestCode == REQUEST_CODE_TOMAR_FOTO && resultCode == RESULT_OK){
            mTomarFoto();
        }else{
            Toast.makeText(getContext(),
                    "No se guardó correctamente la imagen en el dispositivo",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Método encargado de tomar la foto
     * */
    private void mTomarFoto() {
        Toast.makeText(getContext(),
                "Se ha guardado la imagen:\n" + Environment.getExternalStorageDirectory()
                        + "/CamaraFolder/foto.jpg", Toast.LENGTH_LONG).show();

        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()
                + "/CamaraFolder/foto.jpg");

        int height = bitmap.getHeight();
        int width = bitmap.getWidth();

        float scaleA = ((float)(width/2))/width;
        float scaleB = ((float)(height/2))/height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleA,scaleB);

        Bitmap nuevaImagen = Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);

        imageView.setImageBitmap(nuevaImagen);
    }


    /**
     * Método encargado de realizar la carga de fotos
     * */
    private void mCargarFoto(Intent data) {
        Uri imagenSelecionada = data.getData();
        String[] path = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver()
                .query(imagenSelecionada,path,null,null,null);
        assert cursor != null;
        cursor.moveToFirst();
        int columna = cursor.getColumnIndex(path[0]);
        String pathimagen = cursor.getString(columna);
        cursor.close();
        bitmap = BitmapFactory.decodeFile(pathimagen);
        //BitmapFactory.Options options= new BitmapFactory.Options();
        int height= bitmap.getHeight();
        int width=bitmap.getWidth();
        float scaleA =((float)(width/2))/width;
        float scaleB =((float)(height/2))/height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleA,scaleB);
        Bitmap nuevaimagen= Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
        imageView.setImageBitmap(nuevaimagen);
    }
}

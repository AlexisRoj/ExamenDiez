package com.innovagenesis.aplicaciones.android.examendiez;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.innovagenesis.aplicaciones.android.examendiez.cubo.MyRenderer;
import com.innovagenesis.aplicaciones.android.examendiez.fragments.AnimacionesFragment;
import com.innovagenesis.aplicaciones.android.examendiez.fragments.AudioFragment;
import com.innovagenesis.aplicaciones.android.examendiez.fragments.CamaraFragment;
import com.innovagenesis.aplicaciones.android.examendiez.fragments.VideoFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int contenedor = R.id.contenedor; //fragment para cambiar

    Toolbar toolbar;

    private static final int REQUEST_CODE = 1;
    private static final String[] PERMISOS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /** Solicitud de permisos en versiones superiores*/
        int leer = ActivityCompat.checkSelfPermission
                (this,Manifest.permission.WRITE_EXTERNAL_STORAGE);

        int grabar = ActivityCompat.checkSelfPermission
                (this, Manifest.permission.RECORD_AUDIO);

        if(leer == PackageManager.PERMISSION_DENIED || grabar == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, PERMISOS, REQUEST_CODE);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        AnimacionesFragment fragment = new AnimacionesFragment();

        //instanciar fragment
        mInstanciarFragment(contenedor, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        Fragment fragment = null;

        if (id == R.id.nav_animaciones) {
            // Instanciar el fragment de animaciones
            fragment = new AnimacionesFragment();

        } else if (id == R.id.nav_graficos) {
            // Ejecuta la animacion del grafico
            GLSurfaceView lienzo = new GLSurfaceView(this);
            lienzo.setRenderer(new MyRenderer(this));
            this.setContentView(lienzo);
            getSupportActionBar().show();



        } else if (id == R.id.nav_imagen) {

            fragment = new CamaraFragment();

        } else if (id == R.id.nav_audio) {

            fragment = new AudioFragment();

        } else if (id == R.id.nav_video) {

            fragment = new VideoFragment();
        }

        if (fragment != null)
            mInstanciarFragment(contenedor, fragment).commit();



        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Método encargado de hacer la carga de todos los fragmentos
     */
    @SuppressLint("CommitTransaction")
    public FragmentTransaction mInstanciarFragment(int contenedor, Fragment fragment) {

        return getSupportFragmentManager().beginTransaction()
                .replace(contenedor, fragment);
    }
}

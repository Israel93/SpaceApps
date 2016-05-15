package com.space.alertaec;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;

import datos.ContextoDAO;
import datos.rest.UbicacionDAO;
import dto.Persona;
import dto.Ubicacion;
import enums.Accion;
import interfaces.Observador;
import servicios.ServicioAmbientLight;
import servicios.ServicioHeartRate;
import servicios.ServicioLocalizacion;
import servicios.ServicioSkinTemperature;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Observador {

    Button botopeligro;
    Button botonestoybien;
    Button botonestoyatrapado;
    Button botoncompaeroherido;
    public static Accion accion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        botoncompaeroherido = (Button) findViewById(R.id.botoncompaeroherido);
        botonestoyatrapado = (Button) findViewById(R.id.botonestoyatrapado);
        botonestoybien = (Button) findViewById(R.id.botonestoybien);
        botopeligro = (Button) findViewById(R.id.botopeligro);

        ContextoDAO.getContextoDAO().suscribirse(this);



        botoncompaeroherido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hacerCheckIn(2);
            }
        });
        botonestoyatrapado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hacerCheckIn(3);
            }
        });
        botonestoybien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hacerCheckIn(1);
            }
        });
        botopeligro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hacerCheckIn(4);
            }
        });

        iniciarServicios();
    }

    private void hacerCheckIn(int estado){
        stopService(new Intent(this, ServicioLocalizacion.class));
        accion = Accion.CHACKIN;
        Ubicacion ubicacion = ContextoDAO.getContextoDAO().getUbicacion();
        ubicacion.setIdEstado(estado);
        ServicioLocalizacion servicioLocalizacion = new ServicioLocalizacion();
        Location location = servicioLocalizacion.getLocation();
        if( location != null ) {
            ubicacion.setLatitud(String.valueOf(location.getLatitude()));
            ubicacion.setLongitud(String.valueOf(location.getLongitude()));
            ubicacion.setAltitud(String.valueOf(location.getAltitude()));
        }

        UbicacionDAO ubicacionDAO = new UbicacionDAO();
        cargarUsuarioRegistrado();
        ubicacionDAO.registrarUbicacion(ubicacion);
    }

    private void iniciarServicios() {
        ServicioHeartRate.reference = new WeakReference<Activity>(this);
        startService(new Intent(this, ServicioHeartRate.class));
        startService(new Intent(this, ServicioSkinTemperature.class));
        startService(new Intent(this, ServicioAmbientLight.class));
        startService(new Intent(this, ServicioLocalizacion.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
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
       // getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_perfil) {
            Log.i("Entro","si");
            Intent Intent = new Intent().setClass(
                    MainActivity.this, PerfilActivity.class);
            startActivity(Intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void cargarUsuarioRegistrado(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        Persona clienteRegistrado = gson.fromJson(sharedPref.getString("usuarioResgitrado", null), Persona.class);
        System.out.println("USUARIO CARGADO 1: " + clienteRegistrado);
        ContextoDAO.getContextoDAO().setPersona(clienteRegistrado);
    }

    @Override
    public void refrescar() {
        if( accion == Accion.CHECKIN_OK ) {
            Snackbar.make(botoncompaeroherido,"Tu estado a sido registrado",Snackbar.LENGTH_LONG).show();

        } else if (accion == Accion.CHECKIN_FAIL) {
            Snackbar.make(botoncompaeroherido,"Algo a fallado",Snackbar.LENGTH_LONG).show();
        }
    }
}

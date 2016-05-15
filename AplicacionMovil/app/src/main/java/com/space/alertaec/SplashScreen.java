package com.space.alertaec;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.graphics.drawable.AnimationDrawable;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import datos.ContextoDAO;
import dto.Persona;
import servicios.ServicioHeartRate;
import servicios.ServicioLocalizacion;
import servicios.ServicioSkinTemperature;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ImageView img = (ImageView)findViewById(R.id.loadingView);
        img.setBackgroundResource(R.drawable.circular_progress_view);

        AnimationDrawable loadingAnimation = (AnimationDrawable)img.getBackground();
        loadingAnimation.start();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                // Start the next activity
                if( ! esUsuarioRegistrado() ) {
                    Intent mainIntent = new Intent().setClass(
                            SplashScreen.this, RegistoActivity.class);
                    startActivity(mainIntent);
                } else {
                    Intent mainIntent = new Intent().setClass(
                            SplashScreen.this, MainActivity.class);
                    startActivity(mainIntent);
                }
                // Close the activity so the user won't able to go back this
                // activity pressing Back button
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 4000);

    }

    @Override
    protected void onStart() {
        super.onStart();
        cargarUsuarioRegistrado();
    }

    private boolean esUsuarioRegistrado(){
        return ContextoDAO.getContextoDAO().getPersona() != null;
    }

    private void cargarUsuarioRegistrado(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        Persona clienteRegistrado = gson.fromJson(sharedPref.getString("usuarioResgitrado", null), Persona.class);
        System.out.println("USUARIO CARGADO 1: " + clienteRegistrado);
        ContextoDAO.getContextoDAO().setPersona(clienteRegistrado);
    }

}

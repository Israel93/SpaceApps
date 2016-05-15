package com.space.alertaec;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import adaptadores.TelefonoListAdaptador;
import datos.ContextoDAO;
import datos.rest.PersonaDAO;
import dto.Persona;
import dto.TelefonoPersona;
import enums.Accion;
import gcm.QuickstartPreferences;
import gcm.RegistrationIntentService;
import interfaces.Observador;
import servicios.ServicioHeartRate;
import servicios.ServicioLocalizacion;
import servicios.ServicioSkinTemperature;

import android.view.View;
import android.widget.Button;
import android.content.BroadcastReceiver;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;
import com.space.alertaec.dialog.DialogTelefonoRegistro;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RegistoActivity extends AppCompatActivity implements Observador{

    //Declaramos los componentes de la actividad de forma global
    Button botonregistro;
    Button botonAddTelefono;
    ImageButton botonbuscar;
    EditText editCedula;
    EditText editNombre;
    EditText editFechanacimiento;
    EditText editLugarnacimiento;
    EditText editDomicilio;
    EditText editTelefono;
    EditText editEstadocivil;
    EditText editConyuge;
    public static Accion accion;

    //Declaracion de las variables necesarias para usar los servicios de la Play Service
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "RegistroActivity";
    BroadcastReceiver mRegistrationBroadcastReceiver;

    String android_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        //Enlazamos los componentes con su respectivo componente del layout
        botonregistro = (Button)findViewById(R.id.buttonRegistro);
        botonAddTelefono =(Button)findViewById(R.id.buttonAddTelefonos);
        botonbuscar = (ImageButton) findViewById(R.id.buttonBuscar);
        editCedula = (EditText) findViewById(R.id.editCedula);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editFechanacimiento = (EditText) findViewById(R.id.editfechanacimiento);
        editLugarnacimiento = (EditText) findViewById(R.id.datoLugarNacimientoperfil);
        editDomicilio = (EditText) findViewById(R.id.editDomicilio);
        editEstadocivil = (EditText) findViewById(R.id.editestadocivil);
        editConyuge=(EditText)findViewById(R.id.editconyuge);

        eventoBotonAgregarTelefono();


        ContextoDAO.getContextoDAO().suscribirse(this);

        botonregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarDispositivo();
            }
        });

        botonbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonaDAO personaDAO = new PersonaDAO();
                personaDAO.cargarPersona(editCedula.getText().toString());
            }
        });

    }

    @Override
    public void refrescar() {

        if( accion != null ) {
            switch (accion){
                case GUARDAR:
                    if ( ContextoDAO.getContextoDAO().getPersona().getId() != null ) {
                        guardarUsuarioRegistrado();
                        //iniciarServicios();
                        RegistoActivity.this.startActivity(new Intent(RegistoActivity.this, MainActivity.class));
                        ContextoDAO.getContextoDAO().darDeBaja(this);
                        finish();
                    }
                    break;

                case CARGAR:
                    Persona persona= ContextoDAO.getContextoDAO().getPersona();
                    editNombre.setText(persona.getNombre());
                    editFechanacimiento.setText(persona.getFechaNacimiento());
                    editLugarnacimiento.setText(persona.getLugarNacimiento());
                    editDomicilio.setText(persona.getDomicilio());
                    editEstadocivil.setText(persona.getEstadoCivil());
                    editConyuge.setText(persona.getConyuge());
                    break;
            }
        }
       // Log.i("Condicion: ",persona.getIdFacebook()+"");

    }

    //Permite abrir el Dialog donde se registraran los diferentes telefonso
    public void showAgregarTelefonoDialog()
    {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        DialogTelefonoRegistro fragmentBusquedaSaldo = DialogTelefonoRegistro.newInstance("Hola");
        fragmentBusquedaSaldo.show(fragmentManager,"dialog_fragment_busqueda_saldo");
    }


    private void eventoBotonAgregarTelefono()
    {
        botonAddTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAgregarTelefonoDialog();
            }
        });
    }

    private void registrarDispositivo(){
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    // mInformationTextView.setText(getString(R.string.gcm_send_message));
                } else {
                    // mInformationTextView.setText(getString(R.string.token_error_message));
                }
            }
        };

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(RegistoActivity.this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(getApplicationContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                // finish();
            }
            return false;
        }
        return true;
    }

    private void guardarUsuarioRegistrado(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String usuarioRegistrado = gson.toJson(ContextoDAO.getContextoDAO().getPersona());
        editor.putString("usuarioResgitrado", usuarioRegistrado);
        editor.commit();
    }
}

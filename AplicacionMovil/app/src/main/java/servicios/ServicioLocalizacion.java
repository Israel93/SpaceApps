package servicios;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import datos.ContextoDAO;
import datos.rest.UbicacionDAO;
import dto.Persona;
import dto.Ubicacion;

/**
 * Created by gabrielguevara on 12/5/16.
 */
public class ServicioLocalizacion extends Service implements com.google.android.gms.location.LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    LocationManager locationManager = null;

    @Override
    public void onCreate() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mGoogleApiClient.connect();

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        System.out.println("LOCATION = " + location.getLatitude() + " : " + location.getLongitude());
        Ubicacion ubicacion = new Ubicacion();
        cargarUsuarioRegistrado();
        ubicacion.setIdPersona(ContextoDAO.getContextoDAO().getPersona().getId().intValue());
        ubicacion.setIdEstado(1);
        ubicacion.setLongitud(String.valueOf(location.getLongitude()));
        ubicacion.setLatitud(String.valueOf(location.getLatitude()));
        ubicacion.setAltitud(String.valueOf(location.getAltitude()));
        ubicacion.setPulso(ContextoDAO.getContextoDAO().getPulso());
        ubicacion.setTemperatura(ContextoDAO.getContextoDAO().getTemperatura());
        ubicacion.setFecha("2016-02-02");
        ubicacion.setHora("12");
        ContextoDAO.getContextoDAO().setUbicacion(ubicacion);
        UbicacionDAO ubicacionDAO = new UbicacionDAO();
        ubicacionDAO.registrarUbicacion(ContextoDAO.getContextoDAO().getUbicacion());
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(5000);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    public Location getLocation() {
        Location location = null;
        boolean gpsActivo = false;
        boolean proveedorInternetActivo = false;
        boolean proveedorPasivoActivo = false;

        try{
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            gpsActivo = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            proveedorInternetActivo = locationManager.isProviderEnabled( LocationManager.NETWORK_PROVIDER );
            proveedorPasivoActivo = locationManager.isProviderEnabled( LocationManager.PASSIVE_PROVIDER );
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

        if(gpsActivo){
            System.out.println("GPS");
            location = obtenerUltimaLocalizacion( locationManager.GPS_PROVIDER );
        }else if( proveedorInternetActivo ){
            System.out.println("NETWORK");
            location = obtenerUltimaLocalizacion( locationManager.NETWORK_PROVIDER );
        }else if ( proveedorPasivoActivo ){
            System.out.println("PASSIVO");
            location = obtenerUltimaLocalizacion( locationManager.PASSIVE_PROVIDER );
        }
        return location;
    }

    private Location obtenerUltimaLocalizacion(String metodoDeLocalizacion){
        locationManager.requestLocationUpdates(metodoDeLocalizacion,
                0,
                0,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
        return locationManager.getLastKnownLocation( metodoDeLocalizacion );
    }

    private void cargarUsuarioRegistrado(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        Persona clienteRegistrado = gson.fromJson(sharedPref.getString("usuarioResgitrado", null), Persona.class);
        System.out.println("USUARIO CARGADO 1: " + clienteRegistrado);
        ContextoDAO.getContextoDAO().setPersona(clienteRegistrado);
    }
}

package com.space.alertaec;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.space.alertaec.dialog.DialogFacebookLogin;

import java.util.NoSuchElementException;

import datos.ContextoDAO;
import dto.Persona;
import interfaces.Observador;

public class PerfilActivity extends AppCompatActivity implements Observador {

    private ImageView imagenPerfil;
    private Button buttonConectarFacebook;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private Fragment facebookFragment;

    private  EditText datocedulaPerfil;
    private  EditText datonombrePerfil;
    private  EditText datofechadenacimientoPerfil;
    private  EditText datodomicilioPerfil;
    //private  EditText datocorreoPerfil;
    private  EditText datocondicionPerfil;
    private  EditText datoestadocivilPerfil;
    private  EditText datogeneroPerfil;
    private  EditText datoinstruccionPerfil;
    private  EditText datolugarnacimientoPerfil;
    private  EditText datonacionalidadPerfil;
    private  EditText datonombrepadrePerfil;
    private  EditText datonombremadrePerfil;
    private  EditText datoprofesionPerfil;
    private  EditText datoconyugePerfil;
    private  ListView datotelefonoPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_perfil);


        imagenPerfil = (ImageView)findViewById(R.id.imagenPerfil);
        datocedulaPerfil = (EditText)findViewById(R.id.datocedulaperfil);
        datonombrePerfil = (EditText)findViewById(R.id.datoNombreperfil);
        datofechadenacimientoPerfil = (EditText)findViewById(R.id.datofechadenacimientoperfil);
        datodomicilioPerfil = (EditText)findViewById(R.id.datodomicilioperfil);
        //datocorreoPerfil = (EditText)findViewById(R.id.datocorreoperfil);
        datocondicionPerfil = (EditText)findViewById(R.id.datocondicionperfil);
        datoestadocivilPerfil = (EditText)findViewById(R.id.datoestadocivilperfil);
        datogeneroPerfil = (EditText)findViewById(R.id.datogeneroperfil);
        datoinstruccionPerfil = (EditText)findViewById(R.id.datoinstruccionperfil);
        datolugarnacimientoPerfil = (EditText)findViewById(R.id.datoLugarNacimientoperfil);
        datonacionalidadPerfil = (EditText)findViewById(R.id.datonacionalidadperfil);
        datonombrepadrePerfil = (EditText)findViewById(R.id.datonombrepadreperfil);
        datonombremadrePerfil = (EditText)findViewById(R.id.datonombremadreperfil);
        datoprofesionPerfil = (EditText)findViewById(R.id.datoprofesionperfil);
        datoconyugePerfil = (EditText)findViewById(R.id.datoconyugeperfil);
        datotelefonoPerfil = (ListView)findViewById(R.id.listatelefonoperfil);
        buttonConectarFacebook = (Button)findViewById(R.id.buttonConectarFacebook);

        refrescar();




        buttonConectarFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextoDAO.getContextoDAO().getPersona().isLogeadoFB())
                {
                    Snackbar.make(view,"Estas Logeado",Snackbar.LENGTH_LONG);
                }
                else
                {
                    showLoginFacebook();
                }

                //LoginManager.getInstance().logOut();
            }
        });


        scaleImage(imagenPerfil);


        //iniciarSesionFacebook();





        cargarToolBar();
    }

    private void cargarToolBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    public void showLoginFacebook()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogFacebookLogin fragmentBusquedaSaldo = DialogFacebookLogin.newInstance("Hola");
        fragmentBusquedaSaldo.show(fragmentManager,"dialog_fragment_busqueda_saldo");
    }
    private void iniciarSesionFacebook()
    {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        Log.i("ID_facebook",loginResult.getAccessToken().getUserId());
                        Log.i("ID_facebook",loginResult.getAccessToken()+"");

                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                }

        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.e("data",data.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_perfil, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =item.getItemId();
        switch (id)
        {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    private void scaleImage(ImageView view) throws NoSuchElementException {
        // Get bitmap from the the ImageView.
        Bitmap bitmap = null;

        try {
            Drawable drawing = view.getDrawable();
            bitmap = ((BitmapDrawable) drawing).getBitmap();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("No drawable on given view");
        } catch (ClassCastException e) {
            // Check bitmap is Ion drawable
            //bitmap = Ion.with(view).getBitmap();
        }

        // Get current dimensions AND the desired bounding box
        int width = 0;

        try {
            width = bitmap.getWidth();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("Can't find bitmap on given view/drawable");
        }

        int height = bitmap.getHeight();
        int bounding = dpToPx(70);

        // Determine how much to scale: the dimension requiring less scaling is
        // closer to the its side. This way the image always stays inside your
        // bounding box AND either x/y axis touches it.
        float xScale = ((float) bounding) / width;
        float yScale = ((float) bounding) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;

        // Create a matrix for the scaling and add the scaling data
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create a new bitmap and convert it to a format understood by the ImageView
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        width = scaledBitmap.getWidth(); // re-use
        height = scaledBitmap.getHeight(); // re-use
        BitmapDrawable result = new BitmapDrawable(scaledBitmap);

        // Apply the scaled bitmap
        view.setImageDrawable(result);

        // Now change ImageView's dimensions to match the scaled image
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    private int dpToPx(int dp) {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }

    @Override
    public void refrescar() {

        Persona persona = ContextoDAO.getContextoDAO().getPersona();
        datocedulaPerfil.setText(persona.getCedula());
        datonombrePerfil.setText(persona.getNombre());
        datofechadenacimientoPerfil.setText(persona.getFechaNacimiento());
        datodomicilioPerfil.setText(persona.getDomicilio());
        //datocorreoPerfil.setText(persona.getCorreo());
        datocondicionPerfil.setText(persona.getCondicion());
        datoestadocivilPerfil.setText(persona.getEstadoCivil());
        datogeneroPerfil.setText(persona.getGenero());
        datoinstruccionPerfil.setText(persona.getInstruccion());
        datolugarnacimientoPerfil.setText(persona.getLugarNacimiento());
        datonacionalidadPerfil.setText(persona.getNacionalidad());
        datonombrepadrePerfil.setText(persona.getNombrePadre());
        datonombremadrePerfil.setText(persona.getNombreMadre());
        datoprofesionPerfil.setText(persona.getProfesion());
        datoconyugePerfil.setText(persona.getConyuge());
        //datotelefonoPerfil.setAdapter(new ArrayAdapter<String >());
    }
}

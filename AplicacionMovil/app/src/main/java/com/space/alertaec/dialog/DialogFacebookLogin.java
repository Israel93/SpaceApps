package com.space.alertaec.dialog;

import android.app.Dialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.space.alertaec.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.json.JSONObject;

import datos.ContextoDAO;
import dto.UserFacebook;
import interfaces.Observador;

/**
 * Created by PárragaCedeñoJordán on 15/5/2016.
 */
public class DialogFacebookLogin extends DialogFragment implements Observador {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private Bitmap bitmap;
    private ImageView imagePerfil;
    private UserFacebook user;


    public static DialogFacebookLogin newInstance(String title) {
        DialogFacebookLogin frag = new DialogFacebookLogin();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo(
                    "com.space.alertaec",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        callbackManager = CallbackManager.Factory.create();
        View view = inflater.inflate(R.layout.dialog_facebook_login, container);

        imagePerfil = (ImageView) view.findViewById(R.id.imagenPerfil);
        loginButton = (LoginButton) view.findViewById(R.id.login_button);

        iniciarSesionFacebook();
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;

    }

    //Permite abrir el Dialog donde se registraran los diferentes telefonso
    private void iniciarSesionFacebook() {
        loginButton.setReadPermissions("user_friends");
        loginButton.setFragment(this);
        loginButton.performClick();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String token = loginResult.getAccessToken().getToken();
                String id = loginResult.getAccessToken().getUserId();
                Log.i("Token", token);
                Log.i("Id", id);

                ContextoDAO.getContextoDAO().getPersona().setIdFacebook(id);
                ContextoDAO.getContextoDAO().getPersona().setLogeadoFB(true);
                refrescar();
                dismiss();
            }

            @Override
            public void onCancel() {
                ContextoDAO.getContextoDAO().getPersona().setLogeadoFB(false);
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("Error Feacebook",error.getMessage());
            }
        });


    }

    @Override
    public void refrescar() {

    }
}




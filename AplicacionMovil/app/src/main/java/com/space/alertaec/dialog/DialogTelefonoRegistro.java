package com.space.alertaec.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.facebook.login.widget.LoginButton;
import com.space.alertaec.R;

import java.util.ArrayList;
import java.util.List;

import adaptadores.TelefonoListAdaptador;
import datos.ContextoDAO;
import dto.TelefonoPersona;
import interfaces.Observador;

/**
 * Created by PárragaCedeñoJordán on 13/5/2016.
 */
public class DialogTelefonoRegistro extends DialogFragment implements Observador {

    ListView listTelefonoPersonal;
    ListView listTelefonoEmergencia;
    Button buttonAddTelefonoPersonal;
    Button buttonAddTelefonoEmergencia;
    Button buttonGuardar;
    Button buttonSalir;
    LoginButton loginButton;
    List<TelefonoPersona> listaTelefonoPersonasPersonal = new ArrayList<>();
    List<TelefonoPersona> listaTelefonoPersonasEmergencia = new ArrayList<>();
    TelefonoListAdaptador telefonoListAdaptadorPersonal;
    TelefonoListAdaptador telefonoListAdaptadorEmergencia;

    public static DialogTelefonoRegistro newInstance(String title) {
        DialogTelefonoRegistro frag = new DialogTelefonoRegistro();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_telefono_registro,container);

        listTelefonoPersonal =(ListView)view.findViewById(R.id.listaTelefonoPersonal);
        listTelefonoEmergencia=(ListView)view.findViewById(R.id.listaTelefonoEmergencia);

        buttonAddTelefonoPersonal=(Button) view.findViewById(R.id.buttonAddTelefonoContacto);
        buttonAddTelefonoEmergencia=(Button) view.findViewById(R.id.buttonAddTelefonoEmergencia);



        listaTelefonoPersonasPersonal.add(new TelefonoPersona("Descripcion",false));
        listaTelefonoPersonasEmergencia = new ArrayList<>();
        listaTelefonoPersonasEmergencia.add(new TelefonoPersona("Descripcion",false));

        telefonoListAdaptadorPersonal = new TelefonoListAdaptador(this.getContext(),listaTelefonoPersonasPersonal);
        telefonoListAdaptadorEmergencia = new TelefonoListAdaptador(this.getContext(),listaTelefonoPersonasEmergencia);

        listTelefonoPersonal.setAdapter(telefonoListAdaptadorPersonal);
        listTelefonoEmergencia.setAdapter(telefonoListAdaptadorEmergencia);

        buttonGuardar = (Button)view.findViewById(R.id.buttonGuardar);
        buttonSalir = (Button)view.findViewById(R.id.buttonSalir);

        eventoBotonesAddTelefonos();

        eventoBotonGuardar();
        eventoBotonSalir();


        return view;
    }

    private void eventoBotonGuardar()
    {
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recorrerListGetDatos(listTelefonoPersonal,listaTelefonoPersonasPersonal,false);
                recorrerListGetDatos(listTelefonoEmergencia,listaTelefonoPersonasEmergencia,true);
                listaTelefonoPersonasPersonal.addAll(listaTelefonoPersonasEmergencia);
                refrescar();

                telefonoListAdaptadorPersonal.notifyDataSetChanged();
                dismiss();
            }
        });
    }

    private void recorrerListGetDatos(ListView lista,List<TelefonoPersona>listaPersona,boolean emergencia)
    {
        int numItem = lista.getAdapter().getCount();


        for(int i=0;i<numItem;i++)
        {
            ViewGroup v =(ViewGroup) lista.getChildAt(i);
            EditText editNumero = (EditText)v.findViewById(R.id.editTextTelefono);
            //Spinner spinnerTipo= (Spinner) v.findViewById(R.id.spinnerTipoTelefono);
            listaPersona.get(i).setNumero(editNumero.getText().toString());
            listaPersona.get(i).setIdTipo(1);

        }
    }
    private void eventoBotonSalir()
    {
        buttonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    // Se aumenta el tamaño de list por medio de la cantidad de hijos que tenga
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;

    }

    @Override
    public void refrescar() {

        ContextoDAO.getContextoDAO().getPersona().setTelefono(listaTelefonoPersonasPersonal);

    }

    private void eventoBotonesAddTelefonos()
    {
        buttonAddTelefonoPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listaTelefonoPersonasPersonal.add(new TelefonoPersona("Descripcion",false));
                if(listaTelefonoPersonasPersonal.size()<3)
                {
                    setListViewHeightBasedOnChildren(listTelefonoPersonal);
                }

                telefonoListAdaptadorPersonal.notifyDataSetChanged();

            }
        });

        buttonAddTelefonoEmergencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaTelefonoPersonasEmergencia.add(new TelefonoPersona("Descripcion",true));
                if(listaTelefonoPersonasEmergencia.size()<3)
                {
                    setListViewHeightBasedOnChildren(listTelefonoEmergencia);
                }
                telefonoListAdaptadorEmergencia.notifyDataSetChanged();

            }
        });
    }
}

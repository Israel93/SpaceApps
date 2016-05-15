package adaptadores;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.space.alertaec.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dto.TelefonoPersona;
import dto.TipoTelefono;

/**
 * Created by PárragaCedeñoJordán on 12/5/2016.
 */
public class TelefonoListAdaptador extends ArrayAdapter<TelefonoPersona> {

    private EditText editTextTelefono;
    private Spinner spinnerTipo;
    private ImageButton imageButtonCancelar;
    private List<String> listTipoTelefono;
    private int posicionItem;

    public TelefonoListAdaptador(Context context, List<TelefonoPersona> objects) {
        super(context,0,objects);

        cargarListTipoTelefono();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listItemView = convertView;

        if (null == convertView) {
            listItemView = inflater.inflate(
                    R.layout.item_telefono_registro,
                    parent,
                    false);
        }

        this.posicionItem =position;
        this.imageButtonCancelar = (ImageButton)listItemView.findViewById(R.id.imageButtonClose);
        //this.editTextTelefono =(EditText) listItemView.findViewById(R.id.editTelefono);
        this.spinnerTipo =(Spinner) listItemView.findViewById(R.id.spinnerTipoTelefono);

        cargarSpinner();

        eliminarItem();


        return listItemView;
    }

    private void eliminarItem() {

        imageButtonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("Eliminar:","Elimino");
                notifyDataSetChanged();

            }
        });
    }

    private void cargarSpinner() {


        ArrayAdapter spinnerAdaptador = new ArrayAdapter(this.getContext(),android.R.layout.simple_spinner_item,listTipoTelefono);
        spinnerAdaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(spinnerAdaptador);
    }

    private void cargarListTipoTelefono()
    {
        List<TipoTelefono> listTipoTelefonoObjeto = new ArrayList<>();

        listTipoTelefonoObjeto.add(new TipoTelefono(new Long(2),"Movil"));
        listTipoTelefonoObjeto.add(new TipoTelefono(new Long(3),"Hogar"));
        listTipoTelefonoObjeto.add(new TipoTelefono(new Long(4),"Trabajo"));

        this.listTipoTelefono=new ArrayList<>();

        for(TipoTelefono t : listTipoTelefonoObjeto)
        {
            this.listTipoTelefono.add(t.getDescipcion());
        }


    }


}

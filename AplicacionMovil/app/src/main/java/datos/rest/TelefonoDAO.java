package datos.rest;

import com.space.alertaec.RegistoActivity;

import datos.ContextoDAO;
import dto.TelefonoPersona;
import enums.Accion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import servicios.ServicioTelefono;

/**
 * Created by gabrielguevara on 14/5/16.
 */
public class TelefonoDAO extends RestDAO {

    public void registrarTelefono(TelefonoPersona telefonoPersona){

        ServicioTelefono servicioTelefono = createService(ServicioTelefono.class);
        servicioTelefono.registrarTelefono(
                telefonoPersona.getIdGcm(), telefonoPersona.getIdPersona(),
                telefonoPersona.getNombre(), telefonoPersona.getNumero(), telefonoPersona.isEmergencia() ? 2 : 1
        ).enqueue(new Callback<TelefonoPersona>() {
            @Override
            public void onResponse(Call<TelefonoPersona> call, Response<TelefonoPersona> response) {
                System.out.println("TELEFONO GUARDADO = " + response.body());
                RegistoActivity.accion = Accion.GUARDAR;
                ContextoDAO.getContextoDAO().notificar();
            }

            @Override
            public void onFailure(Call<TelefonoPersona> call, Throwable t) {
                System.out.println("TELEFONO ERROR = " + t.getMessage());
            }
        });

    }

}

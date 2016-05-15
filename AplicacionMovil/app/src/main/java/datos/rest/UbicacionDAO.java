package datos.rest;

import com.space.alertaec.MainActivity;

import datos.ContextoDAO;
import dto.Ubicacion;
import enums.Accion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import servicios.ServicioUbicacion;

/**
 * Created by gabrielguevara on 15/5/16.
 */
public class UbicacionDAO extends RestDAO {

    public void registrarUbicacion(Ubicacion ubicacion){
        ServicioUbicacion servicioUbicacion = createService(ServicioUbicacion.class);

        servicioUbicacion.registrarUbicacion(
                ubicacion.getLongitud(), ubicacion.getLatitud(),
                ubicacion.getAltitud(), ubicacion.getFecha(),
                ubicacion.getHora(), String.valueOf(ContextoDAO.getContextoDAO().getPersona().getId()),
                String.valueOf(ubicacion.getIdEstado()), ubicacion.getPulso(), ubicacion.getTemperatura()
        ).enqueue(new Callback<Ubicacion>() {
            @Override
            public void onResponse(Call<Ubicacion> call, Response<Ubicacion> response) {
                System.out.println("UBICACION REG = " + response.body());
                if(MainActivity.accion == Accion.CHACKIN) MainActivity.accion = Accion.CHECKIN_OK;
                ContextoDAO.getContextoDAO().notificar();
            }

            @Override
            public void onFailure(Call<Ubicacion> call, Throwable t) {
                System.out.println("ERROR = " + t.getMessage());
                if(MainActivity.accion == Accion.CHACKIN) MainActivity.accion = Accion.CHECKIN_FAIL;
                ContextoDAO.getContextoDAO().notificar();
            }
        });

    }

}

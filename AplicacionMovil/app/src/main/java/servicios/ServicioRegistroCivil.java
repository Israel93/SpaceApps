package servicios;

import dto.RespuestaRegistroCivil;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by PárragaCedeñoJordán on 12/5/2016.
 */
public interface ServicioRegistroCivil {

    @GET("wsregistrocivil/buscardatosporcedula.php")
    Call<RespuestaRegistroCivil> getPersonaPorCedula(@Query("cedula") String cedula);

}

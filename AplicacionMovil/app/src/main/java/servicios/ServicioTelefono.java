package servicios;

import dto.TelefonoPersona;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by gabrielguevara on 14/5/16.
 */
public interface ServicioTelefono {

    @FormUrlEncoded
    @POST("alertaec/index.php/api/telefono/")
    Call<TelefonoPersona> registrarTelefono(@Field("id_gcm") String id_gcm,
                                            @Field("persona_id") int persona_id,
                                            @Field("nombre") String nombre,
                                            @Field("numero") String numero,
                                            @Field("tipo_telefono_id") int tipo_telefono_id
                                            );

}

package servicios;

import dto.Ubicacion;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by gabrielguevara on 15/5/16.
 */
public interface ServicioUbicacion {

    @FormUrlEncoded
    @POST("alertaec/index.php/api/ubicacion/")
    Call<Ubicacion> registrarUbicacion(
            @Field("longitud") String longitud,
            @Field("latitud") String latitud,
            @Field("altitud") String altitud,
            @Field("fecha") String fecha,
            @Field("hora") String hora,
            @Field("persona_id") String persona_id,
            @Field("estado_id") String estado_id,
            @Field("pulso") String pulso,
            @Field("temperatura") String temperatura
                                       );

}

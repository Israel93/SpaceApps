package servicios;

import dto.Persona;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by gabrielguevara on 14/5/16.
 */
public interface ServicioPersona {

    @FormUrlEncoded
    @POST("alertaec/index.php/api/persona/")
    Call<Persona> registrarPersona(@Field("cedula") String cedula,
                                   @Field("nombre") String nombre,
                                   @Field("fechaNacimiento") String fechaNacimiento,
                                   @Field("direccion") String direccion,
                                   @Field("correo") String correo,
                                   @Field("condicion") String condicion,
                                   @Field("domicilio") String domicilio,
                                   @Field("estadoCivil") String estadoCivil,
                                   @Field("genero") String genero,
                                   @Field("instruccion") String instruccion,
                                   @Field("lugarNacimiento") String lugarNacimiento,
                                   @Field("nacionalidad") String nacionalidad,
                                   @Field("nombrePadre") String nombrePadre,
                                   @Field("nombreMadre") String nombreMadre,
                                   @Field("profesion") String profesion,
                                   @Field("conyuge") String conyuge,
                                   @Field("idFacebook") String idFacebook);

}

package datos.rest;

import android.util.Log;

import com.space.alertaec.RegistoActivity;

import java.util.List;

import datos.ContextoDAO;
import dto.Persona;
import dto.RespuestaRegistroCivil;
import dto.TelefonoPersona;
import enums.Accion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import servicios.ServicioPersona;
import servicios.ServicioRegistroCivil;

/**
 * Created by PárragaCedeñoJordán on 12/5/2016.
 */
public class PersonaDAO extends RestDAO {

    public void cargarPersona(String cedula) {
        forzarJsonParser(endPointWS);
        ServicioRegistroCivil servicioRegistroCivil = createService(ServicioRegistroCivil.class);
        servicioRegistroCivil.getPersonaPorCedula(cedula).enqueue(new Callback<RespuestaRegistroCivil>() {
            @Override
            public void onResponse(Call<RespuestaRegistroCivil> call, Response<RespuestaRegistroCivil> response) {
                Persona persona = new Persona();

                persona.setCedula(response.body().getCedula());
                persona.setNombre(response.body().getNombre());
                persona.setCondicion(response.body().getCondicionCiudadano());
                persona.setEstadoCivil(response.body().getEstadoCivil());
                persona.setGenero(response.body().getGenero());
                persona.setInstruccion(response.body().getInstruccion());
                persona.setNacionalidad(response.body().getNacionalidad());
                persona.setNombreMadre(response.body().getNombreMadre());
                persona.setNombrePadre(response.body().getNombrePadre());
                persona.setLugarNacimiento(response.body().getLugarNacimiento());
                persona.setProfesion(response.body().getProfesion());
                persona.setDomicilio(response.body().getDomicilio());
                persona.setFechaNacimiento(response.body().getFechaNacimiento());
                persona.setConyuge(response.body().getConyuge());

                ContextoDAO.getContextoDAO().setPersona(persona);
                System.out.println("PERSONA RECUPERADA = " + ContextoDAO.getContextoDAO().getPersona());
                RegistoActivity.accion = Accion.CARGAR;
                ContextoDAO.getContextoDAO().notificar();
            }

            @Override
            public void onFailure(Call<RespuestaRegistroCivil> call, Throwable t) {
                Log.i("Error: ",t.getMessage()+"");
            }
        });
    }

    public void registrarPersona(Persona persona) {
        ServicioPersona servicioPersona = createService(ServicioPersona.class);

        servicioPersona.registrarPersona(persona.getCedula(),
                persona.getNombre(),
                persona.getFechaNacimiento(),
                persona.getDireccion(),
                persona.getCorreo(),
                persona.getCondicion(),
                persona.getDomicilio(),
                persona.getEstadoCivil(),
                persona.getGenero(),
                persona.getInstruccion(),
                persona.getLugarNacimiento(),
                persona.getNacionalidad(),
                persona.getNombrePadre(),
                persona.getNombreMadre(),
                persona.getProfesion(),
                persona.getConyuge(),
                persona.getIdFacebook()
        ).enqueue(new Callback<Persona>() {
            @Override
            public void onResponse(Call<Persona> call, Response<Persona> response) {
                System.out.println("PERSONA RECIBIDA = " + response.body());
                ContextoDAO.getContextoDAO().getPersona().setId(response.body().getId());
                registrarTelefonosPersona(ContextoDAO.getContextoDAO().getPersona());
            }

            @Override
            public void onFailure(Call<Persona> call, Throwable t) {
                System.out.println("ERROR PERSONA = " + t.getMessage());
            }
        });
    }

    private void registrarTelefonosPersona(Persona persona){
        if( persona != null && persona.getTelefono() != null ) {
            for (TelefonoPersona telefonoPersona : persona.getTelefono()) {
                TelefonoDAO telefonoDAO = new TelefonoDAO();
                telefonoPersona.setIdPersona(persona.getId().intValue());
                if( telefonoPersona.getIdGcm() == null ) telefonoPersona.setIdGcm("");
                telefonoDAO.registrarTelefono(telefonoPersona);
            }
        }
    }
}

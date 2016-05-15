package datos;

import java.util.ArrayList;

import dto.Persona;
import dto.Ubicacion;
import interfaces.Observador;
import interfaces.Sujeto;

/**
 * Created by gabrielguevara on 12/5/16.
 */
public class ContextoDAO implements Sujeto {

    private static ContextoDAO contextoDAO;
    private ArrayList<Observador> observadores =  new ArrayList<>();

    private Persona persona;
    private String pulso;
    private String temperatura;
    private Ubicacion ubicacion;

    private ContextoDAO() {
    }

    public static synchronized ContextoDAO getContextoDAO(){
        if(contextoDAO == null ) {
            contextoDAO = new ContextoDAO();
        }
        return contextoDAO;
    }

    @Override
    public void suscribirse(Observador observador) {
        if( !observadores.contains(observador) ) observadores.add(observador);
    }

    @Override
    public void darDeBaja(Observador observador) {
        if( observadores.contains(observador)) observadores.remove(observador);
    }

    @Override
    public void notificar() {
        for(Observador observador : observadores) {
            observador.refrescar();
        }
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getPulso() {
        return pulso;
    }

    public void setPulso(String pulso) {
        this.pulso = pulso;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }
}

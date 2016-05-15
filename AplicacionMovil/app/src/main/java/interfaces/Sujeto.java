package interfaces;

/**
 * Created by gabrielguevara on 12/5/16.
 */
public interface Sujeto {

    void notificar();

    void suscribirse(Observador observador);

    void darDeBaja(Observador observador);

}

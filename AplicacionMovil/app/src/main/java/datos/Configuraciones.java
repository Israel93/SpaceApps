package datos;

/**
 * Created by gabrielguevara on 12/5/16.
 */
public class Configuraciones  {

    private static Configuraciones configuraciones;

    private Configuraciones() {
    }

    public static synchronized Configuraciones getConfiguraciones(){
        if( configuraciones == null ) {
            configuraciones = new Configuraciones();
        }
        return configuraciones;
    }

}

package dto;

/**
 * Created by PárragaCedeñoJordán on 15/5/2016.
 */
public class UserFacebook {

    private String iD;
    private String nombre;
    private String correo;

    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}

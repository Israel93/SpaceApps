package dto;

import com.orm.dsl.Ignore;
import com.orm.dsl.Table;

/**
 * Created by PárragaCedeñoJordán on 12/5/2016.
 */
@Table
public class TelefonoPersona {

    private Long id;
    private String idGcm;
    @Ignore
    private int idPersona;
    private String numero;
    private String nombre;
    private boolean emergencia;
    @Ignore
    private int idTipo;

    private Persona persona;
    private TipoTelefono tipoTelefono;
    public TelefonoPersona(String nombre,Boolean emergencia) {
        this.emergencia=emergencia;
        this.nombre = nombre;
    }

    public boolean isEmergencia() {
        return emergencia;
    }

    public void setEmergencia(boolean emergencia) {
        this.emergencia = emergencia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdGcm() {
        return idGcm;
    }

    public void setIdGcm(String idGcm) {
        this.idGcm = idGcm;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public TipoTelefono getTipoTelefono() {
        return tipoTelefono;
    }

    public void setTipoTelefono(TipoTelefono tipoTelefono) {
        this.tipoTelefono = tipoTelefono;
    }

    @Override
    public String toString() {
        return "TelefonoPersona{" +
                "id=" + id +
                ", idGcm='" + idGcm + '\'' +
                ", idPersona=" + idPersona +
                ", numero='" + numero + '\'' +
                ", nombre='" + nombre + '\'' +
                ", emergencia=" + emergencia +
                ", idTipo=" + idTipo +
                ", persona=" + persona +
                ", tipoTelefono=" + tipoTelefono +
                '}';
    }
}

package dto;

/**
 * Created by gabrielguevara on 13/5/16.
 */
public class TelefonoInstitucion {

    private Long id;
    private int idGcm;
    private int idInstitucion;
    private int numero;
    private String nombre;
    private int idTipo;

    private InstitucionHabilitada institucionHabilitada;
    private TipoTelefono tipoTelefono;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIdGcm() {
        return idGcm;
    }

    public void setIdGcm(int idGcm) {
        this.idGcm = idGcm;
    }

    public int getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(int idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
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

    public InstitucionHabilitada getInstitucionHabilitada() {
        return institucionHabilitada;
    }

    public void setInstitucionHabilitada(InstitucionHabilitada institucionHabilitada) {
        this.institucionHabilitada = institucionHabilitada;
    }

    public TipoTelefono getTipoTelefono() {
        return tipoTelefono;
    }

    public void setTipoTelefono(TipoTelefono tipoTelefono) {
        this.tipoTelefono = tipoTelefono;
    }
}

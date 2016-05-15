package dto;

import com.orm.dsl.Ignore;
import com.orm.dsl.Table;

/**
 * Created by gabrielguevara on 13/5/16.
 */
@Table
public class Provincia {

    private Long id;
    private String nombre;
    @Ignore
    private int idPais;
    private Pais pais;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }
}

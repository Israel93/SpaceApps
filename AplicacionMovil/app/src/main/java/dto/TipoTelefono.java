package dto;

import com.orm.dsl.Table;

/**
 * Created by PárragaCedeñoJordán on 12/5/2016.
 */
@Table
public class TipoTelefono {

    private Long id;
    private String descipcion;

    public TipoTelefono(Long id, String descipcion) {
        this.id = id;
        this.descipcion = descipcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescipcion() {
        return descipcion;
    }

    public void setDescipcion(String descipcion) {
        this.descipcion = descipcion;
    }


}

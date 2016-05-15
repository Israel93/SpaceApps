package dto;

import com.orm.dsl.Table;

/**
 * Created by gabrielguevara on 13/5/16.
 */
@Table
public class Estado {

    private Long id;
    private String estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

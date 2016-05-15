package dto;

import com.orm.dsl.Table;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by gabrielguevara on 13/5/16.
 */
@Table
public class Notificacion {

    private Long id;
    private String titulo;
    private String texto;
    private Date fecha_enviada;
    private Time hora_enviada;
    private String respuesta_gcm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getFecha_enviada() {
        return fecha_enviada;
    }

    public void setFecha_enviada(Date fecha_enviada) {
        this.fecha_enviada = fecha_enviada;
    }

    public Time getHora_enviada() {
        return hora_enviada;
    }

    public void setHora_enviada(Time hora_enviada) {
        this.hora_enviada = hora_enviada;
    }

    public String getRespuesta_gcm() {
        return respuesta_gcm;
    }

    public void setRespuesta_gcm(String respuesta_gcm) {
        this.respuesta_gcm = respuesta_gcm;
    }
}

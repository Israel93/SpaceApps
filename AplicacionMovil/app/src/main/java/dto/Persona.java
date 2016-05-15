package dto;

import com.google.gson.annotations.SerializedName;
import com.orm.dsl.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PárragaCedeñoJordán on 12/5/2016.
 */
@Table
public class Persona {

    private Long id;
    private String cedula;
    private String nombre;
    private String fechaNacimiento;
    private String direccion;
    private String correo;
    private String condicion;
    private String domicilio;
    private String estadoCivil;
    private String genero;
    private String instruccion;
    private String lugarNacimiento;
    private String nacionalidad;
    private String nombrePadre;
    private String nombreMadre;
    private String profesion;
    private String conyuge;
    private List<TelefonoPersona> telefono= new ArrayList<>();
    private String idFacebook;
    private boolean isLogeadoFB;

    public List<TelefonoPersona> getTelefono() {
        return telefono;
    }

    public void setTelefono(List<TelefonoPersona> telefono) {
        this.telefono = telefono;
    }

    public String getConyuge() {
        return conyuge;
    }

    public void setConyuge(String conyuge) {
        this.conyuge = conyuge;
    }

    public Long getId() {
        return id;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getInstruccion() {
        return instruccion;
    }

    public void setInstruccion(String instruccion) {
        this.instruccion = instruccion;
    }

    public String getLugarNacimiento() {
        return lugarNacimiento;
    }

    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getNombrePadre() {
        return nombrePadre;
    }

    public void setNombrePadre(String nombrePadre) {
        this.nombrePadre = nombrePadre;
    }

    public String getNombreMadre() {
        return nombreMadre;
    }

    public void setNombreMadre(String nombreMadre) {
        this.nombreMadre = nombreMadre;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getIdFacebook() {
        return idFacebook;
    }

    public void setIdFacebook(String idFacebook) {
        this.idFacebook = idFacebook;
    }

    public boolean isLogeadoFB() {
        return isLogeadoFB;
    }

    public void setLogeadoFB(boolean logeadoFB) {
        isLogeadoFB = logeadoFB;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", cedula='" + cedula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", direccion='" + direccion + '\'' +
                ", correo='" + correo + '\'' +
                ", condicion='" + condicion + '\'' +
                ", domicilio='" + domicilio + '\'' +
                ", estadoCivil='" + estadoCivil + '\'' +
                ", genero='" + genero + '\'' +
                ", instruccion='" + instruccion + '\'' +
                ", lugarNacimiento='" + lugarNacimiento + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", nombrePadre='" + nombrePadre + '\'' +
                ", nombreMadre='" + nombreMadre + '\'' +
                ", profesion='" + profesion + '\'' +
                ", conyuge='" + conyuge + '\'' +
                ", idFacebook='" + idFacebook + '\'' +
                '}';
    }
}


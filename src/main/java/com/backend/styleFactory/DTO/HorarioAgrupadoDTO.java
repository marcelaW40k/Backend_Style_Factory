package com.backend.styleFactory.DTO;

import org.apache.juli.logging.Log;

import java.util.*;

public class HorarioAgrupadoDTO {

    private Long idEmpleado;

    private Long idUsuario;
    private String nombreEmpleado;

    private String especialidad;
    private String urlImagen;

    private Map<String, List<String>> fechas;

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Map<String, List<String>> getFechas() {
        return fechas;
    }

    public void setFechas(Map<String, List<String>> fechas) {
        this.fechas = fechas;
    }
}

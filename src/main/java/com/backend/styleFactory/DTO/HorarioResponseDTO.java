package com.backend.styleFactory.DTO;

import com.backend.styleFactory.model.Horario;

import java.time.LocalDateTime;

public class HorarioResponseDTO {

    private Long idHorario;
    private LocalDateTime fechaHora;
    private String nombreEmpleado;
    private Long idEmmpleado;
    private Long idUsuario;

    // Constructor vacío
    public HorarioResponseDTO() {
    }

    // Constructor con parámetros
    public static HorarioResponseDTO desde(Horario horario) {
        HorarioResponseDTO dto = new HorarioResponseDTO();
        dto.idHorario = horario.getIdHorario();
        dto.fechaHora = horario.getFechaHora();
        if (horario.getEmpleado() != null) {
            dto.nombreEmpleado = horario.getEmpleado().getUsuario().getNombre();
        }
        if (horario.getEmpleado().getId() != null){
            dto.idEmmpleado = horario.getEmpleado().getId();
        }
        if (horario.getEmpleado().getUsuario().getId() != null){
            dto.idUsuario = horario.getEmpleado().getUsuario().getId();
        }
        return dto;
    }

    // Getters y Setters


    public Long getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Long idHorario) {
        this.idHorario = idHorario;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public Long getIdEmmpleado() {
        return idEmmpleado;
    }

    public void setIdEmmpleado(Long idEmmpleado) {
        this.idEmmpleado = idEmmpleado;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}

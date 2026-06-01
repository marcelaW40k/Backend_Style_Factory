package com.backend.styleFactory.service;

import com.backend.styleFactory.DTO.HorarioAgrupadoDTO;
import com.backend.styleFactory.DTO.HorarioRequestDTO;
import com.backend.styleFactory.DTO.HorarioResponseDTO;
import com.backend.styleFactory.model.Empleado;
import com.backend.styleFactory.model.Horario;
import com.backend.styleFactory.repository.EmpleadoRepository;
import com.backend.styleFactory.repository.HorarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HorarioService {

    private final HorarioRepository horarioRepository;
    private final EmpleadoRepository empleadoRepository;

    public HorarioService(HorarioRepository horarioRepository,
                          EmpleadoRepository empleadoRepository) {
        this.horarioRepository = horarioRepository;
        this.empleadoRepository = empleadoRepository;
    }

    /**
     * Lista todos los horarios registrados, incluyendo el ID del empleado asociado.
     *
     * @return Lista de HorarioResponseDTO
     */
    public List<HorarioResponseDTO> findAll(){
        return horarioRepository.findAll()
                .stream()
                .map(HorarioResponseDTO::desde)
                .collect(Collectors.toList());
    }

    /**
     * Guarda un nuevo horario asociado a un empleado existente.
     * Si el empleado no se encuentra, lanza una excepción.
     *
     * @param requestDTO Datos del horario (fecha/hora y empleadoId)
     * @return HorarioResponseDTO con los datos guardados
     */
    public HorarioResponseDTO guardarHorario(HorarioRequestDTO requestDTO) {
        Empleado empleado = empleadoRepository.findById(requestDTO.getEmpleadoId())
                .orElseThrow(() -> new RuntimeException(
                        "Empleado no encontrado con id: " + requestDTO.getEmpleadoId()));

        Horario horario = new Horario(empleado, requestDTO.getFechaHora());
        Horario guardado = horarioRepository.save(horario);

        return HorarioResponseDTO.desde(guardado);
    }


    public List<HorarioAgrupadoDTO> findAllAgrupados() {

        List<Horario> horarios = horarioRepository.findAll();

        Map<Long, List<Horario>> horariosPorEmpleado =
                horarios.stream()
                        .collect(Collectors.groupingBy(
                                h -> h.getEmpleado().getId()
                        ));

        return horariosPorEmpleado.values()
                .stream()
                .map(listaHorarios -> {

                    Horario primerHorario =
                            listaHorarios.get(0);

                    HorarioAgrupadoDTO dto = new HorarioAgrupadoDTO();

                    dto.setIdEmpleado(primerHorario.getEmpleado().getId());
                    dto.setNombreEmpleado(primerHorario.getEmpleado()
                                    .getUsuario()
                                    .getNombre()
                    );
                    dto.setIdUsuario(primerHorario.getEmpleado().getUsuario().getId());
                    dto.setEspecialidad(primerHorario.getEmpleado().getEspecialidad());
                    dto.setUrlImagen(primerHorario.getEmpleado().getUrl());
                    Map<String, List<String>> fechas = listaHorarios
                                    .stream()
                                    .collect(Collectors.groupingBy(h -> h.getFechaHora()
                                                    .toLocalDate()
                                                    .toString(),
                                            Collectors.mapping(h -> h.getFechaHora()
                                                            .toLocalTime()
                                                            .toString(), Collectors.toList()
                                            )
                                    ));

                    dto.setFechas(fechas);

                    return dto;

                })
                .toList();
    }


}
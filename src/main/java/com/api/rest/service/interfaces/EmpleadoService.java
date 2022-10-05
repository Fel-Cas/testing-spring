package com.api.rest.service.interfaces;

import com.api.rest.dtos.EmpleadoDTO;
import com.api.rest.models.Empleado;

import java.util.List;
import java.util.Optional;

public interface EmpleadoService {
    EmpleadoDTO create(EmpleadoDTO empleadoDTO);
    List<EmpleadoDTO> getAll();
    EmpleadoDTO getById(Long id);
    EmpleadoDTO update(Long id, EmpleadoDTO empleadoDTO);
    void delete(Long id);

}

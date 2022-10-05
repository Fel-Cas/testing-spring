package com.api.rest.mappers;

import com.api.rest.dtos.EmpleadoDTO;
import com.api.rest.models.Empleado;
import org.springframework.stereotype.Component;

@Component
public class MapperEmpleado {

    public Empleado mapperEmpleado(EmpleadoDTO empleadoDTO){
        return  Empleado.builder()
                .id(empleadoDTO.getId())
                .nombre(empleadoDTO.getNombre())
                .apellido(empleadoDTO.getApellido())
                .email(empleadoDTO.getEmail())
                .build();
    }

    public EmpleadoDTO mapperEmpleadoDTO(Empleado empleado){
        return  EmpleadoDTO.builder()
                .id(empleado.getId())
                .nombre(empleado.getNombre())
                .apellido(empleado.getApellido())
                .email(empleado.getEmail())
                .build();
    }
}

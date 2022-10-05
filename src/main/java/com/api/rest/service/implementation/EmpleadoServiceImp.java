package com.api.rest.service.implementation;

import com.api.rest.dtos.EmpleadoDTO;
import com.api.rest.exceptions.BadRequest;
import com.api.rest.exceptions.NotFoundException;
import com.api.rest.mappers.MapperEmpleado;
import com.api.rest.models.Empleado;
import com.api.rest.repositories.EmpleadoRepository;
import com.api.rest.service.interfaces.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmpleadoServiceImp implements EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private MapperEmpleado mapperEmpleado;


    @Override
    public EmpleadoDTO create(EmpleadoDTO empleadoDTO) {
        Optional<Empleado> empleadoFound=this.empleadoRepository.findByEmail(empleadoDTO.getEmail());
        if(empleadoFound.isPresent()) throw new BadRequest("Already exist one empleado whit this email: %s".formatted(empleadoDTO.getEmail()));
        Empleado empleadoSaved=this.empleadoRepository.save(mapperEmpleado.mapperEmpleado(empleadoDTO));
        return mapperEmpleado.mapperEmpleadoDTO(empleadoSaved);
    }

    @Override
    public List<EmpleadoDTO> getAll() {
        List<Empleado> allEmpleados=this.empleadoRepository.findAll();
        return allEmpleados.stream().map(empleado -> mapperEmpleado.mapperEmpleadoDTO(empleado)).collect(Collectors.toList());
    }

    @Override
    public EmpleadoDTO getById(Long id) {
        Optional<Empleado> empleadoFound=this.empleadoRepository.findById(id);
        if(empleadoFound.isEmpty()) throw  new NotFoundException("Empleado doesn't exitst");
        return mapperEmpleado.mapperEmpleadoDTO(empleadoFound.get());
    }

    @Override
    public EmpleadoDTO update(Long id, EmpleadoDTO empleadoDTO) {
        EmpleadoDTO empleadoFound=getById(id);
        EmpleadoDTO empleadoToSave=updateInformation(empleadoFound, empleadoDTO);
        Empleado empleadoUpdated=this.empleadoRepository.save(mapperEmpleado.mapperEmpleado(empleadoToSave));
        return mapperEmpleado.mapperEmpleadoDTO(empleadoUpdated);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        this.empleadoRepository.deleteById(id);
    }

    private EmpleadoDTO updateInformation(EmpleadoDTO empleadoFound, EmpleadoDTO empleadoWithNewInformation){
        empleadoFound.setNombre(empleadoWithNewInformation.getNombre());
        empleadoFound.setApellido(empleadoWithNewInformation.getApellido());
        empleadoFound.setEmail(empleadoWithNewInformation.getEmail());
        return empleadoFound;
    }
}

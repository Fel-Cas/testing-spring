package com.api.rest.controllers;

import com.api.rest.dtos.EmpleadoDTO;
import com.api.rest.service.interfaces.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping()
    public ResponseEntity<List<EmpleadoDTO>> getAll(){
        List<EmpleadoDTO> allEmpleados=this.empleadoService.getAll();
        return  ResponseEntity.ok(allEmpleados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> getOne(@PathVariable("id") Long id){
        EmpleadoDTO empleadoDTO=this.empleadoService.getById(id);
        return ResponseEntity.ok(empleadoDTO);
    }

    @PostMapping
    public  ResponseEntity<EmpleadoDTO> create(@RequestBody EmpleadoDTO empleadoDTO){
        return new ResponseEntity(this.empleadoService.create(empleadoDTO),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<EmpleadoDTO> update(@PathVariable("id") Long id, EmpleadoDTO empleadoDTO){
        EmpleadoDTO empleadoUpdated=this.empleadoService.update(id, empleadoDTO);
        return  ResponseEntity.ok(empleadoUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        this.empleadoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

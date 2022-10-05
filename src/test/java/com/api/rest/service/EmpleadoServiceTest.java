package com.api.rest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import com.api.rest.dtos.EmpleadoDTO;
import com.api.rest.exceptions.BadRequest;
import com.api.rest.exceptions.NotFoundException;
import com.api.rest.mappers.MapperEmpleado;
import com.api.rest.models.Empleado;
import com.api.rest.repositories.EmpleadoRepository;
import com.api.rest.service.implementation.EmpleadoServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceTest {

    @InjectMocks
    private EmpleadoServiceImp empleadoService;

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private MapperEmpleado mapperEmpleado;

    private EmpleadoDTO empleadoDTO;

    private Empleado empleado;

    @BeforeEach()
    void init(){
        empleadoDTO=EmpleadoDTO.builder()
                .id(1L)
                .nombre("Andrés")
                .apellido("Castro")
                .email("anc@email.com")
                .build();

        empleado=Empleado
                .builder()
                .id(1L)
                .nombre("Andrés")
                .apellido("Castro")
                .email("anc@email.com")
                .build();
    }

    @Test
    @DisplayName("Test Service guardar un empleado")
    void create(){
        // given
        given(empleadoRepository.findByEmail(empleadoDTO.getEmail())).willReturn(Optional.empty());
        given(empleadoRepository.save(empleado)).willReturn(empleado);

        given(mapperEmpleado.mapperEmpleadoDTO(any())).willReturn(empleadoDTO);
        given(mapperEmpleado.mapperEmpleado(any())).willReturn(empleado);
        //when
        EmpleadoDTO empleadoDTOSaved=empleadoService.create(empleadoDTO);
        //then
        assertNotNull(empleadoDTOSaved);
        assertEquals(1L, empleadoDTOSaved.getId());
    }

    @Test
    @DisplayName("Test Service guardar un empleado con un email que a existe")
    void failCreate(){
        //given
        given(empleadoRepository.findByEmail(empleadoDTO.getEmail())).willReturn(Optional.of(empleado));
        //when
        BadRequest exception=assertThrows(BadRequest.class,()->{
           empleadoService.create(empleadoDTO);
        });
        assertEquals("Already exist one empleado whit this email: %s".formatted(empleadoDTO.getEmail()),exception.getMessage());
        verify(empleadoRepository,never()).save(any(Empleado.class));
    }
    @Test
    @DisplayName("Test Service buscar todos los empleados")
    void getAll(){
        //given
        given(empleadoRepository.findAll()).willReturn(List.of(empleado));
        given(mapperEmpleado.mapperEmpleadoDTO(any())).willReturn(empleadoDTO);
        // when
        List<EmpleadoDTO> empleadosFound=empleadoService.getAll();
        //then
        assertThat(empleadosFound.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Test Service no hay empleados registrados")
    void emptyList(){
        //given
        given(empleadoRepository.findAll()).willReturn(Collections.emptyList());
        //when
        List<EmpleadoDTO> allEmpleados=empleadoService.getAll();
        //then
        assertThat(allEmpleados).isEmpty();
        assertThat(allEmpleados.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Test Service buscar un empleado por su id")
    void getById(){
        //given
        given(empleadoRepository.findById(1L)).willReturn(Optional.of(empleado));
        given(mapperEmpleado.mapperEmpleadoDTO(any())).willReturn(empleadoDTO);
        //when
        EmpleadoDTO empleadoFound=empleadoService.getById(1L);
        //then
        assertNotNull(empleadoFound);
        assertEquals(1L, empleadoFound.getId());
    }

    @Test
    @DisplayName("Test Service buscar un empleado que no existe")
    void failGetById(){
        //given
        given(empleadoRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        NotFoundException exception=assertThrows(NotFoundException.class,()->empleadoService.getById(anyLong()));
        //then
        assertEquals("Empleado doesn't exitst", exception.getMessage());
    }

    @Test
    @DisplayName("Test Service Actualizar un Empleado")
    void update(){
        //given
        EmpleadoDTO empleadoDTOToUpdate=  EmpleadoDTO.builder()
                .id(1L)
                .nombre("Andrés Felipe")
                .apellido("Castro Monsalve")
                .email("anc@email.com")
                .build();

        Empleado empleadoUpdated=  Empleado.builder()
                .id(1L)
                .nombre("Andrés Felipe")
                .apellido("Castro Monsalve")
                .email("anc@email.com")
                .build();

        given(empleadoRepository.findById(1L)).willReturn(Optional.of(empleado));
        given(empleadoRepository.save(empleado)).willReturn(empleadoUpdated);

        given(mapperEmpleado.mapperEmpleadoDTO(any())).willReturn(empleadoDTOToUpdate);
        given(mapperEmpleado.mapperEmpleado(any())).willReturn(empleado);
        //when
        EmpleadoDTO empleadoDTOUpdated= empleadoService.update(1L,empleadoDTOToUpdate);
        //then
        assertThat(empleadoDTOUpdated).isNotNull();
        assertEquals("Andrés Felipe", empleadoDTOUpdated.getNombre());
        assertEquals("Castro Monsalve",empleadoDTOUpdated.getApellido());
    }

    @Test
    @DisplayName("Test Service eliminar un empleado")
    void delete(){
        //given
        given(empleadoRepository.findById(1L)).willReturn(Optional.ofNullable(empleado));
        willDoNothing().given(empleadoRepository).deleteById(1L);
        //when
         empleadoService.delete(1L);
        //then
        verify(empleadoRepository,times(1)).deleteById(1L);
    }

}
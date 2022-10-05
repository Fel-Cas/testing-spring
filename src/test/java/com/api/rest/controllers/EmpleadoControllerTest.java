package com.api.rest.controllers;


import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import com.api.rest.dtos.EmpleadoDTO;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.api.rest.service.interfaces.EmpleadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest
class EmpleadoControllerTest {
    @MockBean
    private EmpleadoService empleadoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvcm;

    private EmpleadoDTO empleadoDTO;

    private String url="/api/empleados";

    @BeforeEach()
    void init(){
        empleadoDTO= EmpleadoDTO.builder()
                .id(1L)
                .nombre("Andrés")
                .apellido("Castro")
                .email("anc@email.com")
                .build();}

    @Test
    void getAll() throws Exception {
        // given
        List<EmpleadoDTO> allEmpleados=new ArrayList<>();
        allEmpleados.add(empleadoDTO);
        allEmpleados.add(EmpleadoDTO.builder().id(2L).nombre("Juan Felipe").apellido("Castro").email("juan@email.com").build());
        allEmpleados.add(EmpleadoDTO.builder().id(3L).nombre("Ricardo").apellido("Marquez").email("rm@email.com").build());
        allEmpleados.add(EmpleadoDTO.builder().id(4L).nombre("Pepe").apellido("Rodriguez").email("pepe@email.com").build());
        given(empleadoService.getAll()).willReturn(allEmpleados);
        // when
        ResultActions response=mockMvcm.perform(get(url));
        // then

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(allEmpleados.size())));
    }

    @Test
    void getOne() throws Exception {
        // given
        given(empleadoService.getById(anyLong())).willReturn(empleadoDTO);
        // when
        ResultActions response=mockMvcm.perform(get(url+"/{id}",1L));
        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nombre",is(empleadoDTO.getNombre())))
                .andExpect(jsonPath("$.email",is(empleadoDTO.getEmail())))
                .andExpect(jsonPath("$.apellido",is(empleadoDTO.getApellido())));


    }

    @Test
    void create() throws Exception {
        //given
        given(empleadoService.create(any()))
                .willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));
        //when
        ResultActions response=mockMvcm.perform((post(url))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empleadoDTO)));
        //then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre",is(empleadoDTO.getNombre())));
    }

    @Test
    void update() throws Exception {
        //given
        EmpleadoDTO empleadoUpdated=EmpleadoDTO.builder()
                .id(1L)
                .nombre("Andrés Felipe")
                .apellido("Castro Monsalve")
                .email("anc@email.com")
                .build();

        given(empleadoService.update(1L,empleadoDTO))
                .willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));
        // When
        ResultActions response;
        response = mockMvcm.perform(put("/api/empleados/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empleadoUpdated)));

        // then
        response.andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void deleteEmpleado() throws Exception {
        // given
        willDoNothing().given(empleadoService).delete(1L);
        // when
        ResultActions response= mockMvcm.perform(delete(url+"/{id}",1L));
        // then
        response.andExpect(status().isNoContent())
                .andDo(print());
    }
}
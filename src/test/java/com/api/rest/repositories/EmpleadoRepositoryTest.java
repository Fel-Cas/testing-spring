package com.api.rest.repositories;

import com.api.rest.models.Empleado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmpleadoRepositoryTest {

    @Autowired
    private  EmpleadoRepository empleadoRepository;

    private Empleado empleado;

    @BeforeEach
    void init(){
        empleado= Empleado.builder()
                .nombre("Andrés")
                .apellido("Castro")
                .email("anc@email.com")
                .build();
    }

    @Test
    @DisplayName("Test Respository save one user in database")
    void create(){
        // given - condición previa

        // when - comportamiento
        Empleado empleadoSaved=empleadoRepository.save(empleado);

        // then - verificar
        assertThat(empleadoSaved).isNotNull();
        assertThat(empleadoSaved.getId()).isGreaterThan(0);

    }

    @Test
    @DisplayName("Test Repository get all empleados")
    void getAll(){
        // given
        empleadoRepository.save(empleado);
        // when
        List<Empleado> allEmpleados=this.empleadoRepository.findAll();
        // then
        assertThat(allEmpleados).isNotNull();
        assertThat(allEmpleados.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Test Repository get one empleado")
    void  getOne(){
        // given
        empleadoRepository.save(empleado);
        // when
        Optional<Empleado> empleadoFound=this.empleadoRepository.findById(1L);
        // then
        assertThat(empleadoFound.get()).isNotNull();
        assertThat(empleadoFound.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("Test Respository get one empleado who doesn't exist")
    void getOneNoExist(){
        // given
        empleadoRepository.save(empleado);
        // when
        Optional<Empleado> empleadoFound=this.empleadoRepository.findById(80L);
        // then
        assertThat(empleadoFound.isEmpty()).isTrue();
        assertThat(empleadoFound.isEmpty()).isTrue();

    }

    @Test
    @DisplayName("Test Repository delete one empleado")
    void delete(){
        // given
        empleadoRepository.save(empleado);
        // when
        empleadoRepository.deleteById(1L);
        Optional<Empleado> empleadoFound=this.empleadoRepository.findById(1L);
        // then
        assertThat(empleadoFound.isEmpty()).isTrue();
        assertThat(empleadoFound.isEmpty()).isTrue();
    }
}
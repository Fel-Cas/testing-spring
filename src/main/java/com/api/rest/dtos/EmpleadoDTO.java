package com.api.rest.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
}

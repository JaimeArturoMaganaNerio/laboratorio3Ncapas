package com.example.tabletasheikah.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateHabitanteRequest {

    @NotNull(message = "El nombre del habitante no puede estar vacío.")
    private String nombre;

    @NotNull(message = "Debe especificar la raza del habitante.")
    private String raza;

    @NotNull(message = "La aldea de origen es obligatoria.")
    private String aldea;

    @NotNull(message = "La edad es requerida.")
    private Integer edad;
}
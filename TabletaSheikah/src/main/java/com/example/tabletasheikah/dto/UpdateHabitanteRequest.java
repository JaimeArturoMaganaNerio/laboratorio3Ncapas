package com.example.tabletasheikah.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateHabitanteRequest {
    private String nombre;
    private String raza;
    private String aldea;
    private Integer edad;
}
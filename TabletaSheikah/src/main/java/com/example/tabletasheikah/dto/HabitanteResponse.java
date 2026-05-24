package com.example.tabletasheikah.dto;

import lombok.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HabitanteResponse {
    private UUID id;
    private String nombre;
    private String raza;
    private String aldea;
    private Integer edad;
}
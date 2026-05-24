package com.example.tabletasheikah.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "habitantes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Habitante {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String raza;      // Hylian, Goron, Zora, Gerudo, Rito, etc.

    @Column(nullable = false)
    private String aldea;     // Kakariko, Zora's Domain, Goron City, etc.

    @Column(nullable = false)
    private Integer edad;
}
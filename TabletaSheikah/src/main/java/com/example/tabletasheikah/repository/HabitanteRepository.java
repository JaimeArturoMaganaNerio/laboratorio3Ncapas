package com.example.tabletasheikah.repository;


import com.example.tabletasheikah.model.Habitante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HabitanteRepository extends JpaRepository<Habitante, UUID> {
}
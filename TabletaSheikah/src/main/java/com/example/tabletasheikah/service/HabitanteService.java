package com.example.tabletasheikah.service;


import com.example.tabletasheikah.dto.CreateHabitanteRequest;
import com.example.tabletasheikah.dto.HabitanteResponse;
import com.example.tabletasheikah.dto.UpdateHabitanteRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface HabitanteService {
    HabitanteResponse createHabitante(CreateHabitanteRequest request);
    Page<HabitanteResponse> getAllHabitantes(Pageable pageable);
    HabitanteResponse getHabitanteById(UUID id);
    HabitanteResponse updateHabitante(UUID id, UpdateHabitanteRequest request);
    HabitanteResponse deleteHabitante(UUID id);
}
package com.example.tabletasheikah.service;



import com.example.tabletasheikah.dto.CreateHabitanteRequest;
import com.example.tabletasheikah.dto.HabitanteResponse;
import com.example.tabletasheikah.dto.UpdateHabitanteRequest;
import com.example.tabletasheikah.exception.ResourceNotFoundException;
import com.example.tabletasheikah.mapper.HabitanteMapper;
import com.example.tabletasheikah.model.Habitante;
import com.example.tabletasheikah.repository.HabitanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HabitanteServiceImpl implements HabitanteService {

    private final HabitanteRepository habitanteRepository;
    private final HabitanteMapper habitanteMapper;

    @Override
    @Transactional
    public HabitanteResponse createHabitante(CreateHabitanteRequest request) {
        Habitante habitante = habitanteMapper.toEntity(request);
        Habitante saved = habitanteRepository.save(habitante);
        return habitanteMapper.toResponse(saved);
    }

    @Override
    public Page<HabitanteResponse> getAllHabitantes(Pageable pageable) {
        Page<Habitante> page = habitanteRepository.findAll(pageable);
        if (page.isEmpty()) {
            throw new ResourceNotFoundException("No hay habitantes registrados en Hyrule.");
        }
        // Usamos el método que resuelve el TODO
        return habitanteMapper.toResponsePage(page);
    }

    @Override
    public HabitanteResponse getHabitanteById(UUID id) {
        Habitante habitante = habitanteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habitante con ID " + id + " no encontrado en Hyrule."));
        return habitanteMapper.toResponse(habitante);
    }

    @Override
    @Transactional
    public HabitanteResponse updateHabitante(UUID id, UpdateHabitanteRequest request) {
        // Verificamos que exista (si no, lanza excepción)
        getHabitanteById(id);
        // Convertimos request + id a entidad
        Habitante habitanteToUpdate = habitanteMapper.toEntity(request, id);
        Habitante updated = habitanteRepository.save(habitanteToUpdate);
        return habitanteMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public HabitanteResponse deleteHabitante(UUID id) {
        HabitanteResponse existing = getHabitanteById(id);
        habitanteRepository.deleteById(id);
        return existing;
    }
}
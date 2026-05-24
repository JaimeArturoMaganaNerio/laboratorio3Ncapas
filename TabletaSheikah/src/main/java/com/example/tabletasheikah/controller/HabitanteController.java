package com.example.tabletasheikah.controller;



import com.example.tabletasheikah.dto.CreateHabitanteRequest;
import com.example.tabletasheikah.dto.HabitanteResponse;
import com.example.tabletasheikah.dto.UpdateHabitanteRequest;
import com.example.tabletasheikah.service.HabitanteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/habitantes")
@RequiredArgsConstructor
public class HabitanteController {

    private final HabitanteService habitanteService;

    // Crear habitante
    @PostMapping
    public ResponseEntity<HabitanteResponse> create(@Valid @RequestBody CreateHabitanteRequest request) {
        HabitanteResponse response = habitanteService.createHabitante(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Listar con paginación y ordenamiento dinámico
    @GetMapping
    public ResponseEntity<Page<HabitanteResponse>> getAll(
            @PageableDefault(size = 5, sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<HabitanteResponse> page = habitanteService.getAllHabitantes(pageable);
        return ResponseEntity.ok(page);
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<HabitanteResponse> getById(@PathVariable UUID id) {
        HabitanteResponse response = habitanteService.getHabitanteById(id);
        return ResponseEntity.ok(response);
    }

    // Actualizar completamente o parcialmente
    @PutMapping("/{id}")
    public ResponseEntity<HabitanteResponse> update(@PathVariable UUID id,
                                                    @RequestBody UpdateHabitanteRequest request) {
        HabitanteResponse response = habitanteService.updateHabitante(id, request);
        return ResponseEntity.ok(response);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<HabitanteResponse> delete(@PathVariable UUID id) {
        HabitanteResponse response = habitanteService.deleteHabitante(id);
        return ResponseEntity.ok(response);
    }
}
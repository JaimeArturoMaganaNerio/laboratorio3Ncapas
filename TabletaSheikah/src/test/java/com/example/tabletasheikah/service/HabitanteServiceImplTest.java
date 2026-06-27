package com.example.tabletasheikah.service;

import com.example.tabletasheikah.dto.CreateHabitanteRequest;
import com.example.tabletasheikah.dto.HabitanteResponse;
import com.example.tabletasheikah.dto.UpdateHabitanteRequest;
import com.example.tabletasheikah.exception.ResourceNotFoundException;
import com.example.tabletasheikah.mapper.HabitanteMapper;
import com.example.tabletasheikah.model.Habitante;
import com.example.tabletasheikah.repository.HabitanteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HabitanteServiceImplTest {

    @Mock
    private HabitanteRepository habitanteRepository;

    @Mock
    private HabitanteMapper habitanteMapper;

    @InjectMocks
    private HabitanteServiceImpl habitanteService;

    private UUID id;
    private Habitante habitante;
    private HabitanteResponse response;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        habitante = Habitante.builder()
                .id(id).nombre("Link").raza("Hylian").aldea("Kakariko").edad(17)
                .build();
        response = HabitanteResponse.builder()
                .id(id).nombre("Link").raza("Hylian").aldea("Kakariko").edad(17)
                .build();
    }

    // ---------- createHabitante ----------
    @Test
    @DisplayName("createHabitante: mapea, guarda y devuelve el DTO")
    void createHabitante_returnsSavedDto() {
        CreateHabitanteRequest request = CreateHabitanteRequest.builder()
                .nombre("Link").raza("Hylian").aldea("Kakariko").edad(17)
                .build();

        when(habitanteMapper.toEntity(request)).thenReturn(habitante);
        when(habitanteRepository.save(habitante)).thenReturn(habitante);
        when(habitanteMapper.toResponse(habitante)).thenReturn(response);

        HabitanteResponse result = habitanteService.createHabitante(request);

        assertNotNull(result);
        assertEquals("Link", result.getNombre());
        verify(habitanteRepository).save(habitante);
    }

    // ---------- getHabitanteById ----------
    @Test
    @DisplayName("getHabitanteById: cuando existe, devuelve el DTO")
    void getHabitanteById_found_returnsDto() {
        when(habitanteRepository.findById(id)).thenReturn(Optional.of(habitante));
        when(habitanteMapper.toResponse(habitante)).thenReturn(response);

        HabitanteResponse result = habitanteService.getHabitanteById(id);

        assertEquals(id, result.getId());
        verify(habitanteRepository).findById(id);
    }

    @Test
    @DisplayName("getHabitanteById: cuando no existe, lanza ResourceNotFoundException")
    void getHabitanteById_notFound_throws() {
        when(habitanteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> habitanteService.getHabitanteById(id));

        verify(habitanteMapper, never()).toResponse(any(Habitante.class));
    }

    // ---------- getAllHabitantes ----------
    @Test
    @DisplayName("getAllHabitantes: con datos, devuelve la pagina mapeada")
    void getAllHabitantes_withData_returnsPage() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Habitante> entityPage = new PageImpl<>(List.of(habitante));
        Page<HabitanteResponse> dtoPage = new PageImpl<>(List.of(response));

        when(habitanteRepository.findAll(pageable)).thenReturn(entityPage);
        when(habitanteMapper.toResponsePage(entityPage)).thenReturn(dtoPage);

        Page<HabitanteResponse> result = habitanteService.getAllHabitantes(pageable);

        assertEquals(1, result.getTotalElements());
        verify(habitanteRepository).findAll(pageable);
    }

    @Test
    @DisplayName("getAllHabitantes: sin datos, lanza ResourceNotFoundException")
    void getAllHabitantes_empty_throws() {
        Pageable pageable = PageRequest.of(0, 5);
        when(habitanteRepository.findAll(pageable)).thenReturn(Page.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> habitanteService.getAllHabitantes(pageable));

        verify(habitanteMapper, never()).toResponsePage(any(Page.class));
    }

    // ---------- updateHabitante ----------
    @Test
    @DisplayName("updateHabitante: cuando existe, guarda y devuelve el DTO actualizado")
    void updateHabitante_found_returnsDto() {
        UpdateHabitanteRequest request = UpdateHabitanteRequest.builder()
                .nombre("Link Heroe").raza("Hylian").aldea("Hateno").edad(18)
                .build();
        Habitante updatedEntity = Habitante.builder()
                .id(id).nombre("Link Heroe").raza("Hylian").aldea("Hateno").edad(18)
                .build();
        HabitanteResponse updatedResponse = HabitanteResponse.builder()
                .id(id).nombre("Link Heroe").raza("Hylian").aldea("Hateno").edad(18)
                .build();

        when(habitanteRepository.findById(id)).thenReturn(Optional.of(habitante)); // validacion interna
        when(habitanteMapper.toResponse(habitante)).thenReturn(response); // llamada interna de getHabitanteById
        when(habitanteMapper.toEntity(request, id)).thenReturn(updatedEntity);
        when(habitanteRepository.save(updatedEntity)).thenReturn(updatedEntity);
        when(habitanteMapper.toResponse(updatedEntity)).thenReturn(updatedResponse);

        HabitanteResponse result = habitanteService.updateHabitante(id, request);

        assertEquals("Link Heroe", result.getNombre());
        verify(habitanteRepository).save(updatedEntity);
    }

    @Test
    @DisplayName("updateHabitante: cuando no existe, lanza excepcion y no guarda")
    void updateHabitante_notFound_throws() {
        UpdateHabitanteRequest request = UpdateHabitanteRequest.builder()
                .nombre("X").raza("Y").aldea("Z").edad(1)
                .build();
        when(habitanteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> habitanteService.updateHabitante(id, request));

        verify(habitanteRepository, never()).save(any(Habitante.class));
    }

    // ---------- deleteHabitante ----------
    @Test
    @DisplayName("deleteHabitante: cuando existe, elimina y devuelve el DTO")
    void deleteHabitante_found_deletesAndReturns() {
        when(habitanteRepository.findById(id)).thenReturn(Optional.of(habitante));
        when(habitanteMapper.toResponse(habitante)).thenReturn(response);

        HabitanteResponse result = habitanteService.deleteHabitante(id);

        assertEquals(id, result.getId());
        verify(habitanteRepository).deleteById(id);
    }

    @Test
    @DisplayName("deleteHabitante: cuando no existe, lanza excepcion y no elimina")
    void deleteHabitante_notFound_throws() {
        when(habitanteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> habitanteService.deleteHabitante(id));

        verify(habitanteRepository, never()).deleteById(any(UUID.class));
    }
}


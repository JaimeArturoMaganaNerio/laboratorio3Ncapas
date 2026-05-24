package com.example.tabletasheikah.mapper;



import com.example.tabletasheikah.dto.CreateHabitanteRequest;
import com.example.tabletasheikah.dto.HabitanteResponse;
import com.example.tabletasheikah.dto.UpdateHabitanteRequest;
import com.example.tabletasheikah.model.Habitante;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class HabitanteMapper {


    public Habitante toEntity(CreateHabitanteRequest request) {
        return Habitante.builder()
                .nombre(request.getNombre())
                .raza(request.getRaza())
                .aldea(request.getAldea())
                .edad(request.getEdad())
                .build();
    }

    public Habitante toEntity(UpdateHabitanteRequest request, UUID id) {
        return Habitante.builder()
                .id(id)
                .nombre(request.getNombre())
                .raza(request.getRaza())
                .aldea(request.getAldea())
                .edad(request.getEdad())
                .build();
    }


    public HabitanteResponse toResponse(Habitante habitante) {
        return HabitanteResponse.builder()
                .id(habitante.getId())
                .nombre(habitante.getNombre())
                .raza(habitante.getRaza())
                .aldea(habitante.getAldea())
                .edad(habitante.getEdad())
                .build();
    }

    public Page<HabitanteResponse> toResponsePage(Page<Habitante> page) {
        return page.map(this::toResponse);
    }
}
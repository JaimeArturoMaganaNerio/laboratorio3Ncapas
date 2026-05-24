package com.example.tabletasheikah;

import com.example.tabletasheikah.model.Habitante;
import com.example.tabletasheikah.repository.HabitanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class HyruleApplication implements CommandLineRunner {

    private final HabitanteRepository habitanteRepository;

    public static void main(String[] args) {
        SpringApplication.run(HyruleApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (habitanteRepository.count() == 0) {
            habitanteRepository.saveAll(List.of(
                    Habitante.builder().nombre("Link").raza("Hylian").aldea("Kakariko").edad(17).build(),
                    Habitante.builder().nombre("Zelda").raza("Hylian").aldea("Castle Town").edad(17).build(),
                    Habitante.builder().nombre("Daruk").raza("Goron").aldea("Goron City").edad(120).build(),
                    Habitante.builder().nombre("Mipha").raza("Zora").aldea("Zora's Domain").edad(55).build(),
                    Habitante.builder().nombre("Urbosa").raza("Gerudo").aldea("Gerudo Town").edad(30).build(),
                    Habitante.builder().nombre("Revali").raza("Rito").aldea("Rito Village").edad(25).build()
            ));
            System.out.println(" Datos iniciales cargados.");
        }
    }
}
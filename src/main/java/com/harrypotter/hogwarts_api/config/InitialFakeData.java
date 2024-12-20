 package com.harrypotter.hogwarts_api.config;

import com.harrypotter.hogwarts_api.entities.House;
import com.harrypotter.hogwarts_api.entities.Spell;
import com.harrypotter.hogwarts_api.repositories.HouseRepository;
import com.harrypotter.hogwarts_api.repositories.SpellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class InitialFakeData {

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private SpellRepository spellRepository;

    @Bean
    public CommandLineRunner initialData() {
        return args -> {
            if (houseRepository.count() == 0) {
                houseRepository.saveAll(Arrays.asList(
                        new House("Gryffindor"),
                        new House("Slytherin"),
                        new House("Hufflepuff"),
                        new House("Ravenclaw")
                ));
            }

            if (spellRepository.count() == 0) {
                spellRepository.saveAll(Arrays.asList(
                        new Spell("Expelliarmus"),
                        new Spell("Lumos"),
                        new Spell("Stupefy"),
                        new Spell("Accio")
                ));
            }
        };
    }
}

package com.harrypotter.hogwarts_api.services;

import com.harrypotter.hogwarts_api.dtos.MagicianRequest;
import com.harrypotter.hogwarts_api.dtos.MagicianResponse;
import com.harrypotter.hogwarts_api.entities.House;
import com.harrypotter.hogwarts_api.entities.Magician;
import com.harrypotter.hogwarts_api.entities.Spell;
import com.harrypotter.hogwarts_api.mappers.MagicianMapper;
import com.harrypotter.hogwarts_api.repositories.MagicianRepository;
import com.harrypotter.hogwarts_api.repositories.HouseRepository;
import com.harrypotter.hogwarts_api.repositories.SpellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MagicianService {

    @Autowired
    private MagicianRepository magicianRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private SpellRepository spellRepository;

    @Autowired
    private MagicianMapper magicianMapper;

    public MagicianResponse createMagician(MagicianRequest request) {
        // Validar que el nombre de la casa y los hechizos no estén vacíos
        if (request.getHouseName() == null || request.getHouseName().isEmpty()) {
            throw new IllegalArgumentException("House name cannot be empty");
        }

        if (request.getSpells() == null || request.getSpells().isEmpty()) {
            throw new IllegalArgumentException("At least one spell is required");
        }

        // Convertir el Request a entidad
        Magician magician = magicianMapper.toEntity(request);

        // Asignar House manualmente
        House house = houseRepository.findByName(request.getHouseName())
                .orElseThrow(() -> new RuntimeException("House not found with name: " + request.getHouseName()));
        magician.setHouseEntity(house);

        // Asignar Spells manualmente
        List<Spell> spells = spellRepository.findBySpellNameIn(request.getSpells());
        if (spells.isEmpty()) {
            throw new RuntimeException("No spells found for the given names: " + String.join(", ", request.getSpells()));
        }
        magician.setSpells(spells);

        // Guardar el mago
        magician = magicianRepository.save(magician);

        // Convertir la entidad a Response
        return magicianMapper.toResponse(magician);
    }

    public List<MagicianResponse> getAllMagicians() {
        // Recuperar todos los magos y convertirlos a Response
        return magicianRepository.findAll().stream()
                .map(magicianMapper::toResponse)
                .collect(Collectors.toList());
    }
    public MagicianResponse getMagicianById(Long id) {
        Magician magician = magicianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Magician not found"));
        return magicianMapper.toResponse(magician);
    }

    // Actualizar un mago
    public MagicianResponse updateMagician(Long id, MagicianRequest request) {
        // Buscar el mago existente
        Magician existingMagician = magicianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Magician not found"));

        // Actualizar los campos básicos
        existingMagician.setName(request.getName());

        // Actualizar la casa
        House house = houseRepository.findByName(request.getHouseName())
                .orElseThrow(() -> new RuntimeException("House not found"));
        existingMagician.setHouseEntity(house);

        // Actualizar los hechizos
        List<Spell> spells = spellRepository.findBySpellNameIn(request.getSpells());
        existingMagician.setSpells(spells);

        // Guardar los cambios
        Magician updatedMagician = magicianRepository.save(existingMagician);

        return magicianMapper.toResponse(updatedMagician);
    }

    // Eliminar un mago
    public void deleteMagician(Long id) {
        Magician magician = magicianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Magician not found"));
        magicianRepository.delete(magician);
    }
}

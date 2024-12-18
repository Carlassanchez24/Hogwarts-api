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
        if (request.getHouseName() == null || request.getHouseName().isEmpty()) {
            throw new IllegalArgumentException("House name cannot be empty");
        }

        if (request.getSpells() == null || request.getSpells().isEmpty()) {
            throw new IllegalArgumentException("At least one spell is required");
        }

        Magician magician = magicianMapper.toEntity(request);

        House house = houseRepository.findByName(request.getHouseName())
                .orElseThrow(() -> new RuntimeException("House not found with name: " + request.getHouseName()));
        magician.setHouseEntity(house);

        List<Spell> spells = spellRepository.findBySpellNameIn(request.getSpells());
        if (spells.isEmpty()) {
            throw new RuntimeException("No spells found for the given names: " + String.join(", ", request.getSpells()));
        }
        magician.setSpells(spells);

        magician = magicianRepository.save(magician);

        return magicianMapper.toResponse(magician);
    }

    public List<MagicianResponse> getAllMagicians() {
        return magicianRepository.findAll().stream()
                .map(magicianMapper::toResponse)
                .collect(Collectors.toList());
    }
    public MagicianResponse getMagicianById(Long id) {
        Magician magician = magicianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Magician not found"));
        return magicianMapper.toResponse(magician);
    }

    public MagicianResponse updateMagician(Long id, MagicianRequest request) {
        Magician existingMagician = magicianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Magician not found"));

        existingMagician.setName(request.getName());

        House house = houseRepository.findByName(request.getHouseName())
                .orElseThrow(() -> new RuntimeException("House not found"));
        existingMagician.setHouseEntity(house);

        List<Spell> spells = spellRepository.findBySpellNameIn(request.getSpells());
        existingMagician.setSpells(spells);

        Magician updatedMagician = magicianRepository.save(existingMagician);

        return magicianMapper.toResponse(updatedMagician);
    }

    public void deleteMagician(Long id) {
        Magician magician = magicianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Magician not found"));
        magicianRepository.delete(magician);
    }
}

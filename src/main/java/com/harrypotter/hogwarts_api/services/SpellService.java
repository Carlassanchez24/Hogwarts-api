package com.harrypotter.hogwarts_api.services;

import com.harrypotter.hogwarts_api.dtos.SpellRequest;
import com.harrypotter.hogwarts_api.dtos.SpellResponse;
import com.harrypotter.hogwarts_api.entities.Spell;
import com.harrypotter.hogwarts_api.mappers.SpellMapper;
import com.harrypotter.hogwarts_api.repositories.SpellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpellService {

    @Autowired
    private SpellRepository spellRepository;

    @Autowired
    private SpellMapper spellMapper;

    public SpellResponse createSpell(SpellRequest request) {
        Spell spell = spellMapper.toEntity(request);
        spell = spellRepository.save(spell);
        return spellMapper.toResponse(spell);
    }


    public List<SpellResponse> getAllSpells() {
        return spellRepository.findAll().stream()
                .map(spellMapper::toResponse)
                .collect(Collectors.toList());
    }

    public SpellResponse getSpellById(Long id) {
        Spell spell = spellRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Spell not found"));
        return spellMapper.toResponse(spell);
    }

    public SpellResponse updateSpell(Long id, SpellRequest request) {
        Spell existingSpell = spellRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Spell not found"));
        existingSpell.setSpellName(request.getSpellName());
        existingSpell = spellRepository.save(existingSpell);
        return spellMapper.toResponse(existingSpell);
    }

    public void deleteSpell(Long id) {
        Spell spell = spellRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Spell not found"));
        spellRepository.delete(spell);
    }
}

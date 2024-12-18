package com.harrypotter.hogwarts_api.controllers;

import com.harrypotter.hogwarts_api.dtos.SpellRequest;
import com.harrypotter.hogwarts_api.dtos.SpellResponse;
import com.harrypotter.hogwarts_api.services.SpellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/spells")
public class SpellController {

    @Autowired
    private SpellService spellService;

    // Crear un hechizo
    @PostMapping
    public ResponseEntity<SpellResponse> createSpell(@RequestBody @Valid SpellRequest request) {
        SpellResponse response = spellService.createSpell(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Obtener todos los hechizos
    @GetMapping
    public List<SpellResponse> getAllSpells() {
        return spellService.getAllSpells();
    }

    // Obtener un hechizo por ID
    @GetMapping("/{id}")
    public SpellResponse getSpellById(@PathVariable Long id) {
        return spellService.getSpellById(id);
    }

    // Actualizar un hechizo
    @PutMapping("/{id}")
    public SpellResponse updateSpell(@PathVariable Long id, @RequestBody @Valid SpellRequest request) {
        return spellService.updateSpell(id, request);
    }

    // Eliminar un hechizo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpell(@PathVariable Long id) {
        spellService.deleteSpell(id);
        return ResponseEntity.noContent().build();
    }
}

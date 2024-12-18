package com.harrypotter.hogwarts_api.controllers;

import com.harrypotter.hogwarts_api.dtos.MagicianRequest;
import com.harrypotter.hogwarts_api.dtos.MagicianResponse;
import com.harrypotter.hogwarts_api.services.MagicianService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/magicians")
public class MagicianController {

    @Autowired
    private MagicianService magicianService;

    @PostMapping
    public ResponseEntity<MagicianResponse> createMagician(@RequestBody @Valid MagicianRequest request) {
        MagicianResponse response = magicianService.createMagician(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public List<MagicianResponse> getAllMagicians() {
        return magicianService.getAllMagicians();
    }

    @GetMapping("/{id}")
    public MagicianResponse getMagicianById(@PathVariable Long id) {
        return magicianService.getMagicianById(id);
    }

    @PutMapping("/{id}")
    public MagicianResponse updateMagician(@PathVariable Long id, @RequestBody @Valid MagicianRequest request) {
        return magicianService.updateMagician(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMagician(@PathVariable Long id) {
        magicianService.deleteMagician(id);
        return ResponseEntity.noContent().build();
    }
}
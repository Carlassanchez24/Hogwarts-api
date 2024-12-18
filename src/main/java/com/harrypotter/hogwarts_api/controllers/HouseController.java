package com.harrypotter.hogwarts_api.controllers;

import com.harrypotter.hogwarts_api.dtos.HouseRequest;
import com.harrypotter.hogwarts_api.dtos.HouseResponse;
import com.harrypotter.hogwarts_api.services.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/houses")
public class HouseController {

    @Autowired
    private HouseService houseService;

    // Crear una casa
    @PostMapping
    public ResponseEntity<HouseResponse> createHouse(@RequestBody @Valid HouseRequest request) {
        HouseResponse response = houseService.createHouse(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Obtener todas las casas
    @GetMapping
    public List<HouseResponse> getAllHouses() {
        return houseService.getAllHouses();
    }

    // Obtener una casa por ID
    @GetMapping("/{id}")
    public HouseResponse getHouseById(@PathVariable Long id) {
        return houseService.getHouseById(id);
    }

    // Actualizar una casa
    @PutMapping("/{id}")
    public HouseResponse updateHouse(@PathVariable Long id, @RequestBody @Valid HouseRequest request) {
        return houseService.updateHouse(id, request);
    }

    // Eliminar una casa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHouse(@PathVariable Long id) {
        houseService.deleteHouse(id);
        return ResponseEntity.noContent().build();
    }
}

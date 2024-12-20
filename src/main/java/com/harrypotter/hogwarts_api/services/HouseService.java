package com.harrypotter.hogwarts_api.services;

import com.harrypotter.hogwarts_api.dtos.HouseRequest;
import com.harrypotter.hogwarts_api.dtos.HouseResponse;
import com.harrypotter.hogwarts_api.entities.House;
import com.harrypotter.hogwarts_api.mappers.HouseMapper;
import com.harrypotter.hogwarts_api.repositories.HouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HouseService {

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HouseMapper houseMapper;

    public HouseResponse createHouse(HouseRequest request) {
        House house = houseMapper.toEntity(request);

        if (house.getMagicians() == null) {
            house.setMagicians(new ArrayList<>());
        }

        house = houseRepository.save(house);
        return houseMapper.toResponse(house);
    }


    public List<HouseResponse> getAllHouses() {
        return houseRepository.findAll().stream()
                .map(houseMapper::toResponse)
                .collect(Collectors.toList());
    }

    public HouseResponse getHouseById(Long id) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("House not found"));
        return houseMapper.toResponse(house);
    }

    public HouseResponse updateHouse(Long id, HouseRequest request) {
        House existingHouse = houseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("House not found"));
        existingHouse.setName(request.getName());
        existingHouse = houseRepository.save(existingHouse);
        return houseMapper.toResponse(existingHouse);
    }

    public void deleteHouse(Long id) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("House not found"));
        houseRepository.delete(house);
    }
}

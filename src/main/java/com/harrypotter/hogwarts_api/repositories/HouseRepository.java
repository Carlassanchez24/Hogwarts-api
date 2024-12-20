package com.harrypotter.hogwarts_api.repositories;

import com.harrypotter.hogwarts_api.entities.House;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface HouseRepository extends JpaRepository<House, Long> {
    Optional<House> findByName(String name);
}

package com.harrypotter.hogwarts_api.repostories;

import com.harrypotter.hogwarts_api.entities.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Long> {
}

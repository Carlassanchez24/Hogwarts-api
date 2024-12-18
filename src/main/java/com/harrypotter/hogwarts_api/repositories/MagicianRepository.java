package com.harrypotter.hogwarts_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.harrypotter.hogwarts_api.entities.Magician;


public interface MagicianRepository extends JpaRepository<Magician, Long> {
}

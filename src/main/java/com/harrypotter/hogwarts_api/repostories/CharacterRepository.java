package com.harrypotter.hogwarts_api.repostories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.harrypotter.hogwarts_api.entities.Character;


public interface CharacterRepository extends JpaRepository<Character, Long> {
}

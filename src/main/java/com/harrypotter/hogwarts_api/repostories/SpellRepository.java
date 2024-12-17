package com.harrypotter.hogwarts_api.repostories;

import com.harrypotter.hogwarts_api.entities.Spell;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpellRepository extends JpaRepository<Spell, Long> {
}

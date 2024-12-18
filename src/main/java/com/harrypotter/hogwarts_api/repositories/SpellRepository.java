package com.harrypotter.hogwarts_api.repositories;

import com.harrypotter.hogwarts_api.entities.Spell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpellRepository extends JpaRepository<Spell, Long> {
    List<Spell> findBySpellNameIn(List<String> names);
}

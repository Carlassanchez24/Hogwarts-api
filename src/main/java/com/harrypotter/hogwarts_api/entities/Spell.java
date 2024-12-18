package com.harrypotter.hogwarts_api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "spells")
public class Spell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String spellName;

    @ManyToMany(mappedBy = "spells")
    private List<Magician> magicians;

    public Spell(String spellName) {
        this.spellName = spellName;
    }
}

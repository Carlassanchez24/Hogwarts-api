package com.harrypotter.hogwarts_api.entities;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "magicians")
public class Magician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "house_id", nullable = true)
    private House houseEntity;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "magician_spell",
            joinColumns = @JoinColumn(name = "magician_id"),
            inverseJoinColumns = @JoinColumn(name = "spell_id")
    )
    private List<Spell> spells;


}

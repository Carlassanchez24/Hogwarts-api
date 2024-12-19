package com.harrypotter.hogwarts_api.entities;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "houses")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "houseEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Magician> magicians = new ArrayList<>();

    public House(String name) {
        this.name = name;
    }
}

package com.harrypotter.hogwarts_api.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class HouseResponse {
    private Long id;
    private String name;
    private List<String> magicianNames;

    public List<String> getMagicianNames() {
        return magicianNames != null ? magicianNames : Collections.emptyList();
    }
}

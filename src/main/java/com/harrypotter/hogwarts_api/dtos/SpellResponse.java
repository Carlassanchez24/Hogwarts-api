package com.harrypotter.hogwarts_api.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class SpellResponse {
    private Long id;
    private String spellName;
    private List<String> magicians;
}

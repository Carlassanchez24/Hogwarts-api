package com.harrypotter.hogwarts_api.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpellRequest {
    @NotNull(message = "Spell name cannot be null")
    @NotEmpty(message = "Spell name cannot be empty")
    @Size(min = 3, max = 50, message = "Spell name must be between 3 and 50 characters")
    private String spellName;


}

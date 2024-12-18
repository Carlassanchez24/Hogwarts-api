package com.harrypotter.hogwarts_api.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class MagicianRequest {
    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @NotNull(message = "House name cannot be null")
    @NotEmpty(message = "House name cannot be empty")
    @Size(max = 50, message = "House name must not exceed 50 characters")
    private String houseName;

    @Size(max = 10, message = "A magician can have at most 10 spells")
    private List<@NotEmpty(message = "Spell name cannot be empty") String> spells;
}

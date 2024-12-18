package com.harrypotter.hogwarts_api.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseRequest {
    @NotNull(message = "House name cannot be null")
    @NotEmpty(message = "House name cannot be empty")
    @Size(min = 3, max = 50, message = "House name must be between 3 and 50 characters")
    private String name;
}

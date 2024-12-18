package com.harrypotter.hogwarts_api.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
public class MagicianResponse {
    private Long id;
    private String name;
    private String houseName;
    private List<String> spells;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

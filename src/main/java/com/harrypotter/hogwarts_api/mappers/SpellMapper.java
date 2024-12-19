package com.harrypotter.hogwarts_api.mappers;

import com.harrypotter.hogwarts_api.dtos.SpellRequest;
import com.harrypotter.hogwarts_api.dtos.SpellResponse;
import com.harrypotter.hogwarts_api.entities.Magician;
import com.harrypotter.hogwarts_api.entities.Spell;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpellMapper {

    Spell toEntity(SpellRequest request);

    @Mapping(target = "magicians", source = "magicians", qualifiedByName = "mapMagiciansToNames")
    SpellResponse toResponse(Spell spell);

    @Named("mapMagiciansToNames")

    default List<String> mapMagiciansToNames(List<Magician> magicians) {
        if (magicians == null || magicians.isEmpty()) {
            return List.of();
        }
        return magicians.stream()
                .map(Magician::getName)
                .toList();
    }

}

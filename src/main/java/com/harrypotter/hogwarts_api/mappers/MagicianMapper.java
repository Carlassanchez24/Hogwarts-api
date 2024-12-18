package com.harrypotter.hogwarts_api.mappers;

import com.harrypotter.hogwarts_api.dtos.MagicianRequest;
import com.harrypotter.hogwarts_api.dtos.MagicianResponse;
import com.harrypotter.hogwarts_api.entities.Magician;
import com.harrypotter.hogwarts_api.entities.Spell;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MagicianMapper {

    // Mapear MagicianRequest a Magician
    @Mapping(target = "houseEntity.name", source = "houseName") // House se obtiene del nombre
    @Mapping(target = "spells", ignore = true) // Se mapea manualmente si es necesario
    Magician toEntity(MagicianRequest request);

    // Mapear Magician a MagicianResponse
    @Mapping(target = "houseName", source = "houseEntity.name") // Obtenemos el nombre de la casa
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(target = "spells", expression = "java(mapSpellsToNames(magician.getSpells()))") // Lista de nombres de spells
    MagicianResponse toResponse(Magician magician);

    default List<String> mapSpellsToNames(List<Spell> spells) {
        return spells.stream()
                .map(Spell::getSpellName)
                .toList();
    }
}
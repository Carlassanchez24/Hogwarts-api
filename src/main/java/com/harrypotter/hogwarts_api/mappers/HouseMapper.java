package com.harrypotter.hogwarts_api.mappers;

import com.harrypotter.hogwarts_api.dtos.HouseRequest;
import com.harrypotter.hogwarts_api.dtos.HouseResponse;
import com.harrypotter.hogwarts_api.entities.House;
import com.harrypotter.hogwarts_api.entities.Magician;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HouseMapper {

    House toEntity(HouseRequest request);

    @Mapping(target = "magicianNames", source = "magicians", qualifiedByName = "mapMagiciansToNames")
    HouseResponse toResponse(House house);

    @Named("mapMagiciansToNames")
    default List<String> mapMagiciansToNames(List<Magician> magicians) {
        if (magicians == null || magicians.isEmpty()) {
            return List.of(); // Devuelve una lista vacía si no hay magos
        }
        return magicians.stream()
                .map(Magician::getName)
                .toList();
    }

}

package com.harrypotter.hogwarts_api;

import com.harrypotter.hogwarts_api.dtos.HouseRequest;
import com.harrypotter.hogwarts_api.dtos.HouseResponse;
import com.harrypotter.hogwarts_api.entities.House;
import com.harrypotter.hogwarts_api.mappers.HouseMapper;
import com.harrypotter.hogwarts_api.repositories.HouseRepository;
import com.harrypotter.hogwarts_api.services.HouseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HouseServiceTest {

    @Mock
    private HouseRepository houseRepository;

    @Mock
    private HouseMapper houseMapper;

    @InjectMocks
    private HouseService houseService;

    @Test
    public void testCreateHouse() {
        HouseRequest houseRequest = new HouseRequest();
        houseRequest.setName("Gryffindor");
    
        House house = new House(1L, "Gryffindor", null);
        HouseResponse expectedResponse = new HouseResponse();
        expectedResponse.setId(1L);
        expectedResponse.setName("Gryffindor");
    
        when(houseMapper.toEntity(houseRequest)).thenReturn(house);
        when(houseRepository.save(house)).thenReturn(house);
        when(houseMapper.toResponse(house)).thenReturn(expectedResponse);
    
        HouseResponse response = houseService.createHouse(houseRequest);
    
        verify(houseRepository, times(1)).save(house);
        assertNotNull(response);
        assertEquals(expectedResponse.getName(), response.getName());
    }

    @Test
    public void testGetAllHouses() {
        House house1 = new House(1L, "Gryffindor", null);
        House house2 = new House(2L, "Hufflepuff", null);
        List<House> houses = List.of(house1, house2);
    
        HouseResponse houseResponse1 = new HouseResponse();
        houseResponse1.setId(1L);
        houseResponse1.setName("Gryffindor");
    
        HouseResponse houseResponse2 = new HouseResponse();
        houseResponse2.setId(2L);
        houseResponse2.setName("Hufflepuff");
    
        when(houseRepository.findAll()).thenReturn(houses);
        when(houseMapper.toResponse(house1)).thenReturn(houseResponse1);
        when(houseMapper.toResponse(house2)).thenReturn(houseResponse2);
    
        List<HouseResponse> response = houseService.getAllHouses();
    
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Gryffindor", response.get(0).getName());
        assertEquals("Hufflepuff", response.get(1).getName());
    }

    @Test
    public void testGetHouseById() {
        House house = new House(1L, "Gryffindor", null);
        HouseResponse expectedResponse = new HouseResponse();
        expectedResponse.setId(1L);
        expectedResponse.setName("Gryffindor");
    
        when(houseRepository.findById(1L)).thenReturn(Optional.of(house));
        when(houseMapper.toResponse(house)).thenReturn(expectedResponse);
    
        HouseResponse response = houseService.getHouseById(1L);
    
        assertNotNull(response);
        assertEquals("Gryffindor", response.getName());
    }

    @Test
    public void testUpdateHouse() {
        HouseRequest houseRequest = new HouseRequest();
        houseRequest.setName("Gryffindor");
    
        House existingHouse = new House(1L, "Old House", null);
        House updatedHouse = new House(1L, "Gryffindor", null);
        HouseResponse expectedResponse = new HouseResponse();
        expectedResponse.setId(1L);
        expectedResponse.setName("Gryffindor");
    
        when(houseRepository.findById(1L)).thenReturn(Optional.of(existingHouse));
        when(houseRepository.save(any(House.class))).thenReturn(updatedHouse);
        when(houseMapper.toResponse(updatedHouse)).thenReturn(expectedResponse);
    
        HouseResponse response = houseService.updateHouse(1L, houseRequest);
    
        verify(houseRepository, times(1)).save(any(House.class));
        assertNotNull(response);
        assertEquals("Gryffindor", response.getName());
    }

    @Test
    public void testDeleteHouse() {
        House house = new House(1L, "Gryffindor", null);

        when(houseRepository.findById(1L)).thenReturn(Optional.of(house));

        houseService.deleteHouse(1L);

        verify(houseRepository, times(1)).delete(house);
    }

    @Test
    public void testGetHouseByIdNotFound() {
        when(houseRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> houseService.getHouseById(1L));
        assertEquals("House not found", exception.getMessage());
    }
}

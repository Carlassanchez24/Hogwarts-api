package com.harrypotter.hogwarts_api;


import com.harrypotter.hogwarts_api.dtos.HouseRequest;
import com.harrypotter.hogwarts_api.dtos.HouseResponse;
import com.harrypotter.hogwarts_api.entities.House;
import com.harrypotter.hogwarts_api.repositories.HouseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class HouseControllerTest {

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        houseRepository.deleteAll();
    }

    @Test
    void given2Houses_whenCallGetAllHouses_thenReturnAListOfTheseHouses() throws Exception {
        // Given
        House house1 = new House("Gryffindor");
        House house2 = new House("Slytherin");
        houseRepository.save(house1);
        houseRepository.save(house2);

        // When
        mockMvc.perform(get("/api/houses").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Gryffindor")))
                .andExpect(jsonPath("$[1].name", is("Slytherin")));
    }

    @Test
    void givenHouseWithEmptyName_whenAddHouse_thenReturnBadRequest() throws Exception {
        String houseWithEmptyName = """
                    {
                        "name": ""
                    }
                """;

        mockMvc.perform(post("/api/houses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(houseWithEmptyName))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenHouseWithId_whenCallGetHouseById_thenReturnThisHouse() throws Exception {
        House house = new House("Hufflepuff");
        houseRepository.save(house);

        mockMvc.perform(get("/api/houses/" + house.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Hufflepuff")));
    }


    @Test
    void givenHouseWithId_whenCallUpdateHouse_thenReturnUpdatedHouse() throws Exception {
        House house = new House("Gryffindor");
        houseRepository.save(house);

        String updatedHouse = """
                    {
                        "name": "Gryffindor Updated"
                    }
                """;

        mockMvc.perform(put("/api/houses/" + house.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedHouse))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Gryffindor Updated")));
    }

    @Test
    void givenHouseWithId_whenCallDeleteHouse_thenReturnNoContent() throws Exception {
        House house = new House("Slytherin");
        houseRepository.save(house);

        mockMvc.perform(delete("/api/houses/" + house.getId()))
                .andExpect(status().isNoContent());
    }


}

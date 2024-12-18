package com.harrypotter.hogwarts_api;


import com.harrypotter.hogwarts_api.dtos.MagicianRequest;
import com.harrypotter.hogwarts_api.dtos.MagicianResponse;
import com.harrypotter.hogwarts_api.entities.House;
import com.harrypotter.hogwarts_api.entities.Magician;
import com.harrypotter.hogwarts_api.entities.Spell;
import com.harrypotter.hogwarts_api.mappers.MagicianMapper;
import com.harrypotter.hogwarts_api.repositories.HouseRepository;
import com.harrypotter.hogwarts_api.repositories.MagicianRepository;
import com.harrypotter.hogwarts_api.repositories.SpellRepository;
import com.harrypotter.hogwarts_api.services.MagicianService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class MagicianServiceTest {

    @Mock
    private MagicianRepository magicianRepository;

    @Mock
    private HouseRepository houseRepository;

    @Mock
    private SpellRepository spellRepository;

    @Mock
    private MagicianMapper magicianMapper;

    @InjectMocks
    private MagicianService magicianService;

    @Test
    public void testCreateMagician() {
        MagicianRequest magicianRequest = new MagicianRequest();
        magicianRequest.setName("Harry Potter");
        magicianRequest.setHouseName("Gryffindor");
        magicianRequest.setSpells(List.of("Expelliarmus", "Stupefy"));
    
        House house = new House(1L, "Gryffindor", null);
        Spell spell1 = new Spell(1L, "Expelliarmus", null);
        Spell spell2 = new Spell(2L, "Stupefy", null);
        Magician magician = new Magician(1L, "Harry Potter", house, null, null, List.of(spell1, spell2));
        MagicianResponse expectedResponse = new MagicianResponse();
        expectedResponse.setId(1L);
        expectedResponse.setName("Harry Potter");
        expectedResponse.setHouseName("Gryffindor");
        expectedResponse.setSpells(List.of("Expelliarmus", "Stupefy"));
    
        when(houseRepository.findByName("Gryffindor")).thenReturn(Optional.of(house));
        when(spellRepository.findBySpellNameIn(List.of("Expelliarmus", "Stupefy"))).thenReturn(List.of(spell1, spell2));
        when(magicianMapper.toEntity(magicianRequest)).thenReturn(magician);
        when(magicianRepository.save(magician)).thenReturn(magician);
        when(magicianMapper.toResponse(magician)).thenReturn(expectedResponse);
    
        MagicianResponse response = magicianService.createMagician(magicianRequest);
    
        verify(magicianRepository, times(1)).save(magician);
        assertNotNull(response);
        assertEquals("Harry Potter", response.getName());
        assertEquals("Gryffindor", response.getHouseName());
        assertEquals(2, response.getSpells().size());
        assertTrue(response.getSpells().contains("Expelliarmus"));
        assertTrue(response.getSpells().contains("Stupefy"));
    }

    @Test
    public void testGetAllMagicians() {
        Magician magician1 = new Magician(1L, "Harry Potter", new House(1L, "Gryffindor", null), null, null, List.of(new Spell(1L, "Expelliarmus", null)));
        Magician magician2 = new Magician(2L, "Hermione Granger", new House(1L, "Gryffindor", null), null, null, List.of(new Spell(2L, "Stupefy", null)));
        List<Magician> magicians = List.of(magician1, magician2);
    
        MagicianResponse magicianResponse1 = new MagicianResponse();
        magicianResponse1.setId(1L);
        magicianResponse1.setName("Harry Potter");
        magicianResponse1.setHouseName("Gryffindor");
        magicianResponse1.setSpells(List.of("Expelliarmus"));
    
        MagicianResponse magicianResponse2 = new MagicianResponse();
        magicianResponse2.setId(2L);
        magicianResponse2.setName("Hermione Granger");
        magicianResponse2.setHouseName("Gryffindor");
        magicianResponse2.setSpells(List.of("Stupefy"));
    
        when(magicianRepository.findAll()).thenReturn(magicians);
        when(magicianMapper.toResponse(magician1)).thenReturn(magicianResponse1);
        when(magicianMapper.toResponse(magician2)).thenReturn(magicianResponse2);
    
        List<MagicianResponse> response = magicianService.getAllMagicians();
    
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Harry Potter", response.get(0).getName());
        assertEquals("Hermione Granger", response.get(1).getName());
    }

    @Test
    public void testGetMagicianById() {
        Magician magician = new Magician(1L, "Harry Potter", new House(1L, "Gryffindor", null), null, null, List.of(new Spell(1L, "Expelliarmus", null)));
        MagicianResponse expectedResponse = new MagicianResponse();
        expectedResponse.setId(1L);
        expectedResponse.setName("Harry Potter");
        expectedResponse.setHouseName("Gryffindor");
        expectedResponse.setSpells(List.of("Expelliarmus"));
    
        when(magicianRepository.findById(1L)).thenReturn(Optional.of(magician));
        when(magicianMapper.toResponse(magician)).thenReturn(expectedResponse);
    
        MagicianResponse response = magicianService.getMagicianById(1L);
    
        assertNotNull(response);
        assertEquals("Harry Potter", response.getName());
    }

    @Test
    void testUpdateMagician() {
        MagicianRequest magicianRequest = new MagicianRequest();
        magicianRequest.setName("Harry Potter");
        magicianRequest.setHouseName("Gryffindor");
        magicianRequest.setSpells(List.of("Expelliarmus", "Stupefy"));
    
        Magician existingMagician = new Magician(1L, "Old Name", new House(1L, "Gryffindor", null), null, null, List.of(new Spell(1L, "Old Spell", null)));
        Magician updatedMagician = new Magician(1L, "Harry Potter", new House(1L, "Gryffindor", null), null, null, List.of(new Spell(1L, "Expelliarmus", null), new Spell(2L, "Stupefy", null)));
        MagicianResponse expectedResponse = new MagicianResponse();
        expectedResponse.setId(1L);
        expectedResponse.setName("Harry Potter");
        expectedResponse.setHouseName("Gryffindor");
        expectedResponse.setSpells(List.of("Expelliarmus", "Stupefy"));
    
        when(magicianRepository.findById(1L)).thenReturn(Optional.of(existingMagician));
        when(houseRepository.findByName("Gryffindor")).thenReturn(Optional.of(new House(1L, "Gryffindor", null)));
        when(spellRepository.findBySpellNameIn(List.of("Expelliarmus", "Stupefy"))).thenReturn(List.of(new Spell(1L, "Expelliarmus", null), new Spell(2L, "Stupefy", null)));
        when(magicianRepository.save(any(Magician.class))).thenReturn(updatedMagician);
        when(magicianMapper.toResponse(updatedMagician)).thenReturn(expectedResponse);
    
        MagicianResponse response = magicianService.updateMagician(1L, magicianRequest);
    
        assertNotNull(response);
        assertEquals("Harry Potter", response.getName());
        assertEquals("Gryffindor", response.getHouseName());
        assertTrue(response.getSpells().contains("Expelliarmus"));
        assertTrue(response.getSpells().contains("Stupefy"));
    }

    @Test
    public void testDeleteMagician() {
        Magician magician = new Magician(1L, "Harry Potter", new House(1L, "Gryffindor", null), null, null, List.of(new Spell(1L, "Expelliarmus", null)));

        when(magicianRepository.findById(1L)).thenReturn(Optional.of(magician));

        magicianService.deleteMagician(1L);
        
        verify(magicianRepository, times(1)).delete(magician);
    }

    @Test
    public void testGetMagicianByIdNotFound() {
        when(magicianRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> magicianService.getMagicianById(1L));
        assertEquals("Magician not found", exception.getMessage());
    }
}

package com.harrypotter.hogwarts_api;


import com.harrypotter.hogwarts_api.dtos.SpellRequest;
import com.harrypotter.hogwarts_api.dtos.SpellResponse;
import com.harrypotter.hogwarts_api.entities.Spell;
import com.harrypotter.hogwarts_api.mappers.SpellMapper;
import com.harrypotter.hogwarts_api.repositories.SpellRepository;
import com.harrypotter.hogwarts_api.services.SpellService;
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
public class SpellServiceTest {

    @Mock
    private SpellRepository spellRepository;

    @Mock
    private SpellMapper spellMapper;

    @InjectMocks
    private SpellService spellService;

    @Test
    public void testCreateSpell() {
        SpellRequest spellRequest = new SpellRequest();
        spellRequest.setSpellName("Expelliarmus");

        Spell spell = new Spell(1L, "Expelliarmus", null);
        SpellResponse expectedResponse = new SpellResponse();
        expectedResponse.setId(1L);
        expectedResponse.setSpellName("Expelliarmus");

        when(spellMapper.toEntity(spellRequest)).thenReturn(spell);
        when(spellRepository.save(spell)).thenReturn(spell);
        when(spellMapper.toResponse(spell)).thenReturn(expectedResponse);

        SpellResponse response = spellService.createSpell(spellRequest);

        verify(spellRepository, times(1)).save(spell);
        assertNotNull(response);
        assertEquals(expectedResponse.getSpellName(), response.getSpellName());
    }

    @Test
    public void testGetAllSpells() {
        Spell spell1 = new Spell(1L, "Expelliarmus", null);
        Spell spell2 = new Spell(2L, "Avada Kedavra", null);
        List<Spell> spells = List.of(spell1, spell2);

        SpellResponse spellResponse1 = new SpellResponse();
        spellResponse1.setId(1L);
        spellResponse1.setSpellName("Expelliarmus");

        SpellResponse spellResponse2 = new SpellResponse();
        spellResponse2.setId(2L);
        spellResponse2.setSpellName("Avada Kedavra");

        when(spellRepository.findAll()).thenReturn(spells);
        when(spellMapper.toResponse(spell1)).thenReturn(spellResponse1);
        when(spellMapper.toResponse(spell2)).thenReturn(spellResponse2);

        List<SpellResponse> response = spellService.getAllSpells();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Expelliarmus", response.get(0).getSpellName());
        assertEquals("Avada Kedavra", response.get(1).getSpellName());
    }

    @Test
    public void testGetSpellById() {
        Spell spell = new Spell(1L, "Expelliarmus", null);
        SpellResponse expectedResponse = new SpellResponse();
        expectedResponse.setId(1L);
        expectedResponse.setSpellName("Expelliarmus");

        when(spellRepository.findById(1L)).thenReturn(Optional.of(spell));
        when(spellMapper.toResponse(spell)).thenReturn(expectedResponse);

        SpellResponse response = spellService.getSpellById(1L);

        assertNotNull(response);
        assertEquals("Expelliarmus", response.getSpellName());
    }

    @Test
    public void testUpdateSpell() {
        SpellRequest spellRequest = new SpellRequest();
        spellRequest.setSpellName("Expelliarmus");

        Spell existingSpell = new Spell(1L, "Old Spell", null);
        Spell updatedSpell = new Spell(1L, "Expelliarmus", null);
        SpellResponse expectedResponse = new SpellResponse();
        expectedResponse.setId(1L);
        expectedResponse.setSpellName("Expelliarmus");

        when(spellRepository.findById(1L)).thenReturn(Optional.of(existingSpell));
        when(spellRepository.save(any(Spell.class))).thenReturn(updatedSpell);
        when(spellMapper.toResponse(updatedSpell)).thenReturn(expectedResponse);

        SpellResponse response = spellService.updateSpell(1L, spellRequest);

        verify(spellRepository, times(1)).save(any(Spell.class));
        assertNotNull(response);
        assertEquals("Expelliarmus", response.getSpellName());
    }

    @Test
    public void testDeleteSpell() {
        Spell spell = new Spell(1L, "Expelliarmus", null);

        when(spellRepository.findById(1L)).thenReturn(Optional.of(spell));

        spellService.deleteSpell(1L);

        verify(spellRepository, times(1)).delete(spell);
    }

    @Test
    public void testGetSpellByIdNotFound() {
        when(spellRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> spellService.getSpellById(1L));
        assertEquals("Spell not found", exception.getMessage());
    }
}

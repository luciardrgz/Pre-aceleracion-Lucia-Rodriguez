package com.alkemy.disney.disney.services;


import com.alkemy.disney.disney.dto.character.CharacterDTO;
import com.alkemy.disney.disney.entities.CharacterEntity;
import com.alkemy.disney.disney.exceptions.DuplicateExc;

import java.util.List;

public interface CharacterService {

    //CharacterDTO save(CharacterDTO dto, Long titleId);

    CharacterDTO saveInRepo(CharacterDTO dto) throws DuplicateExc;

    CharacterEntity getCharacterById(Long id);

    CharacterDTO getCharacterDTOById(Long id);

    List<CharacterDTO> getCharacters();

    CharacterDTO updateCharacter(Long id, CharacterDTO characterDTO);

    List<CharacterDTO> getByFilters(String name, Integer age, Integer weight, List<Long>titles);

    void delete(Long id);



}

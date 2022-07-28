package com.alkemy.disney.disney.services.impl;

import com.alkemy.disney.disney.dto.character.CharacterDTO;
import com.alkemy.disney.disney.entities.CharacterEntity;
import com.alkemy.disney.disney.dto.character.CharacterFiltersDTO;
import com.alkemy.disney.disney.exceptions.ParamNotFoundExc;
import com.alkemy.disney.disney.mappers.CharacterMapper;
import com.alkemy.disney.disney.repositories.CharacterRepository;
import com.alkemy.disney.disney.services.CharacterService;
import com.alkemy.disney.disney.services.TitleService;
import com.alkemy.disney.disney.repositories.specifications.CharacterSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterMapper characterMapper;
    private final CharacterRepository characterRepository;
    private final TitleService titleService;
    private final CharacterSpecification characterSpecification;

    @Lazy
    @Autowired
    public CharacterServiceImpl(CharacterMapper characterMapper,
                                CharacterRepository characterRepository,
                                TitleService titleService,
                                CharacterSpecification characterSpecification)
    {
        this.characterMapper = characterMapper;
        this.characterRepository = characterRepository;
        this.titleService = titleService;
        this.characterSpecification = characterSpecification;
    }


    // Saves a Character with an associated Title in Repository
    public CharacterDTO save(CharacterDTO dto, Long titleId) {

        CharacterEntity entity = this.characterMapper.characterDTO2Entity(dto);
        CharacterEntity savedEntity = this.characterRepository.save(entity);
        titleService.addCharacterToTitle(titleId, savedEntity.getId());
        CharacterDTO result = this.characterMapper.characterEntity2DTO(savedEntity,true);

        return result;
    }

    // Saves a Character with no movies associated in Repository
    public CharacterDTO saveWithoutMovies(CharacterDTO dto) {

        CharacterEntity entity = this.characterMapper.characterDTO2Entity(dto);
        CharacterEntity savedEntity = this.characterRepository.save(entity);
        CharacterDTO result = this.characterMapper.characterEntity2DTO(savedEntity,false);

        return result;
    }

    // Retrieves a title Entity from Repository by its id
    public CharacterEntity getCharacterById(Long id)
    {
        Optional<CharacterEntity> entity = characterRepository.findById(id);
        if(!entity.isPresent()) {
            throw new ParamNotFoundExc("Character ID not found");
        }
        return entity.get();
    }

    // Searches a character Entity from Repository by its id, converts it and retrieves a character DTO
    public CharacterDTO getCharacterDTOById(Long id){
        Optional<CharacterEntity> entity = characterRepository.findById(id);
        if(!entity.isPresent()) {
            throw new ParamNotFoundExc("Character ID not found");
        }
        CharacterDTO characterDTO = this.characterMapper.characterEntity2DTO(entity.get(),true);
        return characterDTO;
    }

    // Character search filtered by Name, Age, Weight, Associated titles IDs
    public List<CharacterDTO> getByFilters(String name, Integer age, Integer weight, List<Long>titles)
    {
        CharacterFiltersDTO filtersDTO = new CharacterFiltersDTO(name, age, weight, titles);
        List<CharacterEntity> entities = this.characterRepository.findAll(this.characterSpecification.getByFilters(filtersDTO));
        List<CharacterDTO> dtos = this.characterMapper.characterEntitySet2DTOList(entities,true);
        return dtos;
    }

    // Invokes a mapper method and modifies Character values
    public CharacterDTO updateCharacter(Long id, CharacterDTO characterDTO)
    {
        Optional<CharacterEntity>entity = characterRepository.findById(id);
        if(!entity.isPresent())
        {
            throw new ParamNotFoundExc("Character ID to modify not found");
        }
        this.characterMapper.modifyCharacterValues(entity.get(),characterDTO);
        CharacterEntity savedEntity = this.characterRepository.save(entity.get());
        CharacterDTO result = this.characterMapper.characterEntity2DTO(savedEntity, true);

        return result;
    }

    // Retrieves all Characters saved in Repository with them associated titles
    public List<CharacterDTO> getCharacters()
    {
        List<CharacterEntity> entities = characterRepository.findAll();
        List<CharacterDTO>result = characterMapper.characterEntitySet2DTOList(entities,true);
        return result;
    }

    // Soft Delete of a Character saved in Repository
    public void delete(Long id)
    {
        Optional<CharacterEntity>entity = this.characterRepository.findById(id);
        if(!entity.isPresent())
        {
            throw new ParamNotFoundExc("Character ID not found, failed to delete");
        }
        this.characterRepository.deleteById(id);
    }


}

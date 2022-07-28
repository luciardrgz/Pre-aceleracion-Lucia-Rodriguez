package com.alkemy.disney.disney.mappers;

import com.alkemy.disney.disney.dto.character.CharacterDTO;
import com.alkemy.disney.disney.dto.title.TitleDTO;
import com.alkemy.disney.disney.entities.CharacterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CharacterMapper {

    @Lazy
    @Autowired
    public TitleMapper titleMapper;

    // Converts a Character DTO to Entity
    public CharacterEntity characterDTO2Entity(CharacterDTO dto){
        CharacterEntity entity = new CharacterEntity();
        entity.setImage(dto.getImage());
        entity.setName(dto.getName());
        entity.setAge(dto.getAge());
        entity.setWeight(dto.getWeight());
        entity.setStory(dto.getStory());

        return entity;
    }

    // Converts a Character Entity to DTO
    public CharacterDTO characterEntity2DTO(CharacterEntity entity, boolean loadTitles){
        CharacterDTO dto = new CharacterDTO();
        dto.setId(entity.getId());
        dto.setImage(entity.getImage());
        dto.setName(entity.getName());
        dto.setAge(entity.getAge());
        dto.setWeight(entity.getWeight());
        dto.setStory(entity.getStory());

        if(loadTitles)
        {
            List<TitleDTO>titleDTOS = this.titleMapper.titleEntity2DTOList(entity.getTitles(), false);
            dto.setTitles(titleDTOS);
        }
        return dto;
    }

    // Converts a Character Entity List to a DTO List
    public List<CharacterDTO> characterEntitySet2DTOList(Collection<CharacterEntity> entities, boolean loadTitles)
    {
        List<CharacterDTO> dtos = new ArrayList<>();

        for(CharacterEntity entity : entities)
        {
            dtos.add(this.characterEntity2DTO(entity, loadTitles));
        }

        return dtos;
    }

    // Converts a Character DTO List to an Entity List
    public List<CharacterEntity> characterDTOList2EntityList(List<CharacterDTO>dtos)
    {
        List<CharacterEntity>entities = new ArrayList<>();

        for(CharacterDTO dto : dtos)
        {
            entities.add(this.characterDTO2Entity(dto));
        }

        return entities;
    }

    // Converts a Character DTO List to an Entity Set
    public Set<CharacterEntity> characterDTOList2EntitySet(List<CharacterDTO>dtos)
    {
        Set<CharacterEntity> entities = new HashSet<>();

        for(CharacterDTO dto : dtos)
        {
            entities.add(this.characterDTO2Entity(dto));
        }

        return entities;
    }

    // Modifies the Entity's info with the DTO info received, it's gonna be saved in Repository by Service
    public void modifyCharacterValues(CharacterEntity entity, CharacterDTO dto)
    {
        entity.setImage(dto.getImage());
        entity.setName(dto.getName());
        entity.setAge(dto.getAge());
        entity.setWeight(dto.getWeight());
        entity.setStory(dto.getStory());
    }

}

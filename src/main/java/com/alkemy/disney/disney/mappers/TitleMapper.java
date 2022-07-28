package com.alkemy.disney.disney.mappers;

import com.alkemy.disney.disney.dto.character.CharacterDTO;
import com.alkemy.disney.disney.dto.title.TitleDTO;
import com.alkemy.disney.disney.entities.CharacterEntity;
import com.alkemy.disney.disney.entities.TitleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class TitleMapper {

    @Lazy
    @Autowired
    private CharacterMapper characterMapper;

    // Converts a Title DTO to Entity
    public TitleEntity titleDTO2Entity(TitleDTO dto){
        TitleEntity entity = new TitleEntity();
        entity.setImage(dto.getImage());
        entity.setName(dto.getName());
        entity.setCreationDate(dto.getCreationDate());
        entity.setScore(dto.getScore());
        entity.setGenreId(dto.getGenreId());

            Set<CharacterEntity> characterEntities = this.characterMapper.characterDTOList2EntitySet(dto.getCharacters());
            entity.setCharacters(characterEntities);

        return entity;
    }

    // Converts a Title Entity to DTO
    public TitleDTO titleEntity2DTO(TitleEntity entity, boolean loadCharacters) {
        TitleDTO dto = new TitleDTO();
        dto.setId(entity.getId());
        dto.setImage(entity.getImage());
        dto.setName(entity.getName());
        dto.setCreationDate(entity.getCreationDate());
        dto.setScore(entity.getScore());
        dto.setGenreId(entity.getGenreId());

        if(loadCharacters)
        {
            List<CharacterDTO> characterDTOS = this.characterMapper.characterEntitySet2DTOList(entity.getCharacters(),false);
            dto.setCharacters(characterDTOS);
        }

        return dto;
    }

    // Converts a Title Entity List to a DTO List
    public List<TitleDTO> titleEntity2DTOList(List<TitleEntity>entities, boolean loadCharacters)
    {
        List<TitleDTO>dtos = new ArrayList<>();

        for(TitleEntity entity : entities)
        {
            dtos.add(this.titleEntity2DTO(entity, loadCharacters));
        }
        return dtos;
    }

    // Converts a Title DTO List to an Entity List
    public List<TitleEntity> titleDTO2EntityList(List<TitleDTO>dtos)
    {
        List<TitleEntity>entities = new ArrayList<>();

        for(TitleDTO dto : dtos)
        {
            entities.add(this.titleDTO2Entity(dto));
        }
        return entities;
    }


    // Modifies the Entity's info with the DTO info received, it's gonna be saved in Repository by Service
    public void modifyTitleValues(TitleEntity entity, TitleDTO dto)
    {
        entity.setImage(dto.getImage());
        entity.setName(dto.getName());
        entity.setCreationDate(dto.getCreationDate());
        entity.setScore(dto.getScore());
        entity.setGenreId(dto.getGenreId());
        List<CharacterEntity>characterEntities = this.characterMapper.characterDTOList2EntityList(dto.getCharacters());

        for(CharacterEntity characterEntity : characterEntities){
            entity.getCharacters().add(characterEntity);
        }
    }
}

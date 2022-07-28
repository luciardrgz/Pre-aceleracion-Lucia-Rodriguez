package com.alkemy.disney.disney.services.impl;

import com.alkemy.disney.disney.dto.title.TitleDTO;
import com.alkemy.disney.disney.dto.title.TitleFiltersDTO;
import com.alkemy.disney.disney.entities.CharacterEntity;
import com.alkemy.disney.disney.entities.TitleEntity;
import com.alkemy.disney.disney.exceptions.ParamNotFoundExc;
import com.alkemy.disney.disney.mappers.TitleMapper;
import com.alkemy.disney.disney.repositories.CharacterRepository;
import com.alkemy.disney.disney.repositories.TitleRepository;
import com.alkemy.disney.disney.repositories.specifications.TitleSpecification;
import com.alkemy.disney.disney.services.CharacterService;
import com.alkemy.disney.disney.services.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TitleServiceImpl implements TitleService {

    private final TitleMapper titleMapper;
    private final TitleRepository titleRepository;
    private final CharacterService characterService;
    private final CharacterRepository characterRepository;
    private final TitleSpecification titleSpecification;

    @Lazy
    @Autowired
    public TitleServiceImpl(TitleMapper titleMapper,
                            TitleRepository titleRepository,
                            CharacterService characterService,
                            CharacterRepository characterRepository,
    TitleSpecification titleSpecification){
        this.titleMapper = titleMapper;
        this.titleRepository = titleRepository;
        this.characterService = characterService;
        this.characterRepository = characterRepository;
        this.titleSpecification = titleSpecification;
    }

    // Saves a title in Titles Repository
    public TitleDTO save(TitleDTO dto)
    {
        TitleEntity entity = titleMapper.titleDTO2Entity(dto);
        TitleEntity savedEntity = titleRepository.save(entity);
        TitleDTO result = titleMapper.titleEntity2DTO(savedEntity, true);
        return result;
    }

    // Retrieves a title Entity from Repository by its id
    public TitleEntity getTitleById(Long id) {
        Optional<TitleEntity> entity = titleRepository.findById(id);
        if(!entity.isPresent()){
            throw new ParamNotFoundExc("Title ID not found");
        }
        return entity.get();
    }

    // Searches a title Entity from Repository by its id, converts it and retrieves a title DTO
    public TitleDTO getTitleDTOById(Long id){
        Optional<TitleEntity> entity = titleRepository.findById(id);
        if(!entity.isPresent()) {
            throw new ParamNotFoundExc("Title ID not found");
        }
        TitleDTO titleDTO = this.titleMapper.titleEntity2DTO(entity.get(), true);
        return titleDTO;
    }

    // Title search filtered by Name, GenreId, ASC|DESC order
    public List<TitleDTO> getByFilters(String name, Long genreId, String order)
    {
        TitleFiltersDTO filtersDTO = new TitleFiltersDTO(name, genreId, order);
        List<TitleEntity>entities = this.titleRepository.findAll(this.titleSpecification.getByFilters(filtersDTO));
        List<TitleDTO>dtos = this.titleMapper.titleEntity2DTOList(entities,true);

        return dtos;
    }

    // Invokes a Mapper method and modifies Title values
    public TitleDTO updateTitle(Long id, TitleDTO titleDTO){
        Optional<TitleEntity>entity = titleRepository.findById(id);

        if(!entity.isPresent())
        {
            throw new ParamNotFoundExc("Title ID to modify not found");
        }
        this.titleMapper.modifyTitleValues(entity.get(),titleDTO);
        TitleEntity savedEntity = this.titleRepository.save(entity.get());
        TitleDTO result = this.titleMapper.titleEntity2DTO(savedEntity, true);

        return result;
    }

    // Soft Delete of a Title in Repository
    public void delete(Long id)
    {
        Optional<TitleEntity>entity = this.titleRepository.findById(id);
        if(!entity.isPresent())
        {
            throw new ParamNotFoundExc("Title ID not found, failed to delete");
        }
        this.titleRepository.deleteById(id);
    }

    // Adds a Character to a list of Characters in a Title
    public void addCharacterToTitle(Long titleId, Long characterId)
    {
        TitleEntity titleEntity = this.getTitleById(titleId);

        CharacterEntity characterEntity = characterService.getCharacterById(characterId);
        titleEntity.addCharacter(characterEntity);
        titleRepository.save(titleEntity);
    }

    // Removes a Character from a list of Characters in a Title
    public void removeCharacterFromTitle(Long titleId, Long characterId)
    {
        TitleEntity titleEntity = getTitleById(titleId);

        CharacterEntity characterEntity = characterService.getCharacterById(characterId);
        titleEntity.removeCharacter(characterEntity);
        titleRepository.save(titleEntity);
    }
}

package com.alkemy.disney.disney.services;

import com.alkemy.disney.disney.dto.title.TitleDTO;
import com.alkemy.disney.disney.entities.TitleEntity;
import com.alkemy.disney.disney.exceptions.DuplicateExc;

import java.util.List;

public interface TitleService {

    TitleDTO saveInRepo(TitleDTO dto) throws DuplicateExc;

    TitleEntity getTitleById(Long id);

    void addCharacterToTitle(Long titleId, Long characterId) throws DuplicateExc;

    void removeCharacterFromTitle(Long titleId, Long characterId);

    List<TitleDTO> getByFilters(String name, Long genreId, String order);

    TitleDTO getTitleDTOById(Long id);

    TitleDTO updateTitle(Long id, TitleDTO titleDTO);

    void delete(Long id);
}

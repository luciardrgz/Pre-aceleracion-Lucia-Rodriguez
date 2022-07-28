package com.alkemy.disney.disney.services;

import com.alkemy.disney.disney.dto.GenreDTO;

import java.util.List;

public interface GenreService {

    GenreDTO save(GenreDTO dto);

    List<GenreDTO> getGenres();

    void delete(Long id);
}

package com.alkemy.disney.disney.controllers;

import com.alkemy.disney.disney.dto.GenreDTO;
import com.alkemy.disney.disney.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @PostMapping // Invokes the Genre Service to save a created Genre
    public ResponseEntity<GenreDTO>save (@RequestBody GenreDTO genre)
    {
        GenreDTO savedGenre = genreService.save(genre);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGenre);
    }

    @GetMapping // Retrieves all saved Genres
    public ResponseEntity<List<GenreDTO>> getAllGenres() {
        List<GenreDTO> genres = this.genreService.getGenres();
        return ResponseEntity.ok().body(genres);
    }

    @DeleteMapping("/{id}") // Deletes a Genre
    public ResponseEntity<Void>delete(@PathVariable Long id){
        this.genreService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

package com.alkemy.disney.disney.controllers;

import com.alkemy.disney.disney.dto.character.CharacterDTO;
import com.alkemy.disney.disney.exceptions.DuplicateExc;
import com.alkemy.disney.disney.services.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("characters")
public class CharacterController {

    @Lazy
    @Autowired
    private CharacterService characterService;

    @PostMapping("/save")// Saves a created Character with no movie associated
    public ResponseEntity<CharacterDTO> save(@RequestBody CharacterDTO characterDTO) throws DuplicateExc {
        try {
            CharacterDTO savedCharacter = characterService.saveInRepo(characterDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCharacter);
        } catch (DuplicateExc e) {
            throw new DuplicateExc("This character already exists in database - check for soft deletes");
        }
    }

    @GetMapping("/id") // Retrieves a Character by its saved id in Repository
    public ResponseEntity<CharacterDTO> getById(@PathVariable Long id) {
        CharacterDTO character = this.characterService.getCharacterDTOById(id);
        return ResponseEntity.ok().body(character);
    }

    @GetMapping // Filtered search of Chars by Name, Age, Weight, Associated titles - without filters, all Chars are displayed
    public ResponseEntity<List<CharacterDTO>> search(@RequestParam(required = false) String name,
                                                     @RequestParam(required = false) Integer age,
                                                     @RequestParam(required = false) Integer weight,
                                                     @RequestParam(required = false) List<Long> titles) {
        List<CharacterDTO> characters = this.characterService.getByFilters(name,age,weight,titles);
        return ResponseEntity.ok().body(characters);
    }

    @PutMapping("/{id}") // Updates a Character
    public ResponseEntity<CharacterDTO>update(@PathVariable Long id, @RequestBody CharacterDTO character){
        CharacterDTO returnable = this.characterService.updateCharacter(id, character);
        return ResponseEntity.ok().body(returnable);
    }

    @DeleteMapping("/{id}") // Deletes a Character
    public ResponseEntity<Void>delete(@PathVariable Long id){
        this.characterService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

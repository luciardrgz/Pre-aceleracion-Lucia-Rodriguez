package com.alkemy.disney.disney.dto.title;

import com.alkemy.disney.disney.dto.character.CharacterDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TitleDTO {
    private Long id;

    private String image;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    private Integer score; // 1 to 5

    private Long genreId;

    private List<CharacterDTO> characters;
}

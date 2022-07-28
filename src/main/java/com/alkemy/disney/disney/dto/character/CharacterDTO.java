package com.alkemy.disney.disney.dto.character;

import com.alkemy.disney.disney.dto.title.TitleDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CharacterDTO {
    private Long id;
    private String image;
    private String name;
    private Integer age;
    private Integer weight;
    private String story;
    private List<TitleDTO> titles;
}

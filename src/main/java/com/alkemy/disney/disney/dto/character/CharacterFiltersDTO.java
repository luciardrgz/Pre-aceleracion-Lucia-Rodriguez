package com.alkemy.disney.disney.dto.character;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CharacterFiltersDTO {

    private String name;
    private Integer age;
    private Integer weight;
    private List<Long>titles;

}

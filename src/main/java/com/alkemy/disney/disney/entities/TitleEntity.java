package com.alkemy.disney.disney.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "titles")
@SQLDelete(sql = "UPDATE titles SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class TitleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq")
    @GenericGenerator(name = "seq", strategy = "increment")
    private Long id;
    private String image;
    private String name;
    private Integer score;
    private boolean deleted = Boolean.FALSE;

    @Column(name = "creation_date")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate creationDate;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "characters_in_this_title",
            joinColumns = @JoinColumn(name = "title_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id"))
    private Set<CharacterEntity> characters = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "genre_id", insertable = false, updatable = false)
    private GenreEntity genre;

    @Column(name = "genre_id", nullable = false)
    private Long genreId;

    public void addCharacter(CharacterEntity characterEntity){
        characters.add(characterEntity);
    }

    public void removeCharacter(CharacterEntity characterEntity){
        characters.remove(characterEntity);
    }
}


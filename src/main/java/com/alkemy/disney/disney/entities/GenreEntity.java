package com.alkemy.disney.disney.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "genres")
@SQLDelete(sql = "UPDATE genres SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator= "seq")
    @GenericGenerator(name = "seq", strategy = "increment")
    private Long id;
    private String image;
    private String name;

    private boolean deleted = Boolean.FALSE;

    @OneToMany (mappedBy = "genre", cascade = CascadeType.PERSIST)
    private List<TitleEntity>titles = new ArrayList<>();
}

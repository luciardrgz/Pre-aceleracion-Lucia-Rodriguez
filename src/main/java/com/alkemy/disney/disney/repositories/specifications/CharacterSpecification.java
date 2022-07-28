package com.alkemy.disney.disney.repositories.specifications;

import com.alkemy.disney.disney.entities.CharacterEntity;
import com.alkemy.disney.disney.dto.character.CharacterFiltersDTO;
import com.alkemy.disney.disney.entities.TitleEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


@Component
public class CharacterSpecification{

    public Specification<CharacterEntity> getByFilters(CharacterFiltersDTO filtersDTO)
    {
        return(root, query, criteriaBuilder) -> {

            // Predicate List for Dynamic Queries
            List<Predicate>predicates = new ArrayList<>();

            // Name
            if(StringUtils.hasLength(filtersDTO.getName())) {
                predicates.add(
                        criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + filtersDTO.getName().toLowerCase() + "%")
                );
            }

            // Age
            if(filtersDTO.getAge() != null) {
                predicates.add(
                        criteriaBuilder.like(
                                root.get("age").as(String.class),
                                "%" + filtersDTO.getAge() + "%"
                        )
                );
            }

            // Weight
            if(filtersDTO.getWeight() != null) {
                predicates.add(
                        criteriaBuilder.like(
                                root.get("weight").as(String.class),
                                "%" + filtersDTO.getWeight() + "%"
                        )
                );
            }

            // Titles list
            if(!CollectionUtils.isEmpty(filtersDTO.getTitles())){
                Join<TitleEntity, CharacterEntity> join = root.join("titles", JoinType.INNER);
                Expression<String> titlesId = join.get("id");
                predicates.add(titlesId.in(filtersDTO.getTitles()));
            }

            // Removing duplicates
            query.distinct(true);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}

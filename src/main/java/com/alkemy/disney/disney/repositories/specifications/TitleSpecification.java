package com.alkemy.disney.disney.repositories.specifications;

import com.alkemy.disney.disney.dto.title.TitleFiltersDTO;
import com.alkemy.disney.disney.entities.TitleEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class TitleSpecification {

    public Specification<TitleEntity> getByFilters(TitleFiltersDTO filtersDTO) {
        return (root, query, criteriaBuilder) -> {

            // Predicate List for Dynamic Queries
            List<Predicate> predicates = new ArrayList<>();

            // Name
            if (StringUtils.hasLength(filtersDTO.getName())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                "%" + filtersDTO.getName().toLowerCase() + "%"
                        )
                );
            }

            // Genre Id
            if(filtersDTO.getGenreId() != null)
            {
                predicates.add(
                        criteriaBuilder.like(
                                root.get("genreId").as(String.class),
                                "%" + filtersDTO.getGenreId() + "%"
                        )
                );
            }

            // Removing duplicates
            query.distinct(true);

            // Order set
            String orderByField = "creationDate";
            query.orderBy(
                    filtersDTO.isASC() ?
                            criteriaBuilder.asc(root.get(orderByField)) : criteriaBuilder.desc(root.get(orderByField))
            );

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };


    }
}

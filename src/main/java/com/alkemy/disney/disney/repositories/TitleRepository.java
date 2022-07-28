package com.alkemy.disney.disney.repositories;

import com.alkemy.disney.disney.entities.TitleEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TitleRepository extends JpaRepository<TitleEntity, Long> {

    List<TitleEntity> findAll(Specification<TitleEntity> spec);
}

package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.demo.entity.Hero;

@RepositoryRestResource
public interface HeroRepository extends CrudRepository<Hero, Long> {
    List<Hero> findByNameContainingIgnoreCase(String name);
}

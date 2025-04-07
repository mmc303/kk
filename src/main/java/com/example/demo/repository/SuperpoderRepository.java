package com.example.demo.repository;

import com.example.demo.entity.Superpoder;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SuperpoderRepository extends CrudRepository<Superpoder, Long> {
}

package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.demo.entity.Superpoder;

@RepositoryRestResource
public interface SuperpoderRepository extends CrudRepository<Superpoder, Long> {

}

package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "superpoderes")
public class Superpoder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotBlank(message = "Name is mandatory")
    private String name;
    
    @ManyToOne
    @JsonIgnore
    private Hero hero;
    
    public Superpoder() {
    }
    
    public Superpoder(String name) {
        this.name = name;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Hero getHero() {
        return hero;
    }
    
    public void setHero(Hero hero) {
        this.hero = hero;
    }
    
    @Override
    public String toString() {
        return "Superpoder [id=" + id + ", name=" + name + "]";
    }
}

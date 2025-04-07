package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "heroes")
public class Hero {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotBlank(message = "Name is mandatory")
    private String name;
    
    @OneToMany(mappedBy = "hero", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Superpoder> powers = new ArrayList<>();
    
    public Hero() {
    }
    
    public Hero(String name) {
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
    
    public List<Superpoder> getSuperpoderes() {
        return powers;
    }
    
    public void setSuperpoderes(List<Superpoder> powers) {
        this.powers = powers;
    }
    
    // Helper method to add a power
    public void addPoder(Superpoder power) {
        powers.add(power);
        power.setHero(this);
    }
    
    // Helper method to remove a power
    public void removeSuperpoder(Superpoder power) {
        powers.remove(power);
        power.setHero(null);
    }
}

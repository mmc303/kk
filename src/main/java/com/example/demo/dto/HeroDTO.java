package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class HeroDTO {
    private Long id;
    private String name;
    private List<String> powers = new ArrayList<>();
    
    public HeroDTO() {
    }
    
    public HeroDTO(Long id, String name, List<String> powers) {
        this.id = id;
        this.name = name;
        this.powers = powers;
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
    
    public List<String> getPowers() {
        return powers;
    }
    
    public void setPowers(List<String> powers) {
        this.powers = powers;
    }
}

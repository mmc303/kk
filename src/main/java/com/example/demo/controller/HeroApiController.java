package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.HeroDTO;
import com.example.demo.entity.Hero;
import com.example.demo.entity.Superpoder;
import com.example.demo.repository.HeroRepository;
import com.example.demo.repository.SuperpoderRepository;

@RestController
@RequestMapping("/api/heroes")
public class HeroApiController {
    
    @Autowired
    private HeroRepository heroRepository;
    
    @Autowired
    private SuperpoderRepository superpoderRepository;
    
    @GetMapping
    public ResponseEntity<List<HeroDTO>> getAllHeroes() {
        try {
            List<Hero> heroes = new ArrayList<>();
            heroRepository.findAll().forEach(heroes::add);
            
            if (heroes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            
            List<HeroDTO> heroDTOs = heroes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
            
            return new ResponseEntity<>(heroDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<HeroDTO> getHeroById(@PathVariable Long id) {
        Optional<Hero> heroData = heroRepository.findById(id);
        
        if (heroData.isPresent()) {
            return new ResponseEntity<>(convertToDTO(heroData.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping
    public ResponseEntity<HeroDTO> createHero(@RequestBody HeroDTO heroDTO) {
        try {
            Hero hero = new Hero(heroDTO.getName());
            
            Hero savedHero = heroRepository.save(hero);
            
            if (heroDTO.getPowers() != null) {
                for (String powerName : heroDTO.getPowers()) {
                    Superpoder power = new Superpoder(powerName);
                    power.setHero(savedHero);
                    superpoderRepository.save(power);
                    savedHero.getPowers().add(power);
                }
            }
            
            Hero finalHero = heroRepository.save(savedHero);
            
            return new ResponseEntity<>(convertToDTO(finalHero), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<HeroDTO> updateHero(@PathVariable Long id, @RequestBody HeroDTO heroDTO) {
        Optional<Hero> heroData = heroRepository.findById(id);
        
        if (heroData.isPresent()) {
            Hero hero = heroData.get();
            hero.setName(heroDTO.getName());
            
            for (Superpoder power : hero.getPowers()) {
                superpoderRepository.deleteById(power.getId());
            }
            hero.getPowers().clear();
            
            if (heroDTO.getPowers() != null) {
                for (String powerName : heroDTO.getPowers()) {
                    Superpoder power = new Superpoder(powerName);
                    power.setHero(hero);
                    superpoderRepository.save(power);
                    hero.getPowers().add(power);
                }
            }
            
            Hero updatedHero = heroRepository.save(hero);
            
            return new ResponseEntity<>(convertToDTO(updatedHero), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    private HeroDTO convertToDTO(Hero hero) {
        List<String> powerNames = hero.getPowers().stream()
            .map(Superpoder::getName)
            .collect(Collectors.toList());
            
        return new HeroDTO(hero.getId(), hero.getName(), powerNames);
    }
}

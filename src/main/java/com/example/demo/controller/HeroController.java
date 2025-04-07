package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Hero;
import com.example.demo.entity.Superpoder;
import com.example.demo.repository.HeroRepository;
import com.example.demo.repository.SuperpoderRepository;

@RestController
@RequestMapping("/heroes")
public class HeroController {
    
    @Autowired
    private HeroRepository heroRepository;
    
    @Autowired
    private SuperpoderRepository superpoderRepository;
    
    @GetMapping
    public ResponseEntity<List<Hero>> getAllHeroes() {
        try {
            List<Hero> heroes = new ArrayList<>();
            heroRepository.findAll().forEach(heroes::add);
            
            if (heroes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            
            return new ResponseEntity<>(heroes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Hero> getHeroById(@PathVariable Long id) {
        Optional<Hero> heroData = heroRepository.findById(id);
        
        if (heroData.isPresent()) {
            return new ResponseEntity<>(heroData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping
    public ResponseEntity<Hero> createHero(@RequestBody Hero hero) {
        try {
            Hero newHero = heroRepository.save(new Hero(hero.getName()));
            
            if (hero.getPowers() != null) {
                for (Superpoder power : hero.getPowers()) {
                    Superpoder newPower = new Superpoder(power.getName());
                    newPower.setHero(newHero);
                    superpoderRepository.save(newPower);
                    newHero.getPowers().add(newPower);
                }
            }
            
            return new ResponseEntity<>(newHero, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Hero> updateHero(@PathVariable Long id, @RequestBody Hero hero) {
        Optional<Hero> heroData = heroRepository.findById(id);
        
        if (heroData.isPresent()) {
            Hero existingHero = heroData.get();
            existingHero.setName(hero.getName());
            
            for (Superpoder power : existingHero.getPowers()) {
                superpoderRepository.deleteById(power.getId());
            }
            existingHero.getPowers().clear();
            
            if (hero.getPowers() != null) {
                for (Superpoder power : hero.getPowers()) {
                    Superpoder newPower = new Superpoder(power.getName());
                    newPower.setHero(existingHero);
                    superpoderRepository.save(newPower);
                    existingHero.getPowers().add(newPower);
                }
            }
            
            return new ResponseEntity<>(heroRepository.save(existingHero), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteHero(@PathVariable Long id) {
        try {
            heroRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

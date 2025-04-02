package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public Iterable<Hero> getAllHeroes() {
        return heroRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Hero> getHeroById(@PathVariable Long id) {
        return heroRepository.findById(id);
    }

    @PostMapping
    public Hero createHero(@RequestBody Hero hero) {
        Hero newHero = new Hero(hero.getName());
        Hero kk = heroRepository.save(newHero);
        System.out.println(kk);
        System.out.println(hero);
        System.out.println(hero.getSuperpoderes());
        hero.getSuperpoderes().forEach(sp -> {
            Superpoder superpoder = new Superpoder(sp.getName());
            // superpoder.setId(sp.getId());
            superpoder.setHero(kk);
            System.out.println(superpoder);
            superpoderRepository.save(superpoder);
            kk.getSuperpoderes().add(superpoder);
        });

        return kk;
    }

    @DeleteMapping("/{id}")
    public void deleteHero(@PathVariable Long id) {
        heroRepository.deleteById(id);
    }
}

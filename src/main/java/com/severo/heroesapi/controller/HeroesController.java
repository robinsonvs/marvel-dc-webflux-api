package com.severo.heroesapi.controller;

import com.severo.heroesapi.document.Heroes;
import com.severo.heroesapi.service.HeroesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.severo.heroesapi.constans.HeroesConstant.HEROES_ENDPOINT_LOCAL;

@RestController
@Slf4j
public class HeroesController {

    private final HeroesService heroesService;

    public HeroesController(HeroesService heroesService) {
        this.heroesService = heroesService;
    }

    @GetMapping(HEROES_ENDPOINT_LOCAL)
    public Flux<Heroes> getAllItens() {
        log.info("requesting the list off all heroes");
        return heroesService.findAll();
    }

    @GetMapping(HEROES_ENDPOINT_LOCAL + "/id")
    public Mono<ResponseEntity<Heroes>> findByIdHero(@PathVariable String id) {
        log.info("requesting the hero with id {}", id);
        return heroesService.findByIdHero(id)
                .map(item -> new ResponseEntity<>(item, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(HEROES_ENDPOINT_LOCAL)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<Heroes> createHero(@RequestBody Heroes heroes) {
        log.info("a new hero was created");
        return heroesService.save(heroes);
    }

    @DeleteMapping(HEROES_ENDPOINT_LOCAL + "/id")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<HttpStatus> deleteByIdHero(@PathVariable String id) {
        log.info("deleting a hero with id {}", id);
        heroesService.deleteByIdHero(id);
        return Mono.just(HttpStatus.NO_CONTENT);
    }
}

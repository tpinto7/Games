package com.boylegu.springboot_vue.controller;

import com.boylegu.springboot_vue.controller.dto.request.UserCreateDto;
import com.boylegu.springboot_vue.dto.PokeBattleDto;
import com.boylegu.springboot_vue.dto.UserDto;
import com.boylegu.springboot_vue.entities.Move;
import com.boylegu.springboot_vue.entities.Pokemon;
import com.boylegu.springboot_vue.entities.PokemonBattle;
import com.boylegu.springboot_vue.service.pokemon.PokemonService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {
    @Autowired
    PokemonService pokemonService;

    @PostMapping(value = "/startGame", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PokemonBattle> createGame(@RequestParam("p1") String id1, @RequestParam("p2") String id2) throws ParseException, IOException {
        return ResponseEntity.ok(pokemonService.createGame(id1, id2));
    }

    @GetMapping(value = "/getPokemon", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Pokemon>> populatePokemon(@RequestParam("pId") String p1Id, @RequestParam("id") String id){
        return ResponseEntity.ok(pokemonService.getPokemon(id, p1Id));
    }

    @GetMapping(value = "/getAllMoves", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Move>> populatePokemon(){
        return ResponseEntity.ok(pokemonService.getAllMoves());
    }

    @PostMapping(value = "/executeMove", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    // TODO: temporary fix for id not working
    // TODO: make payload
    public ResponseEntity<String> executeMove(@RequestParam("p1") String id1, @RequestParam("p2") String id2, @RequestParam("move") int move){
        return ResponseEntity.ok(pokemonService.executeMove(id1, id2, move));
    }

//    @GetMapping(value = "/populatePokemon", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity<PokemonBattle> populatePokemon(@Validated @RequestBody PokeBattleDto pokeBattleDto) throws IOException, ParseException{
//        return ResponseEntity.ok(pokemonService.populatePokemon(pokeBattleDto));
//    }
}

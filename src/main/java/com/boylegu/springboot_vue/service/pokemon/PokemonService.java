package com.boylegu.springboot_vue.service.pokemon;

import com.boylegu.springboot_vue.dto.PokeBattleDto;
import com.boylegu.springboot_vue.entities.Move;
import com.boylegu.springboot_vue.entities.Pokemon;
import com.boylegu.springboot_vue.entities.PokemonBattle;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface PokemonService {
    PokemonBattle createGame(UUID id1, UUID id2) throws ParseException, IOException;
    List<Pokemon> getPokemon(UUID id, UUID pId);
    Long getAllMoves();
    PokemonBattle populatePokemon(PokemonBattle pokemonBattle) throws ParseException, IOException;
    void populateMoves() throws  ParseException, IOException;
}

package com.boylegu.springboot_vue.service.pokemon;

import com.boylegu.springboot_vue.dto.PokeBattleDto;
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
    PokemonBattle populatePokemon(PokemonBattle pokemonBattle) throws FileNotFoundException, ParseException, IOException;
}

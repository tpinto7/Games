package com.boylegu.springboot_vue.repository;

import com.boylegu.springboot_vue.entities.Pokemon;
import com.boylegu.springboot_vue.entities.PokemonBattle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PokemonRepo extends JpaRepository<Pokemon, UUID> {
    Pokemon save(Pokemon pokemon);
//    List<Pokemon> saveAll(List<Pokemon> pokemons);
}
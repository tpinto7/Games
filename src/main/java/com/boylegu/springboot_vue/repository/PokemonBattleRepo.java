package com.boylegu.springboot_vue.repository;

import com.boylegu.springboot_vue.entities.PokemonBattle;
import com.boylegu.springboot_vue.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PokemonBattleRepo extends JpaRepository<PokemonBattle, UUID> {
    PokemonBattle save(PokemonBattle pokemonBattle);
    Optional<PokemonBattle> findOneById(UUID id);
    Optional<PokemonBattle> findOneByUserEmailAddressAndUser2EmailAddress(String e1, String e2);
//    Optional<User> findOneByEmailAddress(String username);
}
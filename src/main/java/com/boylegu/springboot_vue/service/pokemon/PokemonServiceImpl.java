package com.boylegu.springboot_vue.service.pokemon;

import com.boylegu.springboot_vue.dto.PokeBattleDto;
import com.boylegu.springboot_vue.entities.*;
import com.boylegu.springboot_vue.repository.MoveRepo;
import com.boylegu.springboot_vue.repository.PokemonBattleRepo;
import com.boylegu.springboot_vue.repository.PokemonRepo;
import com.boylegu.springboot_vue.repository.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

@Service
@Slf4j
public class PokemonServiceImpl implements  PokemonService{
    @Autowired
    PokemonRepo pokemonRepo;

    @Autowired
    MoveRepo moveRepo;

    @Autowired
    PokemonBattleRepo pokemonBattleRepo;

    @Autowired
    UserRepo userRepo;

    // TODO: Allow Pokemon to be chosen ... like a list
    @Transactional
    @Override
    public PokemonBattle createGame(UUID id1, UUID id2) throws ParseException, IOException {
        Optional<User> userOptional1 = userRepo.findOneById(id1);
        Optional<User> userOptional2 = userRepo.findOneById(id2);

        if(!userOptional1.isPresent()){

        }
        if(!userOptional2.isPresent()){

        }

        PokemonBattle pokemonBattle = new PokemonBattle();
        pokemonBattle.setUser(userOptional1.get());
        pokemonBattle.setUser2(userOptional2.get());
        populatePokemon(pokemonBattle);
        return pokemonBattleRepo.save(pokemonBattle);
    }

    // TODO: See if can do on startup
    @Transactional
    @Override
    public PokemonBattle populatePokemon(PokemonBattle pokemonBattle) throws ParseException, IOException {
        // fix path
        FileReader reader = new FileReader("C:\\Users\\tyler\\EasyShoppr\\src\\main\\java\\com\\boylegu\\springboot_vue\\service\\pokemon\\pokemon.json");
        JSONParser jsonParser = new JSONParser();
        JSONArray pokemonList = new JSONArray();

        Object result = jsonParser.parse(reader);
        pokemonList.add(result);

        Map<String, Object> user = (Map<String, Object>) result;
        JSONObject jsonObject = new JSONObject(user);
        Object obj = jsonObject.get("pokemon");
        JSONArray array = (JSONArray)obj;

        Random rand = new Random();

        List<Pokemon> pokemons = new ArrayList<>();
        List<Pokemon> pokemons2 = new ArrayList<>();
        for(int i = 0; i < 6; ++i){
            int index = rand.nextInt(151);
            JSONObject jsonObject1 = (JSONObject) array.get(index);
            pokemons.add(addPokemon(jsonObject1));

            int index2 = rand.nextInt(151);
            JSONObject jsonObject2 = (JSONObject) array.get(index2);
            pokemons2.add(addPokemon(jsonObject2));
        }
//        pokemonRepo.saveAll(pokemons);
//        pokemonRepo.saveAll(pokemons2);
        pokemonBattle.setPokemon(pokemons);
        pokemonBattle.setPokemon2(pokemons2);

        return pokemonBattleRepo.save(pokemonBattle);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pokemon> getPokemon(UUID id, UUID pId){
        Optional<PokemonBattle> pokemonBattleOptional = pokemonBattleRepo.findOneById(id);
        if(!pokemonBattleOptional.isPresent()){
            // throw exception
        }
        PokemonBattle pokemonBattle = pokemonBattleOptional.get();
        if(pId.equals(pokemonBattle.getUser().getId())){
            return pokemonBattle.getPokemon();
        }
        else if(pId.equals(pokemonBattle.getUser2().getId())){
            return pokemonBattle.getPokemon2();
        }
        else {
            // TODO: throw exception
            return null;
        }
    }

    // can remove if it works
    @Transactional
    public Pokemon addPokemon(JSONObject jsonObject){
        Pokemon pokemon = new Pokemon();
        pokemon.setAttack(Integer.parseInt((String)jsonObject.get("attack")));
        pokemon.setDefense(Integer.parseInt((String)jsonObject.get("defense")));
        pokemon.setSpAttack(Integer.parseInt((String)jsonObject.get("spAttack")));
        pokemon.setSpDefense(Integer.parseInt((String) jsonObject.get("spDefense")));
        pokemon.setSpeed(Integer.parseInt((String)jsonObject.get("speed")));
        pokemon.setHp(Integer.parseInt((String)jsonObject.get("hp")));
        pokemon.setIndex(Integer.parseInt((String)jsonObject.get("index")));
        pokemon.setName((String) jsonObject.get("name"));
        pokemon.setImage((String) jsonObject.get("image"));
        pokemon.setType(ParseType((String) jsonObject.get("type1")));
        if(jsonObject.get("type2") != null){
            pokemon.setType2(ParseType((String) jsonObject.get("type2")));
        }
        return pokemonRepo.save(pokemon);
//        return pokemon;
    }
    public String ParseType(String type){
        String[] typeSplit = type.split("/");
        return typeSplit[typeSplit.length - 1].substring(0, typeSplit[typeSplit.length - 1].length() - 4);
    }

    @Override
    @Transactional
    public void populateMoves() throws ParseException, IOException{
        FileReader reader = new FileReader("C:\\Users\\tyler\\NewWebsite\\src\\main\\java\\com\\boylegu\\springboot_vue\\service\\pokemon\\moves.json");
        JSONParser jsonParser = new JSONParser();
        JSONArray movesList = new JSONArray();

        Object result = jsonParser.parse(reader);
        movesList.add(result);

        Map<String, Object> user = (Map<String, Object>) result;
        JSONObject jsonObject = new JSONObject(user);
        Object obj = jsonObject.get("moves");
        JSONArray array = (JSONArray)obj;
        List<Move> moves = new ArrayList<>();
        for(int index = 0; index < array.size(); ++index ){
            JSONObject jsonObject1 = (JSONObject) array.get(index);
            moves.add(addMove(jsonObject1));
        }
    }

    //TODO: use builder
    @Transactional
    public Move addMove(JSONObject jsonObject){
        Move move = new Move();
        try{
            // all moves except struggle have pp
            move.setPp(Integer.parseInt((String)jsonObject.get("pp")));
        }
        catch(Exception e){

        }
        // proper
        try {
            move.setAccuracy(Integer.parseInt((String) jsonObject.get("accuracy")));
        }
        catch(Exception e){

        }
        try {
            move.setPower(Integer.parseInt((String) jsonObject.get("power")));
        }
        catch(Exception e){

        }
        move.setCategory((String) jsonObject.get("category"));
        move.setEffect((String) jsonObject.get("effect"));
        move.setName((String) jsonObject.get("name"));
        move.setType((String) jsonObject.get("type"));
        return moveRepo.save(move);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getAllMoves(){
        return moveRepo.count();
    }
}

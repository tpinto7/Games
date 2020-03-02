package com.boylegu.springboot_vue.service.pokemon;

import com.boylegu.springboot_vue.dto.PokeBattleDto;
import com.boylegu.springboot_vue.entities.*;
import com.boylegu.springboot_vue.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

//    @Autowired
//    PokemonMoveRepo pokemonMoveRepo;

    HashMap<Pair<String, String>, Double> effectivenessMap = new HashMap<Pair<String, String>, Double>(){{
        put(new Pair("Normal", "Rock"), 0.5);
        put(new Pair("Normal", "Ghost"), 0.0);
        put(new Pair("Fire", "Water"), 0.5);
        put(new Pair("Fire", "Fire"), 0.5);
        put(new Pair("Fire", "Rock"), 0.5);
        put(new Pair("Fire", "Dragon"), 0.5);
        put(new Pair("Fire", "Grass"), 2.0);
        put(new Pair("Fire", "Ice"), 2.0);
        put(new Pair("Fire", "Bug"), 2.0);
        put(new Pair("Water", "Water"), 0.5);
        put(new Pair("Water", "Grass"), 0.5);
        put(new Pair("Water", "Dragon"), 0.5);
        put(new Pair("Water", "Fire"), 2.0);
        put(new Pair("Water", "Rock"), 2.0);
        put(new Pair("Water", "Ground"), 2.0);
        put(new Pair("Electric", "Ground"), 0.0);
        put(new Pair("Electric", "Electric"), 0.5);
        put(new Pair("Electric", "Grass"), 0.5);
        put(new Pair("Electric", "Dragon"), 0.5);
        put(new Pair("Electric", "Water"), 2.0);
        put(new Pair("Electric", "Flying"), 2.0);
        put(new Pair("Grass", "Grass"), 0.5);
        put(new Pair("Grass", "Fire"), 0.5);
        put(new Pair("Grass", "Poison"), 0.5);
        put(new Pair("Grass", "Flying"), 0.5);
        put(new Pair("Grass", "Bug"), 0.5);
        put(new Pair("Grass", "Dragon"), 0.5);
        put(new Pair("Grass", "Rock"), 2.0);
        put(new Pair("Grass", "Water"), 2.0);
        put(new Pair("Grass", "Ground"), 2.0);
        put(new Pair("Ice", "Water"), 0.5);
        put(new Pair("Ice", "Ice"), 0.5);
        put(new Pair("Ice", "Ground"), 2.0);
        put(new Pair("Ice", "Grass"), 2.0);
        put(new Pair("Ice", "Flying"), 2.0);
        put(new Pair("Ice", "Dragon"), 2.0);
        // TODO: Finish
    }};

    // TODO: Allow Pokemon to be chosen ... like a list
    // TODO: Fix bug where quering repo by id is not working
    @Transactional
    @Override
    public PokemonBattle createGame(String id1, String id2) throws ParseException, IOException {
        Optional<User> userOptional1 = userRepo.findOneByEmailAddress(id1);
        Optional<User> userOptional2 = userRepo.findOneByEmailAddress(id2);

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

        // TODO: #1 todo - see why persistance is not working by id
    @Override
    @Transactional(readOnly = true)
    public List<Pokemon> getPokemon(String id1, String id2){
        Optional<PokemonBattle> pokemonBattleOptional =
                pokemonBattleRepo.findOneByUserEmailAddressAndUser2EmailAddress(id1, id2);
        if(!pokemonBattleOptional.isPresent()){
            // throw exception
        }
        PokemonBattle pokemonBattle = pokemonBattleOptional.get();
        if(id1.equals(pokemonBattle.getUser().getEmailAddress())){
            return pokemonBattle.getPokemon();
        }
        else if(id2.equals(pokemonBattle.getUser2().getEmailAddress())){
            return pokemonBattle.getPokemon2();
        }
        else {
            // TODO: throw exception
            return null;
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<Pokemon> getPokemon(UUID id, UUID pId){
        Optional<PokemonBattle> pokemonBattleOptional =
                pokemonBattleRepo.findOneById(id);
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

        List<Move> moves = new ArrayList<Move>();
//        for(int i = 0; i < 1; ++i){
            moves.add(addMove(pokemon));
//        }
//        pokemon.setMove1(addMove());
//        pokemon.setMove2(addMove());
//        pokemon.setMove3(addMove());
//        pokemon.setMove4(addMove());
        pokemon.setMoves(moves);
        // TODO: alter dto returned to get the moves included
         pokemonRepo.save(pokemon);
//        addMove(pokemon);
        return pokemon;
    }

    public Move addMove(Pokemon pokemon){
        int idx = (int) (Math.random() * 165); // 165 is number of moves in repo
        Page<Move> questionPage = moveRepo.findAll(new PageRequest(idx, 1));
        Move m = null;
        if (questionPage.hasContent()) {
            m = questionPage.getContent().get(0);
        }
//        PokemonMove pokemonMove = new PokemonMove();
//        pokemonMove.setPokemon(pokemon);
//        pokemonMove.setMove(m);
//        pokemonMoveRepo.save(pokemonMove);
        System.out.println("banaan");
        System.out.println(m);

        return m;
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
    public List<Move> getAllMoves(){
        return moveRepo.findAll();
    }

    // Todo: actual implementation would be for both players to choose the move, and establish from there
    @Override
    public String executeMove(String id1, String id2, int moveIndex){
        Optional<PokemonBattle> pokemonBattleOptional = pokemonBattleRepo.findOneByUserEmailAddressAndUser2EmailAddress(id1, id2);
        if(!pokemonBattleOptional.isPresent()){
            // throw exception
        }
        // TODO: determine player1 vs player2 -> would pass in battle id and see if user id is id1 or id2
        PokemonBattle pokemonBattle = pokemonBattleOptional.get();
        // check for special move
        Pokemon attackingPokemon = pokemonBattle.getPokemon().get(pokemonBattle.getCurrIndex());
        Move move = attackingPokemon.getMoves().get(moveIndex);
        Pokemon defendingPokemon = pokemonBattle.getPokemon2().get(pokemonBattle.getCurrIndex2());
        double damage = calculateDamage(attackingPokemon, defendingPokemon, move);

        // todo: build string : super effective, damage, critical, etc.
        return attackingPokemon.getName() + " has done " + damage + " damage.";
    }

    // TODO: add max health column for pokemon.. on heal check it doesn't surpass
    public double calculateDamage(Pokemon attacker, Pokemon defender, Move move){
        // level is assumed to be 20
        double damage = 2 * 20 / 5 + 2;
        if(move.getType().equals("Physical")) {
            damage *= attacker.getAttack() * move.getPower();
            damage /= defender.getDefense() * 50;
        }
        else{
            damage *= attacker.getSpAttack() * move.getPower();
            damage /= defender.getSpDefense() * 50;
        }
        damage += 2;
        if(move.getType().equals(attacker.getType()) || attacker.getType2() != null && move.getType().equals(attacker.getType2())){
            damage *= 1.5;
        }
        double effectiveness = calculateEffectiveness(attacker, defender);
        // todo: add effectiveness message
        damage *= effectiveness;
        damage *= (int) (Math.random() * 39) + 217;
        damage /= 255;
        return damage;
    }
    public double calculateEffectiveness(Pokemon attacker, Pokemon defender){
        double effectiveness = 1;
        Pair<String, String> pair = new Pair(attacker.getType(), defender.getType());
        if(effectivenessMap.containsKey(pair)) effectiveness *= effectivenessMap.get(pair); // could make helper?
        if(defender.getType2() != null){
            pair = new Pair(attacker.getType(), defender.getType2());
            if(effectivenessMap.containsKey(pair)) effectiveness *= effectivenessMap.get(pair);
        }
        return effectiveness;
    }



}

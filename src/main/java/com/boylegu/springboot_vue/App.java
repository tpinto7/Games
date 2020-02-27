package com.boylegu.springboot_vue;
import com.boylegu.springboot_vue.controller.PokemonController;
import com.boylegu.springboot_vue.service.pokemon.PokemonService;
import org.json.simple.parser.ParseException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;


@Configuration
@SpringBootApplication
public class App {
	@Transactional
	public static void main(String[] args) throws IOException, ParseException {
		ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
		context.getBean(PokemonService.class).populateMoves();
	}
}

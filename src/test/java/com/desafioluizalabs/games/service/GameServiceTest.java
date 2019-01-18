package com.desafioluizalabs.games.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.lang.reflect.Type;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.desafioluizalabs.games.domain.Game;
import com.desafioluizalabs.games.services.GameService;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.reflect.TypeToken;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServiceTest {
	
	@Autowired
	private GameService gameService;
	
	@Test
	public void shouldNotFindAnyGame() {
		assertThat(this.gameService.getById(0)).isNull();
	}
	
	@Test
	public void shouldReturn21Games() {
		Map<String, Game> gamesList = this.gameService.getAll();
		assertThat(gamesList).isNotNull();
		assertThat(gamesList.size() == 21).isTrue();
	}
	
	@Test
	public void shouldMatchJsonFile() throws FileNotFoundException {
		final Map<String, Game> gamesMapFromJsonFile = JsonLoader.getListFromJsonFile();
		final Map<String, Game> gamesMapFromService = this.gameService.getAll();
		assertThat(gamesMapFromJsonFile).isNotNull();
		assertThat(gamesMapFromService).isNotNull();
		assertThat(gamesMapFromJsonFile.size() == gamesMapFromService.size()).isTrue();
		gamesMapFromService.entrySet().stream().forEach(entry -> {
			assertThat(gamesMapFromJsonFile.containsKey(entry.getKey())).isNotNull();
			if( !entry.getValue().equals(gamesMapFromJsonFile.get(entry.getKey())) ) {
				System.out.println("nao eh igual");
			}
			assertThat(entry.getValue().equals(gamesMapFromJsonFile.get(entry.getKey()))).isTrue();
		});
	}
	
	private static class JsonLoader {
		
		private static final String JSON_FILE_PATH = "src/test/resources/response.json";
		
		public static Map<String, Game> getListFromJsonFile() throws FileNotFoundException{
			
			Gson gson = new Gson();
			JsonReader reader = new JsonReader(new FileReader(JSON_FILE_PATH));
			Type type = new TypeToken<Map<String, Game>>(){}.getType();
			Map<String, Game> gamesMap = gson.fromJson(reader, type);
			
			return gamesMap;
		}
		
	}
}

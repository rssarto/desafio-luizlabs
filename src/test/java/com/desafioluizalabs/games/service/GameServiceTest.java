package com.desafioluizalabs.games.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.desafioluizalabs.games.domain.Game;
import com.desafioluizalabs.games.util.AbstractGameTest;
import com.desafioluizalabs.games.util.JsonLoader;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServiceTest extends AbstractGameTest {
	
	@Test
	public void shouldNotFindAnyGame() {
		assertThat(this.gameService.getById(0)).isNull();
	}
	
	@Test
	public void shouldMatchJsonFile() throws FileNotFoundException {
		final Map<String, Game> gamesMapFromJsonFile = JsonLoader.getListFromJsonFile();
		final Map<String, Game> gamesMapFromService = this.gameService.getAll();
		this.assertGamesMaps(gamesMapFromJsonFile, gamesMapFromService);
	}
	
	@Test
	public void shouldAddGame() {
		Set<String> playersSet = new HashSet<>();
		playersSet.add("Ricardo");
		playersSet.add("Maria");
		
		Map<String, Integer> playerKillsMap = new HashMap<>();
		playerKillsMap.put("Ricardo", 8);
		playerKillsMap.put("Maria", 5);
		
		Game game = new Game(13, playersSet, playerKillsMap);
		Long gameId = this.gameService.add(game);
		
		Game retrievedGame = this.gameService.getById(gameId);
		assertThat(retrievedGame).isNotNull();
		assertThat(game.equals(retrievedGame)).isNotNull();
	}
}

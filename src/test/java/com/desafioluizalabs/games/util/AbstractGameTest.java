package com.desafioluizalabs.games.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import com.desafioluizalabs.games.domain.Game;
import com.desafioluizalabs.games.parser.GameFileParser;
import com.desafioluizalabs.games.services.GameService;

public abstract class AbstractGameTest {
	
	@Autowired
	protected GameService gameService;
	
	@Before
	public void after() {
		if( this.gameService.getAll().isEmpty() ) {
			GameFileParser gameFileParser = new GameFileParser(this.gameService);
			try {
				gameFileParser.run(new String[] {});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void assertGamesMaps(final Map<String, Game> sourceGamesMap, final Map<String, Game> destinationGamesMap) {
		assertThat(sourceGamesMap).isNotNull();
		assertThat(destinationGamesMap).isNotNull();
		sourceGamesMap.entrySet().stream().forEach(entry -> {
			assertThat(destinationGamesMap.containsKey(entry.getKey())).isNotNull();
			assertThat(entry.getValue().equals(destinationGamesMap.get(entry.getKey()))).isTrue();
		});
	}
}

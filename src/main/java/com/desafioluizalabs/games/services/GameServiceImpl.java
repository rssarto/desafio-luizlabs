package com.desafioluizalabs.games.services;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.desafioluizalabs.games.domain.Game;
import com.desafioluizalabs.games.parser.GameFileParser;

/**
 * Responsavel por manipular o repositorio de Jogos ({@link Game})
 * @author rssarto
 *
 */
@Service
public class GameServiceImpl implements GameService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GameFileParser.class);
	
	private static final Map<String, Game> GAMES = new LinkedHashMap<>();
	private static final String ID_PREFIX = "game_";
	private static AtomicLong COUNTER = new AtomicLong();

	/**
	 * Adiciona um novo jogo ao repositorio.
	 * @return Id do jogo incluido.
	 */
	@Override
	public Long add(Game game) {
		LOGGER.info("Adicionando game: " + game.toString());
		GAMES.put(getGameId(COUNTER.incrementAndGet()), game);
		return COUNTER.get();
	}

	/**
	 * Recupera um jogo por id.
	 * @param id - Id para recuperar o jogo ({@link Game})
	 * @return Game ou nulo caso o jogo nao exista no repositorio 
	 */
	@Override
	public Game getById(long id) {
		return GAMES.get(getGameId(id));
	}

	/**
	 * Retorna todos os jogos existentes no repositorio
	 * @return Map<String, Game> - Map com todos os jogos do repositorio.
	 */
	@Override
	public Map<String, Game> getAll() {
		return GAMES;
	}
	
	private String getGameId(long id) {
		return ID_PREFIX + id;
	}

}

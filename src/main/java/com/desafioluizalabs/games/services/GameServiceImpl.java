package com.desafioluizalabs.games.services;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.desafioluizalabs.games.domain.Game;

/**
 * Responsavel por manipular o repositorio de Jogos ({@link Game})
 * @author rssarto
 *
 */
@Service
public class GameServiceImpl implements GameService {
	
	private static final Map<String, Game> games = new LinkedHashMap<>();
	private static final String ID_PREFIX = "game_";
	private static AtomicLong counter = new AtomicLong();

	/**
	 * Adiciona um novo jogo ao repositorio.
	 * @return Id do jogo incluido.
	 */
	@Override
	public Long add(Game game) {
		games.put(getGameId(counter.incrementAndGet()), game);
		return counter.get();
	}

	/**
	 * Recupera um jogo por id.
	 * @param id - Id para recuperar o jogo ({@link Game})
	 * @return Game ou nulo caso o jogo nao exista no repositorio 
	 */
	@Override
	public Game getById(long id) {
		return games.get(getGameId(id));
	}

	/**
	 * Retorna todos os jogos existentes no repositorio
	 * @return Map<String, Game> - Map com todos os jogos do repositorio.
	 */
	@Override
	public Map<String, Game> getAll() {
		return games;
	}
	
	private String getGameId(long id) {
		return ID_PREFIX + id;
	}

}

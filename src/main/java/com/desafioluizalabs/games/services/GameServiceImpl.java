package com.desafioluizalabs.games.services;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.desafioluizalabs.games.domain.Game;

@Service
public class GameServiceImpl implements GameService {
	
	private static final Map<String, Game> games = new LinkedHashMap<>();
	private static final String ID_PREFIX = "game_";
	private static AtomicLong counter = new AtomicLong();

	@Override
	public Long add(Game game) {
		games.put(getGameId(counter.incrementAndGet()), game);
		return counter.get();
	}

	@Override
	public Game getById(long id) {
		return games.get(getGameId(id));
	}

	@Override
	public Map<String, Game> getAll() {
		return games;
	}
	
	private String getGameId(long id) {
		return ID_PREFIX + id;
	}

}

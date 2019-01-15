package com.desafioluizalabs.games.services;

import java.util.Map;

import com.desafioluizalabs.games.domain.Game;

public interface GameService {
	
	void add(Game game);
	Game getById(long id);
	Map<String, Game> getAll();

}

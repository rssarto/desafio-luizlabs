package com.desafioluizalabs.games;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.desafioluizalabs.games.domain.Game;
import com.desafioluizalabs.games.services.GameService;

@RestController
@RequestMapping(value="/api")
public class GamesController {
	
	@Autowired
	private GameService gameService;
	
	@ResponseStatus(code=HttpStatus.NOT_FOUND)
	@GetMapping("/v1/game")
	public ResponseEntity<Map<String, Resource<Game>>> getAll(){
		Map<String, Game> gamesMap = this.gameService.getAll();
		if( Objects.nonNull(gamesMap) && !gamesMap.isEmpty() ) {
			final Map<String, Resource<Game>> mapGameResource = new LinkedHashMap<>();
			gamesMap.forEach((id, game) -> {
				mapGameResource.put(id, this.createResource(game, id));
			});
			return new ResponseEntity<Map<String, Resource<Game>>>(mapGameResource, HttpStatus.OK);
		}
		return null;
	}
	
	@ResponseStatus(code=HttpStatus.NOT_FOUND)
	@GetMapping("/v1/game/{id}")
	public ResponseEntity<Resource<Game>> getById(@PathVariable(name="id") long id){
		Game game = this.gameService.getById(id);
		if( game != null ) {
			return new ResponseEntity<Resource<Game>>(this.createResource(game, id), HttpStatus.OK);
		}
		return null;
	}
	
	private Resource<Game> createResource(Game game, Long id){
		return this.createResource(game, "game_" + id);
	}
	
	private Resource<Game> createResource(Game game, String id){
		Long gameId = Long.valueOf(StringUtils.substringAfter(id, "_"));
		Resource<Game> gameResource = new Resource<>(game);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass(), id).getById(gameId));
		gameResource.add(linkTo.withRel("self"));
		linkTo = linkTo(methodOn(this.getClass()).getAll());
		gameResource.add(linkTo.withRel("getAll"));
		return gameResource;
	}
}

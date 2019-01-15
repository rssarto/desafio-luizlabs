package com.desafioluizalabs.games;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	
	@GetMapping("/v1/game")
	public ResponseEntity<Map<String, Game>> getAll(){
		ResponseEntity<Map<String, Game>> responseEntity = new ResponseEntity<Map<String,Game>>(this.gameService.getAll(), HttpStatus.OK);
		return responseEntity;
	}
	
	@ResponseStatus(code=HttpStatus.NOT_FOUND)
	@GetMapping("/v1/game/{id}")
	public ResponseEntity<Game> getById(@PathVariable(name="id") long id){
		Game game = this.gameService.getById(id);
		if( game != null ) {
			return new ResponseEntity<Game>(game, HttpStatus.OK);
		}
		return null;
	}

}

package com.desafioluizalabs.games.parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.desafioluizalabs.games.domain.Game;
import com.desafioluizalabs.games.domain.Player;
import com.desafioluizalabs.games.services.GameService;

@Profile("!test")
@Component
public class GameFileParser implements CommandLineRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(GameFileParser.class);
	
	private static final String LOG_FILE_PATH = "/games.log";
	private static final String INIT_GAME_MARKER = "InitGame:";
	private static final String CLIENT_USER_INFO_MARKER = "ClientUserinfoChanged:";
	private static final String KILL_MARKER = "Kill:";
	private static final String WORLD_MARKER = "<world>";
	
	private GameService gameService;
	
	public GameFileParser(@Autowired GameService gameService) {
		this.gameService = gameService;
	}

	@Override
	public void run(String... args) throws Exception {
		InputStream is = getClass().getResourceAsStream(LOG_FILE_PATH);
		if( is != null ) {
			Reader reader = new InputStreamReader(is);
			try( BufferedReader br = new BufferedReader(reader) ) {
				
				Game game = null;
				
				String readLine = br.readLine();
				
				while( readLine != null ) {
					logger.info(readLine);
					if (isReadableLine(readLine) ){
						StringBuilder sb = new StringBuilder(readLine);
						if( StringUtils.contains(sb.toString(), INIT_GAME_MARKER) ) {
							if( game != null ) {
								this.gameService.add(game);
							}
							game = new Game();
						}
						
						if( isDataLine(readLine) ) {
							String playerName = getPlayerName(sb.toString());
							if( StringUtils.isNotBlank(playerName) ) {
								game.addPlayer(playerName);
							} else {
								Player player = getKill(sb.toString());
								if( player != null ) {
									game.addKill(player.getName(), player.getKill());
								}
							}
						}
					}
					
					readLine = br.readLine();
					
					if( readLine == null && game != null ) {
						this.gameService.add(game);
					}
				}
			}
		}
	}
	
	private static String getPlayerName(String line) {
		if( StringUtils.contains(line, CLIENT_USER_INFO_MARKER) ) {
			String lineContent = StringUtils.substringAfter(line, CLIENT_USER_INFO_MARKER);
			lineContent = StringUtils.substringBetween(lineContent, "n\\", "\\t");
			if( !lineContent.trim().equals(WORLD_MARKER) ) {
				return lineContent.trim();
			}
		}
		
		return null;
	}
	
	private static Player getKill(String line) {
		if( StringUtils.contains(line, KILL_MARKER) ) {
			String lineContent = StringUtils.substringAfter(line, KILL_MARKER); 
			lineContent = StringUtils.substringAfter(lineContent, ":");
			lineContent = StringUtils.substringBefore(lineContent, "by");
			String[] players = lineContent.split("killed");
			if( players[0].trim().equals(WORLD_MARKER) ) {
				return new Player(players[1].trim(), Integer.valueOf(-1));
			}else if( !players[0].trim().equals(players[1].trim()) ) {
				return new Player(players[0].trim(), Integer.valueOf(1));
			}else {
				return new Player(players[0].trim(), Integer.valueOf(0));
			}
		}
		
		return null;
	}
	
	private boolean isReadableLine(String line) {
		return line.contains(INIT_GAME_MARKER) || isDataLine(line);
	}
	
	private boolean isDataLine(String line) {
		return line.contains(CLIENT_USER_INFO_MARKER) || line.contains(KILL_MARKER);
	}

}

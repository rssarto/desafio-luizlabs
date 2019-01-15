package com.desafioluizalabs.games.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Game {
	
	private long total_kills;
	private Set<String> players;
	private Map<String, Integer> kills;
	
	public Game() {
		this.players = new HashSet<>();
		this.kills = new HashMap<>();
	}
	
	public Game(int total_kills, Set<String> players, Map<String, Integer> kills) {
		super();
		this.total_kills = total_kills;
		this.players = players;
		this.kills = kills;
	}

	public long getTotal_kills() {
		return total_kills;
	}
	public void setTotal_kills(long total_kills) {
		this.total_kills = total_kills;
	}
	public Set<String> getPlayers() {
		return players;
	}
	public void setPlayers(Set<String> players) {
		this.players = players;
	}
	public Map<String, Integer> getKills() {
		return kills;
	}
	public void setKills(Map<String, Integer> kills) {
		this.kills = kills;
	}
	
	public void addPlayer(String player) {
		this.players.add(player);
		if( !this.kills.containsKey(player) )
			this.kills.put(player, 0);
	}
	
	public void addKill(String player, Integer kill) {
		this.total_kills++;
		if( kills.containsKey(player) ) {
			Integer currentKills = kills.get(player);
			currentKills += kill;
			kill = currentKills;
		} 
		kills.put(player, kill);
	}
}

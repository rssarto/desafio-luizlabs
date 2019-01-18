package com.desafioluizalabs.games.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
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

	@Override
	public boolean equals(Object other) {
		
        if (other == this) { 
            return true; 
        } 
        
        if (!(other instanceof Game)) { 
            return false; 
        }        
  		
		final Game otherGame = (Game) other;
		
		if(otherGame.getTotal_kills() != this.getTotal_kills()) {
			return false;
		}
		
		if( this.getPlayers() != null ) {
			if( otherGame.getPlayers() != null ) {
				if( this.getPlayers().size() != otherGame.getPlayers().size() ) {
					return false;
				}
				
				for( String playerName : this.getPlayers() ) {
					if( !otherGame.getPlayers().contains(playerName) ) {
						return false;
					}
				}
			}else {
				return false;
			}
		}else {
			if( otherGame.getPlayers() != null ) {
				return false;
			}
		}
		
		if( this.getKills() != null ) {
			if( otherGame.getKills() != null ) {
				if( this.getKills().size() != otherGame.getKills().size() ) {
					return false;
				}
				
				for( Entry<String, Integer> entry : this.getKills().entrySet() ) {
					if( otherGame.getKills().containsKey(entry.getKey()) ) {
						if( entry.getValue() != null ) {
							if( otherGame.getKills().get(entry.getKey()) != null ) {
								if( entry.getValue().intValue() != otherGame.getKills().get(entry.getKey()).intValue() ) {
									return false;
								}
							}else {
								return false;
							}
						}else {
							if( otherGame.getKills().get(entry.getKey()) != null ) {
								return false;
							}
						}
					}else {
						return false;
					}
				}
			}else {
				return false;
			}
		}else {
			if( otherGame.getKills() != null ) {
				return false;
			}
		}
		
		return true;
	}
}

package com.desafioluizalabs.games.domain;

public class Player {
	
	private String name;
	private Integer kill;
	
	public Player() {
		
	}
	
	public Player(String name, Integer kill) {
		super();
		this.name = name;
		this.kill = kill;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getKill() {
		return kill;
	}

	public void setKill(Integer kill) {
		this.kill = kill;
	}
}

package com.example.MonopolyGame.model;

public class PlayersModel {
	private int id;
	private String name;
	private int game_id;
	private int current_cash;
	private int current_position;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGame_id() {
		return game_id;
	}

	public void setGame_id(int game_id) {
		this.game_id = game_id;
	}

	public int getCurrent_cash() {
		return current_cash;
	}

	public void setCurrent_cash(int current_cash) {
		this.current_cash = current_cash;
	}

	public int getCurrent_position() {
		return current_position;
	}

	public void setCurrent_position(int current_position) {
		this.current_position = current_position;
	}

}

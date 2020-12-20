package com.example.MonopolyGame.model;

public class GamesModel {
	private int game_id;
	private int active;
	private int current_turn_player_id;

	public int getGame_id() {
		return game_id;
	}

	public void setGame_id(int game_id) {
		this.game_id = game_id;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getCurrent_turn_player_id() {
		return current_turn_player_id;
	}

	public void setCurrent_turn_player_id(int current_turn_player_id) {
		this.current_turn_player_id = current_turn_player_id;
	}

}

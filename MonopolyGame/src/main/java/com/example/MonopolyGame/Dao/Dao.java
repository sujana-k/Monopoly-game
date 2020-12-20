package com.example.MonopolyGame.Dao;

import com.example.MonopolyGame.model.GamesModel;
import com.example.MonopolyGame.model.PlacesModel;
import com.example.MonopolyGame.model.PlayersModel;

public interface Dao {

	public void createGame(GamesModel gamesModel);

	public String joinGame(PlayersModel playersModel);

	public String rollDie(String name);

}

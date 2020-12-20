package com.example.MonopolyGame.service;

import com.example.MonopolyGame.model.GamesModel;
import com.example.MonopolyGame.model.PlacesModel;
import com.example.MonopolyGame.model.PlayersModel;

public interface MonoService {

	public void creteGame(GamesModel gamesModel);

	public String joinGame(PlayersModel playersModel);

	public String rollDie(PlacesModel placesModel, GamesModel gamesModel, PlayersModel playersModel);

}

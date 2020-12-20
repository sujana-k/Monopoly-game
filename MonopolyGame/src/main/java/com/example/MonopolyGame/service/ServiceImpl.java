package com.example.MonopolyGame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MonopolyGame.Dao.Dao;
import com.example.MonopolyGame.model.GamesModel;
import com.example.MonopolyGame.model.PlacesModel;
import com.example.MonopolyGame.model.PlayersModel;

@Service
public class ServiceImpl implements MonoService {
	@Autowired
	Dao dao;

	@Override
	public void creteGame(GamesModel gamesModel) {
		dao.createGame(gamesModel);
	}

	@Override
	public String joinGame(PlayersModel playersModel) {
		return dao.joinGame(playersModel);

	}

	@Override
	public String rollDie(PlacesModel placesModel, GamesModel gamesModel, PlayersModel playersModel) {

		return dao.rollDie(placesModel, gamesModel, playersModel);
	}

}

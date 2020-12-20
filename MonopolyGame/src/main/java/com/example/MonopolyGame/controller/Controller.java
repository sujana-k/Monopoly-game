package com.example.MonopolyGame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.MonopolyGame.model.GamesModel;
import com.example.MonopolyGame.model.PlacesModel;
import com.example.MonopolyGame.model.PlayersModel;
import com.example.MonopolyGame.service.MonoService;

@RestController
public class Controller {
	@Autowired
	MonoService monoService;

	@PostMapping("/create-game")
	public String createGame(@RequestBody GamesModel gamesModel) {
		monoService.creteGame(gamesModel);
		return "game created successfully";
	}

	@PostMapping("/join-game")
	public String joinGame(@RequestBody PlayersModel playersModel) {
		return monoService.joinGame(playersModel);
	}

	@PostMapping("/roll-dies/{id}")
	public String rollDies(@RequestBody GamesModel gamesModel, @RequestBody PlacesModel placesModel,
			@RequestBody PlayersModel playersModel, @PathVariable int id) {
		return monoService.rollDie(placesModel, gamesModel, playersModel);
	}

}

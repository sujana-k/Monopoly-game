package com.example.MonopolyGame.Dao;

import java.beans.Statement;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.MonopolyGame.model.GamesModel;
import com.example.MonopolyGame.model.PlacesModel;
import com.example.MonopolyGame.model.PlayersModel;

@Repository
public class DaoImpl implements Dao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	Statement statement;

	private String insert = "insert into monoploly.games(active) values(1)";
	private String update = "update games set active=0 , current_turn_playerId=null where active=1";
	private String resetPlayers = " update players set game_id=0 where game_id!=0";
	private String statusCheck = "select count(*)  from games where current_turn_playerId != null";
	private String game_Id = "select game_id from games where active=1";

	@Override
	public void createGame(GamesModel gamesModel) {
		jdbcTemplate.update(update);
		jdbcTemplate.update(resetPlayers);
		jdbcTemplate.update(insert);

	}

	@Override
	public String joinGame(PlayersModel playerModel) {
		int gameId = jdbcTemplate.queryForObject(game_Id, Integer.class);
		String addPlayer = "insert into monoploly.players(name,game_id,current_cash,current_position) values('"
				+ playerModel.getName() + "'," + gameId + ",1000,0)";
		String playerCheck = "select count(*)  from players where name= '" + playerModel.getName() + "'";
		String countCheck = "select count(*)  from players where game_id=" + gameId;

		int count = jdbcTemplate.queryForObject(statusCheck, Integer.class);
		if (count == 0) {
			int player = jdbcTemplate.queryForObject(playerCheck, Integer.class);
			if (player == 0) {
				int playerCount = jdbcTemplate.queryForObject(countCheck, Integer.class);
				if (playerCount >= 2)
					return "already 2 players in the game " + playerModel.getName() + " cannot join the game";

				jdbcTemplate.update(addPlayer);
				return "player " + playerModel.getName() + " joined successfully";
			} else {
				return "player " + playerModel.getName() + " already exist";
			}
		} else
			return "game already started player " + playerModel.getName() + " cannot join";

	}

	@Override
	public String rollDie(PlacesModel placesModel, GamesModel gamesModel, PlayersModel playersModel) {
		String update = "update table games set current_player_id=" + playersModel.getId();
		jdbcTemplate.update(update);
		String getPlayerId = "select id from players where name='" + playersModel.getName() + "'"
				+ "and game_id=game_id";
		int playerId = jdbcTemplate.queryForObject(getPlayerId, Integer.class);

		Random rand = new Random();

		int die1 = rand.nextInt(6);
		int die2 = rand.nextInt(6);
		String checkTurn = "select count(*) from games where active=1 and current_turn_id=" + playerId;
		int check = jdbcTemplate.queryForObject(checkTurn, Integer.class);
		if (check == 0) {
			return "It is not " + playersModel.getId() + " your turn";
		}
		String currentPosition = "select current_position from players where id=" + playersModel.getId();
		int cur_pos = jdbcTemplate.queryForObject(currentPosition, Integer.class);
		String currentCash = "select current_cash from players where id=" + playersModel.getId();
		int cur_cash = jdbcTemplate.queryForObject(currentCash, Integer.class);
		if (cur_pos + die1 + die2 > 11) {
			cur_cash += 200;
			cur_pos = cur_pos + die1 + die2 - 11;
		} else
			cur_pos = cur_pos + die1 + die2;

		String isPurchased = "select  is_purchased  from places where id=" + cur_pos;
		String rentPrice = "select   rent_price from places where id=" + cur_pos;
		String buyPrice = "select   buy_price from places where id=" + cur_pos;
		int is_Purchased = jdbcTemplate.queryForObject(isPurchased, Integer.class);
		int rent_Price = jdbcTemplate.queryForObject(rentPrice, Integer.class);
		int buy_Price = jdbcTemplate.queryForObject(buyPrice, Integer.class);
		if (is_Purchased == 1) {
			cur_cash = cur_cash - rent_Price;
			String upDate1 = "update table players set current_cash+=rent_price where id=owned_by_player_id";
			jdbcTemplate.update(upDate1);
		} else {
			cur_cash = cur_cash - buy_Price;
		}
		String update2 = "update table players set current_position=cur_pos, current_cash=cur_cash where id=id";
		jdbcTemplate.update(update2);
		if (cur_cash == 0) {
			String update3 = "update table games set active=0";
			jdbcTemplate.update(update3);
			String update4 = "update table places set is_purchased=0 and owned_by_player_id=null";
			jdbcTemplate.update(update4);
			return "game_over";
		} else
			return "continue playing";

	}

}

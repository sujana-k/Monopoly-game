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
	public String rollDie(String name) {
		//Get player Id
		String getPlayerId = "select id from players where name='" + name+"'" ;
		int playerId = jdbcTemplate.queryForObject(getPlayerId, Integer.class);
		int gameId = jdbcTemplate.queryForObject(game_Id, Integer.class);
		String strPlayer2 = "select id from players where id!=" + playerId +"and game_id="+gameId;
		int player2 = jdbcTemplate.queryForObject(strPlayer2, Integer.class);
		
		//check the current player
		 String checkTurn = "select playerId  from games where current_turn_playerId = "+playerId;
		 int checkTurnId = jdbcTemplate.queryForObject(checkTurn, Integer.class);
		 if (checkTurnId == playerId) {
			 //roll dies
			 Random rand = new Random();
			 	int die1 = rand.nextInt(6);
				int die2 = rand.nextInt(6);
				int diesRolled=die1+die2;
				String message="die rolled "+diesRolled;

				String currentPosition = "select current_position from players where id=" + playerId;
				int cur_pos = jdbcTemplate.queryForObject(currentPosition, Integer.class);
				String currentCash = "select current_cash from players where id=" + playerId;
				int cur_cash = jdbcTemplate.queryForObject(currentCash, Integer.class);
				if (cur_pos + diesRolled > 11) {
					message=message+" you crossed start position so you gain $200 ";
					cur_cash += 200;
					cur_pos = cur_pos + diesRolled - 11;
				} else
					cur_pos = cur_pos + diesRolled;
				
				//place property check
				String propertyName = "select  name from places where id=" + cur_pos;
				String isPurchased = "select  is_purchased  from places where id=" + cur_pos;
				String rentPrice = "select   rent_price from places where id=" + cur_pos;
				String buyPrice = "select   buy_price from places where id=" + cur_pos;
				int is_Purchased = jdbcTemplate.queryForObject(isPurchased, Integer.class);
				int rent_Price = jdbcTemplate.queryForObject(rentPrice, Integer.class);
				int buy_Price = jdbcTemplate.queryForObject(buyPrice, Integer.class);
				String propName=jdbcTemplate.queryForObject(propertyName, String.class);
				 message=message+" and landed on place "+propName;
				if (is_Purchased == 1) {
					message=message+" , paid rent $"+rent_Price;
					cur_cash = cur_cash - rent_Price;
					//Adding rent to the owner
					String upDate1 = "update table players set current_cash+="+rent_Price+" where id=owned_by_player_id";
					jdbcTemplate.update(upDate1);
					
				} else {
					message=message+" , unclaimed place and hence bought for $"+buy_Price;
					cur_cash = cur_cash - buy_Price;
					
				}
				message=message+", remaining balance $"+cur_cash;
				//updating cash for current player
				String upDate1 = "update table players set current_cash="+cur_cash+" where id="+playerId;
				jdbcTemplate.update(upDate1);
				String update = "update table games set current_player_id=" + player2;
				jdbcTemplate.update(update);
				if(cur_cash<=0) {
					message=message+" game over, you lost";
					
					//reseting the game players and places
					String update3 = "update table games set active=0";
					jdbcTemplate.update(update3);
					String update4 = "update table places set is_purchased=0 and owned_by_player_id=null";
					jdbcTemplate.update(update4);
					String update5="update table players set game_id=0";
					jdbcTemplate.update(update5);
				}
				return message;
				
			}else
				return "It is not   your turn- "+name;
		
		

	}

}

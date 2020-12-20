package com.example.MonopolyGame.model;

public class PlacesModel {
	private int id;
	private String name;
	private int is_purchased;
	private int buy_price;
	private int rent_price;
	private int owned_by_player_id;

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

	public int getIs_purchased() {
		return is_purchased;
	}

	public void setIs_purchased(int is_purchased) {
		this.is_purchased = is_purchased;
	}

	public int getBuy_price() {
		return buy_price;
	}

	public void setBuy_price(int buy_price) {
		this.buy_price = buy_price;
	}

	public int getRent_price() {
		return rent_price;
	}

	public void setRent_price(int rent_price) {
		this.rent_price = rent_price;
	}

	public int getOwned_by_player_id() {
		return owned_by_player_id;
	}

	public void setOwned_by_player_id(int owned_by_player_id) {
		this.owned_by_player_id = owned_by_player_id;
	}

}

package com.warren.tournament.util;

import static org.mockito.Mockito.when;

import java.util.Comparator;
import java.util.TreeSet;

import org.mockito.Mockito;

import com.warren.tournament.entity.Player;

public class PlayerMocks {

	public static TreeSet<Player> getPlayerMocks(int quantity) {
		final TreeSet<Player> playerMocks = new TreeSet<Player>(new Comparator<Player>(){
			public int compare(Player player1, Player player2) {
				return player1.getRank() - player2.getRank();
			}});
		for(int i=1; i<=quantity; i++) {
			Player player = Mockito.mock(Player.class);
			when(player.getEmail()).thenReturn("player" + String.valueOf(i) + "@gmail.com");
			when(player.getFname()).thenReturn("fname" + String.valueOf(i));
			when(player.getFname()).thenReturn("lname" + String.valueOf(i));
			when(player.getRank()).thenReturn(i);
			playerMocks.add(player);
		}
		return playerMocks;
	}
}

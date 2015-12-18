package com.warren.tournament.util;

import static org.mockito.Mockito.when;

import java.util.HashSet;

import org.mockito.Mockito;

import com.warren.tournament.entity.Player;

public class PlayerMocks {

	public static HashSet<Player> getPlayerMocks(int quantity) {
		final HashSet<Player> playerMocks = new HashSet<Player>();
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

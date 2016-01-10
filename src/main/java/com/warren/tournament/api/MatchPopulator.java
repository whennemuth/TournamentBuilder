package com.warren.tournament.api;

import java.util.TreeSet;

import com.warren.tournament.entity.Match;
import com.warren.tournament.entity.Side;

public interface MatchPopulator {
	public void populate(Match match, TreeSet<Side> unassignedSides);
}

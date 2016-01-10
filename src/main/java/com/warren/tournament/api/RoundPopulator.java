package com.warren.tournament.api;

import java.util.TreeSet;

import com.warren.tournament.entity.Round;
import com.warren.tournament.entity.Side;

public interface RoundPopulator {
	public boolean populate(Round round, TreeSet<Side> unassignedSides);
}

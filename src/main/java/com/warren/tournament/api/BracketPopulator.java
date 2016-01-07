package com.warren.tournament.api;

import java.util.Comparator;

import com.warren.tournament.entity.Bracket;
import com.warren.tournament.entity.Side;

public interface BracketPopulator {
	
	public boolean populate(Bracket bracket, Comparator<Side> comparator);
}

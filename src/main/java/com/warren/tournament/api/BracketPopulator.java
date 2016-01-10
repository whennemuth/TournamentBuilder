package com.warren.tournament.api;

import com.warren.tournament.entity.Bracket;

public interface BracketPopulator {
	
	public boolean populate(Bracket bracket, SideSorter sideSorter);
}

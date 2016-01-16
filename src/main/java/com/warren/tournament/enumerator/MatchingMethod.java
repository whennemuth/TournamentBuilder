package com.warren.tournament.enumerator;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * A matching method is meant how are two players selected to play each other in a match.
 * For example, the goal of one matching method might be to optimize the chances that the two best players eventually
 * meet each other in the finals, which would require pairing up the lowest ranked player with the highest ranked player 
 * in each round of a bracket.
 * 
 * @author Warren
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MatchingMethod {
	HIGHEST_WITH_LOWEST_RANK(
			"HighLow",
			"Highest ranked player plays lowest ranked, 2nd highest player plays 2nd lowest ranked, etc."),
	CLOSEST_RANK(
			"Closest",
			"Highest ranked player plays 2nd highest ranked player, 3rd highest player plays 4th highest, etc."),
	MIDDLE_RANK(
			"Middle",
			"Highest ranked player plays middle ranked player, 2nd highest player plays next lowest ranked player from the middle, etc."),
	RANDOM(
			"Random",
			"Players are assigned to play one another randomly");
	
	private String name;
	private String description;
	private MatchingMethod(String name, String description) {
		this.name = name;
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public String getValue() {
		return this.name();
	}
	public String getDescription() {
		return description;
	}
}

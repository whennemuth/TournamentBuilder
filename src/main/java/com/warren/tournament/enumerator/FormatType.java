package com.warren.tournament.enumerator;

/**
 * http://www.r2sports.com/bracket-formats.asp
 * @author Warren
 *
 */
public enum FormatType {
	SINGLE_ELIMINATION("Single Elimination"),
	DOUBLE_ELIMINATION("Double Elimination"),
	CONSOLATION("Consolation"),
	TRIPLE_KNOCKOUT("Triple Knockout"),
	FEED_IN_DROPDOWN("Feed-In Dropdown"),
	FEED_IN_WITH_CONSOLATION("Feed-In with Consolation"),
	CLASSIC_PLATE_DROPDOWN("Classic Plate Dropdown"),
	OLYMPIC_FORMAT_DIVISION("Olympic Format Division"),
	QUALIFIER_DRAW("Qualifier Draw"),
	
	ROUND_ROBIN("Round Robin"),
	DOUBLE_ROUND_ROBIN("Double Round Robin"),
	TRIPLE_ROUND_ROBIN("Triple Round Robin");
	
	private String description;
	private FormatType(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}

}

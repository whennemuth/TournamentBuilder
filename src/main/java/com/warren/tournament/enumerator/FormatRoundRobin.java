package com.warren.tournament.enumerator;

/**
 * http://www.r2sports.com/bracket-formats.asp
 * @author Warren
 *
 */
public enum FormatRoundRobin {

	ROUND_ROBIN("Round Robin"),
	DOUBLE_ROUND_ROBIN("Double Round Robin"),
	TRIPLE_ROUND_ROBIN("Triple Round Robin");
	
	private String description;
	
	private FormatRoundRobin(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
}

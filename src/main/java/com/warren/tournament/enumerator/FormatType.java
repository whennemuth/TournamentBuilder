package com.warren.tournament.enumerator;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * http://www.r2sports.com/bracket-formats.asp
 * @author Warren
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FormatType {
	SINGLE_ELIMINATION("Single Elimination", Orientation.BRACKET),
	DOUBLE_ELIMINATION("Double Elimination", Orientation.BRACKET),
	CONSOLATION("Consolation", Orientation.BRACKET),
	TRIPLE_KNOCKOUT("Triple Knockout", Orientation.BRACKET),
	FEED_IN_DROPDOWN("Feed-In Dropdown", Orientation.BRACKET),
	FEED_IN_WITH_CONSOLATION("Feed-In with Consolation", Orientation.BRACKET),
	CLASSIC_PLATE_DROPDOWN("Classic Plate Dropdown", Orientation.BRACKET),
	OLYMPIC_FORMAT_DIVISION("Olympic Format Division", Orientation.BRACKET),
	QUALIFIER_DRAW("Qualifier Draw", Orientation.BRACKET),
	
	ROUND_ROBIN("Round Robin", Orientation.ROUND_ROBIN),
	DOUBLE_ROUND_ROBIN("Double Round Robin", Orientation.ROUND_ROBIN),
	TRIPLE_ROUND_ROBIN("Triple Round Robin", Orientation.ROUND_ROBIN);
	
	private String description;
	private Orientation orientation;
	private FormatType(String description, Orientation orientation) {
		this.description = description;
		this.orientation = orientation;
	}
	public String getDescription() {
		return description;
	}
	public Orientation getOrientation() {
		return orientation;
	}	
    public final String getName() {
        return this.name();
    }
    
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public static enum Orientation {
		BRACKET("Elimination Brackets"),
		ROUND_ROBIN("Round Robin"),
		POOL_PLAY("Pool Play"),
		HEAT("Heats");
		
		private String description;
		private Orientation(String description) {
			this.description = description;
		}
		public String getDescription() {
			return this.description;
		}
	    public final String getName() {
	        return this.name();
	    }
	}
}

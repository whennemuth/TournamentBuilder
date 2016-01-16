package com.warren.tournament.enumerator;

public enum Event {

	SUCCESS("Success", "The service request has completed successfully"),
	UNKNOWN_ERROR("Error", "An error has occurred"),
	AUTHENTICATION_FAILURE("Authentication Failure", "Authentication details are invalid or missing"),
	AUTHORIZATION_FAILURE("Unauthorized", "The requester is not authorized to access the service");
	
	private String shortName;
	private String description;
	
	private Event(String shortName, String description) {
		this.shortName = shortName;
		this.description = description;
	}

	public String getShortName() {
		return shortName;
	}

	public String getDescription() {
		return description;
	}
	
}

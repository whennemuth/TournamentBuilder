package com.warren.tournament.rest.util;

import com.warren.tournament.enumerator.Event;

public class Message {
	private String text;
	private String miscellaneous;
	private Event event;
	
	public Message(Event event) {
		this.event = event;
	}
	
	public Message(String text) {
		this.event = event;
	}
	
	public Message(Event event, String text) {
		this.event = event;
		this.text = text;
	}

	public String getText() {
		if(text == null && event != null)
			return event.getDescription();
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getMiscellaneous() {
		return miscellaneous;
	}

	public void setMiscellaneous(String miscellaneous) {
		this.miscellaneous = miscellaneous;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result
				+ ((miscellaneous == null) ? 0 : miscellaneous.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (event != other.event)
			return false;
		if (miscellaneous == null) {
			if (other.miscellaneous != null)
				return false;
		} else if (!miscellaneous.equals(other.miscellaneous))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	
}

package com.warren.tournament.rest.util;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.warren.tournament.enumerator.Event;
import com.warren.tournament.service.TournamentParms;
import com.warren.tournament.util.Utils;

/**
 * Regardless of the outcome of a request to a REST endpoint, a standard json object should be returned.
 * This includes circumstances where an exception has been thrown. This avoids the REST endpoint returning
 * the standard HTML 500 page.
 * Also this ServiceRequest class will contain specific messages and status codes.
 * 
 * @author Warren
 *
 */
public class ServiceResponse {
	public static final int FAIL_THRESHOLD = 203; // Any HttpResponse >= this value is not "success"

	private Boolean success;
	private Response.Status status;
	private Set<Message> messages = new HashSet<Message>();
	private Object data;
	private Throwable throwable;
	
	public ServiceResponse() { }
	
	public ServiceResponse(Object data) {
		this.data = data;
	}

	public static Response getSuccessInstance(Object data) {
		ServiceResponse sr = new ServiceResponse(data);
		sr.status = Response.Status.OK;
		sr.messages.add(new Message(Event.SUCCESS));
		return Response.status(sr.status).entity(sr).build();
	}
	
	public static Response getErrorInstance(Throwable throwable) {
		ServiceResponse sr = new ServiceResponse();
		sr.setThrowable(throwable);
		sr.status = Response.Status.INTERNAL_SERVER_ERROR;
		sr.messages.add(new Message(Event.UNKNOWN_ERROR));
		return Response.status(sr.status).entity(sr).build();
	}
	
	public Boolean getSuccess() {
		if(success == null) {
			if(status == null)
				return false;
			else
				return status.getStatusCode() < FAIL_THRESHOLD;
		}
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public Response.Status getStatus() {
		return status;
	}
	public void setStatus(Response.Status status) {
		this.status = status;
	}
	public Set<Message> getMessages() {
		return messages;
	}
	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}
	public void addMessage(Message message) {
		this.messages.add(message);
	}
	public Object getData() {
		if(data == null) 
			return Utils.stackTraceToString(throwable);
		else if(throwable == null)
			return data;
		else
			return new DataWithException(data, throwable);
	}
	public void setData(Object data) {
		this.data = data;
	}
	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
	
	/**
	 * Simple class to combine a data object and a stacktrace
	 * @author Warren
	 *
	 */
	public static class DataWithException {
		private Object data;
		private String stacktrace;		
		@SuppressWarnings("unused")
		private DataWithException() { /* Restrict no-args constructor */ }
		public DataWithException(Object data, Throwable throwable) {
			this.data = data;
			this.stacktrace = Utils.stackTraceToString(throwable);
		}
		public Object getData() {
			return data;
		}
		public String getStackTrace() {
			return stacktrace;
		}
	}
}

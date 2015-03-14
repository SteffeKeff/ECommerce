package se.dreamteam.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public final class BadMessageException extends WebApplicationException
{
	private static final long serialVersionUID = 7679062210665685099L;

	public BadMessageException(String message)
	{
		super(Response.status(Status.BAD_REQUEST)
				.entity("Problem: " + message)
				.build());
	}
}
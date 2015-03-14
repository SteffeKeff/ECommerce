package se.dreamteam.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import se.dreamteam.ecommerce.exceptions.LoginException;

@Provider
public final class LoginExceptionMapper implements ExceptionMapper<LoginException>
{
	@Override
	public Response toResponse(final LoginException exception)
	{
		return Response.status(Status.UNAUTHORIZED).entity(exception.getMessage()).build();
	}
}
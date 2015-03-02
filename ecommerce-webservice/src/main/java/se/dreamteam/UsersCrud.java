package se.dreamteam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("users")
public final class UsersCrud
{
	@GET
	public final Response getUsers()
	{
		return Response.ok("Did you mean users/{id}?").build();
	}
	
	@GET
	@Path("{userId}")
	public final Response getUser(@PathParam("userId") final String userId){
		return Response.ok("userId: " + userId).build();
	}
}

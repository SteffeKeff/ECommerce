package se.dreamteam;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import se.dreamteam.model.User;

@Path("users")
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
public final class UsersCrud
{
	@GET
	public final Response getUsers()
	{
		return Response.ok("Did you mean users/{id}?").build();
	}
	
	@GET
	@Path("{userId}")
	public final Response getUser(@PathParam("userId") final String userId)
	{
		return Response.ok("userId: " + userId).build();
	}
	
	@POST
	public final Response addUser(User user)
	{
		return Response.ok(user.getUsername()).build();
	}
	
	@PUT
	public final Response updateUser(User user)
	{
		return Response.ok(user.getUsername()).build();
	}
	
	@DELETE
	public final Response deleteUser(String username)
	{
		return Response.noContent().build();
	}
}

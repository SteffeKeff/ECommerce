package se.dreamteam;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import se.dreamteam.ecommerce.ECommerceManager;
import se.dreamteam.ecommerce.repository.sqlinterface.SqlOrderInterface;
import se.dreamteam.ecommerce.repository.sqlinterface.SqlProductInterface;
import se.dreamteam.ecommerce.repository.sqlinterface.SqlUserInterface;
import se.dreamteam.model.User;

@Path("users")
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
public final class UsersCrud
{
	private SqlOrderInterface orders;
	private SqlProductInterface products;
	private SqlUserInterface users;
	
	private final ECommerceManager manager = new ECommerceManager(orders, products, users);
	
	@Context
	public UriInfo uriInfo; 
	
	@GET
	public final Response getUsers()
	{
		return Response.ok("Did you mean users/{id}?").build();
	}
	
	@GET
	@Path("{userId}")
	public final Response getUser(@PathParam("userId") final int userId)
	{
		return Response.ok(manager.getUserById(userId)).build();
	}
	
	@POST
	public final Response addUser(User user)
	{
		return Response.ok(user.getUsername()).build();
	}
	
	@PUT
	@Path("{userId}")
	public final Response updateUser(@PathParam("userId") final String UserId, User user)
	{
		return Response.ok(user.getUsername()).build();
	}
	
	@DELETE
	@Path("{userId}")
	public final Response deleteUser(@PathParam("userId") final String userId, String username)
	{
		return Response.noContent().build();
	}
}

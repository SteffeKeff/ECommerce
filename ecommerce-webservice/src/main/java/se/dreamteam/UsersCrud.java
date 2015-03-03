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
import se.dreamteam.ecommerce.repository.sqldatabase.SqlOrderRepository;
import se.dreamteam.ecommerce.repository.sqldatabase.SqlProductRepository;
import se.dreamteam.ecommerce.repository.sqldatabase.SqlUserRepository;
import se.dreamteam.model.User;

import java.net.URI;

@Path("users")
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
public final class UsersCrud
{
	private SqlOrderRepository orders = new SqlOrderRepository();
	private SqlProductRepository products = new SqlProductRepository();
	private SqlUserRepository users = new SqlUserRepository();
	
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
		User theUser = manager.getUserById(userId);
		return Response.ok(theUser).build();
	}
	
	@POST
	public final Response addUser(User user)
	{
		final URI location = uriInfo.getAbsolutePathBuilder().path(manager.createUser(user)).build();
		return Response.created(location).build();
	}
	
	@PUT
	@Path("{userId}")
	public final Response updateUser(@PathParam("userId") final String userId, User user)
	{
		return Response.ok(manager.updateUser(user)).build();
		//return Response.ok(manager.updateUser(userId, user)).build();
	}
	
	@DELETE
	@Path("{userId}")
	public final Response deleteUser(@PathParam("userId") final String username)
	{
		return Response.ok(manager.deleteUser(username)).build();
	}
}

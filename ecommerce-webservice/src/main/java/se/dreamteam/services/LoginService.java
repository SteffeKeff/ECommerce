package se.dreamteam.services;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import se.dreamteam.ecommerce.ECommerceManager;
import se.dreamteam.ecommerce.repositories.SqlUserRepository;

@Path("login")
public final class LoginService
{
	private static SqlUserRepository users = new SqlUserRepository();
	private static final ECommerceManager manager = new ECommerceManager(users);

	@GET
	public Response loginUser(@HeaderParam("username") @DefaultValue("") final String username, 
							  @HeaderParam("password") @DefaultValue("") final String password)
	{
		return Response.ok(manager.loginUser(username, password)).build();
	}
}

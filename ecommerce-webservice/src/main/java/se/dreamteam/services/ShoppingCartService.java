package se.dreamteam.services;

import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import se.dreamteam.ecommerce.ECommerceManager;
import se.dreamteam.ecommerce.repository.sqldatabase.SqlOrderRepository;
import se.dreamteam.ecommerce.repository.sqldatabase.SqlProductRepository;
import se.dreamteam.ecommerce.repository.sqldatabase.SqlShoppingcartRepository;
import se.dreamteam.ecommerce.repository.sqldatabase.SqlUserRepository;

@Path("users/{userId}/shoppingcart")
// @Consumes(MediaType.APPLICATION_JSON)
// @Produces(MediaType.APPLICATION_JSON)
public final class ShoppingCartService
{
	private SqlOrderRepository orders = new SqlOrderRepository();
	private SqlProductRepository products = new SqlProductRepository();
	private SqlUserRepository users = new SqlUserRepository();
	private SqlShoppingcartRepository shoppingcart = new SqlShoppingcartRepository();
	
	private final ECommerceManager manager = new ECommerceManager(orders, products, users,shoppingcart);
	@Context
	public UriInfo uriInfo;

	@GET
	public final Response getShoppingCart(@PathParam("userId") final String username)
	{
//		ArrayList<Integer> integers = new ArrayList<>();
//		integers.add(1001);
//		integers.add(1003);
//		integers.add(1004);
//		return Response.ok(integers).build();
		return Response.ok(manager.getShoppingCart(username)).build();
	}

	@POST
	public final Response addProductToShoppingCart(@PathParam("userId") final String username, final int productId)
	{
		 //final URI location = uriInfo.getAbsolutePathBuilder().path(manager.addProductToShoppingCart(username, productId)).build();
		 //return Response.created(location).build();
		return Response.ok(username + ", Nytt item i ShoppingCart: " + productId).build();
	}

	@DELETE
	@Path("{productId}")
	public final Response removeProductFromShoppingCart(@PathParam("userId") final String username, @PathParam("productId") final String productId)
	{
		// return Response.ok(manager.removeProductFromShoppingCart(productId,
		// username)).build();
		return Response.ok(username + ", ta bort item från ShoppingCart: " + productId).build();
	}

	@DELETE
	public final Response removeAllProductsFromShoppingCart(@PathParam("userId") final String username)
	{
		// return
		// Response.ok(manager.removeAllProductsFromShoppingCart(username)).build();
		return Response.ok(username + ", ta bort alla items från ShoppingCart").build();
	}
}

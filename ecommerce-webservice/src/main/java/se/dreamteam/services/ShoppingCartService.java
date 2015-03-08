//package se.dreamteam.services;
//
//import javax.ws.rs.DELETE;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.UriInfo;
//
//import se.dreamteam.ecommerce.ECommerceManager;
//import se.dreamteam.ecommerce.repository.sqldatabase.SqlOrderRepository;
//import se.dreamteam.ecommerce.repository.sqldatabase.SqlProductRepository;
//import se.dreamteam.ecommerce.repository.sqldatabase.SqlShoppingcartRepository;
//import se.dreamteam.ecommerce.repository.sqldatabase.SqlUserRepository;
//
//@Path("users/{userId}/shoppingcart")
//// @Consumes(MediaType.APPLICATION_JSON)
//// @Produces(MediaType.APPLICATION_JSON)
//public final class ShoppingCartService
//{
//	private SqlShoppingcartRepository shoppingcart = new SqlShoppingcartRepository();
//	private final ECommerceManager manager = new ECommerceManager(shoppingcart);
//
//	@Context
//	public UriInfo uriInfo;
//
//	@GET
//	public final Response getShoppingCart(@PathParam("userId") final String username)
//	{
//		return Response.ok(manager.getShoppingCart(username)).build();
//	}
//
//	@POST
//	public final Response addProductToShoppingCart(@PathParam("userId") final String username, final String title)
//	{
//		 manager.addProductToShoppingCart(username, title);
//		 return Response.noContent().build();
//	}
//
//	@DELETE
//	@Path("{productId}")
//	public final Response removeProductFromShoppingCart(@PathParam("userId") final String username, @PathParam("productId") final int productId)
//	{
//		manager.deleteProductFromShoppingCart(username, productId);
//		return Response.noContent().build();
//		//return Response.ok(username + ", ta bort item från ShoppingCart: " + title).build();
//	}
//
//	@DELETE
//	public final Response removeAllProductsFromShoppingCart(@PathParam("userId") final String username)
//	{
//		manager.deleteShoppingCart(username);
//		return Response.noContent().build();
//		//return Response.ok(username + ", ta bort alla items från ShoppingCart").build();
//	}
// }

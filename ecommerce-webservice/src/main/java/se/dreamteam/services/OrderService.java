package se.dreamteam.services;

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
import se.dreamteam.ecommerce.repository.sqldatabase.SqlShoppingcartRepository;
import se.dreamteam.ecommerce.repository.sqldatabase.SqlUserRepository;

@Path("users/{userId}/orders")
public class OrderService
{
	private static SqlOrderRepository orders = new SqlOrderRepository();;
	private static SqlProductRepository products = new SqlProductRepository();
	private static SqlUserRepository users = new SqlUserRepository();
	private static SqlShoppingcartRepository shoppingcart = new SqlShoppingcartRepository();
	
	private static final ECommerceManager manager = new ECommerceManager(orders, products, users,shoppingcart);
	
	@Context
	public UriInfo uriInfo; 
	
	@GET
	public Response getOrders(@PathParam("userId") final String userId){
		return Response.ok("userId: " + userId + ", orders: .....").build();
	}
	
	@GET
	@Path("{orderId}")
	public Response getOrder(@PathParam("userId") final String userId, @PathParam("orderId") final String orderId){
		return Response.ok("userId: " + userId + ", orderId: " + orderId).build();
	}
	
	@POST
	@Path("{orderId}")
	public Response createOrder(@PathParam("userId") final String userId, @PathParam("orderId") final String orderId){
		return Response.ok("userId: " + userId + ", orderId: " + orderId).build();
	}
	
	@PUT
	@Path("{orderId}")
	public Response updateOrder(@PathParam("userId") final String userId, @PathParam("orderId") final String orderId){
		return Response.ok("userId: " + userId + ", orderId: " + orderId).build();
	}
	
	@DELETE
	@Path("{orderId}")
	public Response deleteOrder(@PathParam("userId") final String userId, @PathParam("orderId") final String orderId){
		return Response.ok("userId: " + userId + ", orderId: " + orderId).build();
	}
}

package se.dreamteam.services;

import java.net.URI;
import java.util.ArrayList;

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
import se.dreamteam.ecommerce.repositories.SqlOrderRepository;
import se.dreamteam.models.Order;

@Path("users/{userId}/orders")
public class OrderService
{
	private static SqlOrderRepository orders = new SqlOrderRepository();;
	private static final ECommerceManager manager = new ECommerceManager(orders);

	@Context
	public UriInfo uriInfo;

	@GET
	public Response getOrders(@PathParam("userId") final String username)
	{
		return Response.ok(manager.getAllOrders(username)).build();
	}

	@GET
	@Path("{orderId}")
	public Response getOrder(@PathParam("userId") final String username, @PathParam("orderId") final int orderId)
	{
		return Response.ok(manager.getOrder(orderId, username)).build();
	}

	@POST
	public Response createOrder(@PathParam("userId") final String username, final ArrayList<Integer> products)
	{
		final URI location = uriInfo.getAbsolutePathBuilder().path(manager.createOrder(username, products)).build();
		return Response.created(location).build();
	}

	@PUT
	@Path("{orderId}")
	public Response updateOrder(@PathParam("userId") final String username, @PathParam("orderId") final int orderId)
	{
		manager.updateOrder(orderId, username);
		return Response.ok(manager.getOrder(orderId, username)).build();
	}

	@DELETE
	@Path("{orderId}")
	public Response deleteOrder(@PathParam("userId") final String username, @PathParam("orderId") final int orderId)
	{
		Order order = manager.getOrder(orderId, username);
		manager.removeOrder(orderId, username);
		return Response.ok(order).build();
	}
}

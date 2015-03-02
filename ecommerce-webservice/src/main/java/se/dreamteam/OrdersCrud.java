package se.dreamteam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("users/{userId}/orders")
public class OrdersCrud
{
	@GET
	public Response getOrders(@PathParam("userId") final String userId){
		return Response.ok("userId: " + userId + ", orders: .....").build();
	}
	
	@GET
	@Path("{orderId}")
	public Response getOrder(@PathParam("userId") final String userId, @PathParam("orderId") final String orderId){
		return Response.ok("userId: " + userId + ", orderId: " + orderId).build();
	}
}

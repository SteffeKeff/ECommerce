package se.dreamteam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("")
public class WebService
{
	
	@GET
	public Response getBase()
	{
		return Response.ok("baseURL").build();
	}
	
	@GET
	@Path("products")
	public Response getProducts()
	{
		return Response.ok("products").build();
	}
	
	@GET
	@Path("products/{productId}")
	public Response getProduct(@PathParam("productId") final String productId)
	{
		return Response.ok("productId: " + productId).build();
	}
	
	@GET
	@Path("users")
	public Response getUsers(){
		return Response.ok("Did you mean users/{id}? ").build();
	}
	
	@GET
	@Path("users/{userId}")
	public Response getUser(@PathParam("userId") final String userId){
		return Response.ok("userId: " + userId).build();
	}
	
	@GET
	@Path("users/{id}/orders")
	public Response getOrders(@PathParam("id") final String id){
		return Response.ok("userId: " + id + ", orders: .....").build();
	}
	
	@GET
	@Path("users/{userId}/orders/{orderId}")
	public Response getOrder(@PathParam("userId") final String userId, @PathParam("orderId") final String orderId){
		return Response.ok("userId: " + userId + ", orderId: " + orderId).build();
	}
	
}

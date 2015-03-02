package se.dreamteam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("products")
public class ProductsCrud
{
	
	@GET
	public Response getProducts()
	{
		return Response.ok("products hära!").build();
	}
	
	@GET
	@Path("{productId}")
	public Response getProduct(@PathParam("productId") final String productId)
	{
		return Response.ok("productId: " + productId).build();
	}
	
	
}

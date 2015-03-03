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

import se.dreamteam.model.Product;

@Path("products")
public class ProductsCrud
{
	
	@Context
	public UriInfo uriInfo; 
	
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
	
	@POST
	@Path("")
	public Response addProduct(Product product)
	{
		return Response.ok().build();
	}
	
	@PUT
	@Path("{productId}")
	public Response updateProduct()
	{
		return Response.ok().build();
	}
	
	@DELETE
	@Path("{productId}")
	public Response deleteProduct()
	{
		return Response.ok().build();
	}
	
}
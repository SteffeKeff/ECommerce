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
import se.dreamteam.ecommerce.repository.sqlinterface.SqlOrderInterface;
import se.dreamteam.ecommerce.repository.sqlinterface.SqlProductInterface;
import se.dreamteam.ecommerce.repository.sqlinterface.SqlUserInterface;
import se.dreamteam.model.Product;

@Path("products")
public class ProductsCrud
{
	
	private static SqlOrderInterface orders;
	private static SqlProductInterface products;
	private static SqlUserInterface users;
	
	private static final ECommerceManager manager = new ECommerceManager(orders, products, users);
	
	@Context
	public UriInfo uriInfo; 
	
	@GET
	public Response getProducts()
	{
		return Response.ok(manager.getAllProducts()).build();
	}
	
	@GET
	@Path("{productId}")
	public Response getProduct(@PathParam("productId") final int productId)
	{
		return Response.ok(manager.getProductWithId(productId)).build();
	}
	
	@POST
	@Path("")
	public Response addProduct(Product product)
	{
		return Response.ok(manager.createProduct(product)).build();
	}
	
	@PUT
	@Path("{productId}")
	public Response updateProduct(Product product)
	{
		return Response.ok(manager.updateProduct(product)).build();
	}
	
	@DELETE
	@Path("{productId}")
	public Response deleteProduct()
	{
		return Response.ok().build();
	}
	
}

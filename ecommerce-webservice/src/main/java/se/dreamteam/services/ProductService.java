package se.dreamteam.services;

import java.net.URI;

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
import se.dreamteam.ecommerce.repositories.SqlProductRepository;
import se.dreamteam.models.Product;

@Path("products")
public class ProductService
{
	private static SqlProductRepository products = new SqlProductRepository();
	private static final ECommerceManager manager = new ECommerceManager(products);

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
	public Response addProduct(Product product)
	{
		final URI location = uriInfo.getAbsolutePathBuilder().path(manager.createProduct(product)).build();
		return Response.created(location).build();
	}

	@PUT
	@Path("{productId}")
	public Response updateProduct(@PathParam("productId") final int productId, Product product)
	{
		Product updateProduct = new Product(product.getTitle(), product.getPrice(), product.getQuantity(), product.getDescription(), productId);
		return Response.ok(manager.updateProduct(updateProduct)).build();
	}

	@DELETE
	@Path("{productId}")
	public Response deleteProduct(@PathParam("productId") final int productId)
	{
		return Response.ok(manager.deleteProduct(productId)).build();
	}
}

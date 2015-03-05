package se.dreamteam.mappers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.TreeSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import se.dreamteam.model.Product;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonWriter;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class ProductsWriter implements MessageBodyWriter<TreeSet<Product>>
{
	private Gson gson;

	public ProductsWriter()
	{
		gson = new GsonBuilder().registerTypeAdapter(TreeSet.class, new ProductAdapter()).create();
	}

	// MessageBodyWriter
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(TreeSet.class);
	}

	@Override
	public long getSize(TreeSet<Product> products, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return 0;
	}

	@Override
	public void writeTo(TreeSet<Product> products, Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream)
			throws IOException, WebApplicationException
	{
		try (final JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream)))
		{
			gson.toJson(products, TreeSet.class, writer);
		}
	}

	private static final class ProductAdapter implements JsonSerializer<TreeSet<Product>>
	{

		@Override
		public JsonElement serialize(TreeSet<Product> products, Type typeOfSrc, JsonSerializationContext context)
		{
			//The Object which will be returned
			final JsonObject jsonToReturn = new JsonObject();
			//An array to hold all products
			final JsonArray jsonArrayForProducts = new JsonArray();
			
			for(Product product: products)
			{
				//An object to hold all informatio~ about the products one by one
				final JsonObject jsonObjectForProduct = new JsonObject();
				jsonObjectForProduct.add("id", new JsonPrimitive(product.getId()));
				jsonObjectForProduct.add("title", new JsonPrimitive(product.getTitle()));
				jsonObjectForProduct.add("price", new JsonPrimitive(product.getPrice()));
				jsonObjectForProduct.add("quantity", new JsonPrimitive(product.getQuantity()));
				jsonObjectForProduct.add("description", new JsonPrimitive(product.getDescription()));
				//Adding the object to the array
				jsonArrayForProducts.add(jsonObjectForProduct);
			}
			//Adding the array to the jsonReturn-object
			jsonToReturn.add("products", jsonArrayForProducts);
			
			return jsonToReturn;
		}

	}

}
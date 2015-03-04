package se.dreamteam;

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
		gson = new GsonBuilder().registerTypeAdapter(Product.class, new ProductAdapter()).create();
	}
	
	// MessageBodyWriter
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(Product.class);
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
		try(final JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream)))
		{
			gson.toJson(products, Product.class, writer);
		}
	}  
	
	private static final class ProductAdapter implements JsonSerializer<TreeSet<Product>>
	{

		@Override
		public JsonElement serialize(TreeSet<Product> products, Type typeOfSrc, JsonSerializationContext context)
		{
			final JsonObject productJson = new JsonObject();
			
			//HashMap<String, Product> theProducts = new HashMap<>(); 
			
			for(Product product: products){
				productJson.add("title", new JsonPrimitive(product.getTitle()));
				productJson.add("price", new JsonPrimitive(product.getPrice()));
				productJson.add("quantity", new JsonPrimitive(product.getQuantity()));
				productJson.add("description", new JsonPrimitive(product.getDescription()));
			}

			return productJson;
		}
		
	}

}
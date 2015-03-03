package se.dreamteam;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import se.dreamteam.model.Product;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonWriter;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class ProductMapper implements MessageBodyReader<Product>, MessageBodyWriter<Product>
{
	private Gson gson;
	
	public ProductMapper()
	{
		gson = new GsonBuilder().registerTypeAdapter(Product.class, new ProductAdapter()).create();
	}
	
	// MessageBodyReader
	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(Product.class);
	}

	@Override
	public Product readFrom(Class<Product> type, Type genericType, Annotation[] annotations, 
						MediaType mediaType, MultivaluedMap<String, String> httpHeaders, 
						InputStream entityStream) throws IOException,
			WebApplicationException
	{
		final Product product = gson.fromJson(new InputStreamReader(entityStream), Product.class);
		
		return product;
	}
	
	// MessageBodyWriter
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(Product.class);
	}

	@Override
	public long getSize(Product t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return 0;
	}

	@Override
	public void writeTo(Product product, Class<?> type, Type genericType, Annotation[] annotations, 
						MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, 
						OutputStream entityStream)
			throws IOException, WebApplicationException
	{
		try(final JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream)))
		{
			gson.toJson(product, Product.class, writer);
		}
	}  
	
	private static final class ProductAdapter implements JsonDeserializer<Product>, JsonSerializer<Product>
	{
		@Override
		public Product deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			final JsonObject productJson = json.getAsJsonObject();
			final String title = productJson.get("title").getAsString();
			final int price = productJson.get("price").getAsInt();
			final int quantity = productJson.get("quantity").getAsInt();
			final String description = productJson.get("description").getAsString();
			
			return new Product(title, price, quantity, description);
		}

		@Override
		public JsonElement serialize(Product product, Type typeOfSrc, JsonSerializationContext context)
		{
			final JsonObject productJson = new JsonObject();
			productJson.add("title", new JsonPrimitive(product.getTitle()));
			productJson.add("price", new JsonPrimitive(product.getPrice()));
			productJson.add("quantity", new JsonPrimitive(product.getQuantity()));
			productJson.add("description", new JsonPrimitive(product.getDescription()));

			return productJson;
		}
		
	}	

}
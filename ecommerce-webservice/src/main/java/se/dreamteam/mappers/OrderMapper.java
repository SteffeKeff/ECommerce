package se.dreamteam.mappers;

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

import se.dreamteam.models.Order;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
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
public final class OrderMapper implements MessageBodyReader<Order>, MessageBodyWriter<Order>
{
	private Gson gson;

	public OrderMapper()
	{
		gson = new GsonBuilder().registerTypeAdapter(Order.class, new OrderAdapter()).create();
	}

	// MessageBodyReader
	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(Order.class);
	}

	@Override
	public Order readFrom(Class<Order> type, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
			InputStream entityStream) throws IOException,
			WebApplicationException
	{
		final Order order = gson.fromJson(new InputStreamReader(entityStream), Order.class);

		return order;
	}

	// MessageBodyWriter
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(Order.class);
	}

	@Override
	public long getSize(Order t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return 0;
	}

	@Override
	public void writeTo(Order order, Class<?> type, Type genericType, Annotation[] annotations,
						MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
						OutputStream entityStream)
						throws IOException, WebApplicationException
	{
		try(final JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream)))
		{
			gson.toJson(order, Order.class, writer);
		}
	}

	private static final class OrderAdapter implements JsonDeserializer<Order>, JsonSerializer<Order>
	{
		@Override
		public Order deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			// will never consume an order!
			return null;
		}

		@Override
		public JsonElement serialize(Order order, Type typeOfSrc, JsonSerializationContext context)
		{
			// The Object which will be returned
			final JsonObject orderJson = new JsonObject();
			orderJson.add("id", new JsonPrimitive(order.getId()));
			orderJson.add("date", new JsonPrimitive(order.getTimestamp().toString()));
			orderJson.add("shipped", new JsonPrimitive(order.isShipped()));

			// An array to hold all products in the order
			final JsonArray jsonArrayForProducts = new JsonArray();

			for(Integer integer : order.getOrderedProducts())
			{
				jsonArrayForProducts.add(new JsonPrimitive(integer));
			}

			orderJson.add("products", jsonArrayForProducts);
			return orderJson;
		}
	}
}
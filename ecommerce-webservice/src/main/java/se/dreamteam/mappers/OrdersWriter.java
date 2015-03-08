package se.dreamteam.mappers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import se.dreamteam.models.Order;

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
public final class OrdersWriter implements MessageBodyWriter<HashSet<Order>>
{
	private Gson gson;

	public OrdersWriter()
	{
		gson = new GsonBuilder().registerTypeAdapter(HashSet.class, new OrderAdapter()).create();
	}

	// MessageBodyWriter
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(HashSet.class);
	}

	@Override
	public long getSize(HashSet<Order> products, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return 0;
	}

	@Override
	public void writeTo(HashSet<Order> products, Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream)
			throws IOException, WebApplicationException
	{
		try (final JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream)))
		{
			gson.toJson(products, HashSet.class, writer);
		}
	}

	private static final class OrderAdapter implements JsonSerializer<HashSet<Order>>
	{

		@Override
		public JsonElement serialize(HashSet<Order> orders, Type typeOfSrc, JsonSerializationContext context)
		{
			// The Object which will be returned
			final JsonObject jsonToReturn = new JsonObject();
			// An array to hold all products
			final JsonArray jsonArrayForOrders = new JsonArray();
			JsonArray jsonArrayForProducts;
			JsonObject jsonObjectForOrder;

			for (Order order : orders)
			{
				// An object to hold all informatio~ about the products one by
				// one
				jsonObjectForOrder = new JsonObject();
				jsonObjectForOrder.add("id", new JsonPrimitive(order.getId()));
				jsonObjectForOrder.add("date", new JsonPrimitive(order.getTimestamp().toString()));
				jsonObjectForOrder.add("shipped", new JsonPrimitive(order.isShipped()));
				jsonArrayForProducts = new JsonArray();
				for (Integer integer : order.getOrderedProducts())
				{
					jsonArrayForProducts.add(new JsonPrimitive(integer));
				}
				jsonObjectForOrder.add("products", jsonArrayForProducts);
				jsonArrayForOrders.add(jsonObjectForOrder);
			}
			// Adding the array to the jsonReturn-object
			jsonToReturn.add("orders", jsonArrayForOrders);

			return jsonToReturn;
		}

	}

}
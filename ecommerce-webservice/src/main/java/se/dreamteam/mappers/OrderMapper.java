package se.dreamteam.mappers;
//package se.dreamteam;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Type;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.Produces;
//import javax.ws.rs.WebApplicationException;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.MultivaluedMap;
//import javax.ws.rs.ext.MessageBodyReader;
//import javax.ws.rs.ext.MessageBodyWriter;
//import javax.ws.rs.ext.Provider;
//
//import se.dreamteam.model.Order;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonDeserializationContext;
//import com.google.gson.JsonDeserializer;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParseException;
//import com.google.gson.JsonPrimitive;
//import com.google.gson.JsonSerializationContext;
//import com.google.gson.JsonSerializer;
//import com.google.gson.stream.JsonWriter;
//
//@Provider
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
//public final class OrderMapper implements MessageBodyReader<Order>, MessageBodyWriter<Order>
//{
//	private Gson gson;
//	
//	public OrderMapper()
//	{
//		gson = new GsonBuilder().registerTypeAdapter(Order.class, new OrderAdapter()).create();
//	}
//	
//	// MessageBodyReader
//	@Override
//	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
//	{
//		return type.isAssignableFrom(Order.class);
//	}
//
//	@Override
//	public Order readFrom(Class<Order> type, Type genericType, Annotation[] annotations, 
//						MediaType mediaType, MultivaluedMap<String, String> httpHeaders, 
//						InputStream entityStream) throws IOException,
//			WebApplicationException
//	{
//		final Order order = gson.fromJson(new InputStreamReader(entityStream), Order.class);
//		
//		return order;
//	}
//	
//	// MessageBodyWriter
//	@Override
//	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
//	{
//		return type.isAssignableFrom(Order.class);
//	}
//
//	@Override
//	public long getSize(Order t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
//	{
//		return 0;
//	}
//
//	@Override
//	public void writeTo(Order order, Class<?> type, Type genericType, Annotation[] annotations, 
//						MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, 
//						OutputStream entityStream)
//			throws IOException, WebApplicationException
//	{
//		try(final JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream)))
//		{
//			gson.toJson(order, Order.class, writer);
//		}
//	}  
//	
//	private static final class OrderAdapter implements JsonDeserializer<Order>, JsonSerializer<Order>
//	{
//		@Override
//		public Order deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
//		{
//			final JsonObject orderJson = json.getAsJsonObject();
//			final String title = orderJson.get("title").getAsString();
//			final int price = orderJson.get("price").getAsInt();
//			final int quantity = orderJson.get("quantity").getAsInt();
//			final String description = orderJson.get("description").getAsString();
//			return new Order(date, isShipped)
//			
//			return new Order(title, price, quantity, description);
//		}
//
//		@Override
//		public JsonElement serialize(Order order, Type typeOfSrc, JsonSerializationContext context)
//		{
//			final JsonObject orderJson = new JsonObject();
//			orderJson.add("title", new JsonPrimitive(order.getTitle()));
//			orderJson.add("price", new JsonPrimitive(order.getPrice()));
//			orderJson.add("quantity", new JsonPrimitive(order.getQuantity()));
//			orderJson.add("description", new JsonPrimitive(order.getDescription()));
//
//			return orderJson;
//		}
//		
//	}	
//
//}
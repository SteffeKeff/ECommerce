package se.dreamteam.mappers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

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
public final class IntegerWriter implements MessageBodyWriter<ArrayList<Integer>>
{
	private Gson gson;

	public IntegerWriter()
	{
		gson = new GsonBuilder().registerTypeAdapter(ArrayList.class, new ProductIdsAdapter()).create();
	}

	// MessageBodyWriter
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(ArrayList.class);
	}

	@Override
	public long getSize(ArrayList<Integer> integers, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return 0;
	}

	@Override
	public void writeTo(ArrayList<Integer> products, Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream)
			throws IOException, WebApplicationException
	{
		try(final JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream)))
		{
			gson.toJson(products, ArrayList.class, writer);
		}
	}

	private static final class ProductIdsAdapter implements JsonSerializer<ArrayList<Integer>>
	{
		@Override
		public JsonElement serialize(ArrayList<Integer> productIds, Type typeOfSrc, JsonSerializationContext context)
		{
			// The Object which will be returned
			final JsonObject jsonToReturn = new JsonObject();
			// An array to hold all productsIds
			final JsonArray jsonArrayForIntegers = new JsonArray();

			for (Integer integer : productIds)
			{
				jsonArrayForIntegers.add(new JsonPrimitive(Integer.toString(integer)));
			}
			// Adding the array to the jsonReturn-object
			jsonToReturn.add("productIds", jsonArrayForIntegers);

			return jsonToReturn;
		}
	}
}
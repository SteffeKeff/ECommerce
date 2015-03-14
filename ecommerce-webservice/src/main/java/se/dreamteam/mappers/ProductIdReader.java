package se.dreamteam.mappers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import se.dreamteam.exceptions.BadMessageException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class ProductIdReader implements MessageBodyReader<ArrayList<Integer>>
{
	private Gson gson;

	public ProductIdReader()
	{
		gson = new GsonBuilder().registerTypeAdapter(ArrayList.class, new ProductIdsAdapter()).create();
	}

	// MessageBodyReader
	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(ArrayList.class);
	}

	@Override
	public ArrayList<Integer> readFrom(Class<ArrayList<Integer>> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException
	{
		@SuppressWarnings("unchecked") final ArrayList<Integer> products = gson.fromJson(new InputStreamReader(entityStream), ArrayList.class);

		return products;
	}
	
	private static final class ProductIdsAdapter implements JsonDeserializer<ArrayList<Integer>>
	{
		@Override
		public ArrayList<Integer> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			try
			{
				final JsonObject userJson = json.getAsJsonObject();
				ArrayList<Integer> products = new ArrayList<>();
				
				JsonArray jsonArray = userJson.get("products").getAsJsonArray();
				for(JsonElement element : jsonArray)
				{
					products.add(element.getAsInt());
				}
				return products;
			}
			catch(Exception e)
			{
				throw new BadMessageException("Bad json");
			}
		}
	}
}
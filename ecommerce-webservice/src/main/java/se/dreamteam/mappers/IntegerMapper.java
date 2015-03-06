package se.dreamteam.mappers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import se.dreamteam.exceptions.BadMessageException;
import se.dreamteam.model.User;

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
public final class IntegerMapper implements MessageBodyWriter<ArrayList<Integer>>, MessageBodyReader<ArrayList<Integer>>
{
	private Gson gson;

	public IntegerMapper()
	{
		gson = new GsonBuilder().registerTypeAdapter(ArrayList.class, new ProductAdapter()).create();
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
		return integers.size();
	}

	@Override
	public void writeTo(ArrayList<Integer> products, Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream)
			throws IOException, WebApplicationException
	{
		try (final JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream)))
		{
			gson.toJson(products, ArrayList.class, writer);
		}
	}

	private static final class ProductAdapter implements JsonSerializer<ArrayList<Integer>>, JsonDeserializer<ArrayList<Integer>>
	{

		@Override
		public JsonElement serialize(ArrayList<Integer> productIds, Type typeOfSrc, JsonSerializationContext context)
		{
			//The Object which will be returned
			final JsonObject jsonToReturn = new JsonObject();
			//An array to hold all products
			final JsonArray jsonArrayForIntegers = new JsonArray();
			
			for(Integer integer: productIds)
			{
				jsonArrayForIntegers.add(new JsonPrimitive(Integer.toString(integer)));
			}
			//Adding the array to the jsonReturn-object
			jsonToReturn.add("productIds", jsonArrayForIntegers);
			
			return jsonToReturn;
		}

		@Override
		public ArrayList<Integer> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			final JsonObject userJson = json.getAsJsonObject();
			try{
				ArrayList<Integer> products = new ArrayList<>();
				
				JsonArray jsonArray = userJson.get("products").getAsJsonArray();
				if(jsonArray == null || jsonArray.size() == 0){
					throw new BadMessageException("No products!");
				}else{
					for(JsonElement element: jsonArray)
					{
						products.add(element.getAsInt());
					}
					return products;
				}
			}catch(Exception e){
				throw new BadMessageException("Very bad json");
			}
		}

	}

	
	//MessageBodyReader
	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(ArrayList.class);
	}

	@Override
	public ArrayList<Integer> readFrom(Class<ArrayList<Integer>> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException
	{
		final ArrayList<Integer> products = gson.fromJson(new InputStreamReader(entityStream), ArrayList.class);
		
		return products;
	}

}
package se.dreamteam.services;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

@Path("")
public final class WebService
{
	private static final Gson gson = new Gson();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public final Response getRoot()
	{
		ArrayList<HashMap<String, String>> links = new ArrayList<>();
		HashMap<String, String> products = new HashMap<>();
		HashMap<String, String> users = new HashMap<>();
		products.put("href", "/webshop/products");
		products.put("action", "get");
		users.put("href", "/webshop/users/{id}");
		users.put("action", "get");
		links.add(products);
		links.add(users);
		String json = gson.toJson(links);
		return Response.ok(json).build();
	}

}

package se.dreamteam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("serv")
public class WebService
{
	@GET
	public String hej(){
		return "test";
	}
}

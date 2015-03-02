package se.dreamteam.model;

public final class User
{

	private final int id;
	private final String username;
	private final String password;


	public User(int id, String username, String password)
	{
		this.username = username;
		this.password = password;
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

}

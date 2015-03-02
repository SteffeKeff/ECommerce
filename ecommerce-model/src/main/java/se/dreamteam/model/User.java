package se.dreamteam.model;

public final class User
{

	private int id;
	private String username;
	private String password;

	public User(String username, String password)
	{
		this.username = username;
		this.password = password;
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

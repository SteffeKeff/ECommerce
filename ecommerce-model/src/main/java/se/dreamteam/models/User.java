package se.dreamteam.models;

public final class User
{

	public static final int EMPTY_ID = -1;
	
	private final int id;
	private final String username;
	private final String password;


	public User(int id, String username, String password)
	{
		this.username = username;
		this.password = password;
		this.id = id;
	}
	public User(String username, String password)
	{
		this(EMPTY_ID,username, password);
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

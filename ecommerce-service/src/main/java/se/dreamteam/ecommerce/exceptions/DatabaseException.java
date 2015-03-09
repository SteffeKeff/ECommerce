package se.dreamteam.ecommerce.exceptions;

public class DatabaseException extends RuntimeException
{
	private static final long serialVersionUID = 4285178260698854882L;
	
	public DatabaseException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public DatabaseException(String message)
	{
		super(message);
	}
}

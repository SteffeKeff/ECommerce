package se.dreamteam.ecommerce.exceptions;

public class LoginException extends RuntimeException
{

	private static final long serialVersionUID = -6930222117610914245L;

	public LoginException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public LoginException(String message)
	{
		super(message);
	}
}

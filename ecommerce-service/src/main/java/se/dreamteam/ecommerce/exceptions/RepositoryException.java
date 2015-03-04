package se.dreamteam.ecommerce.exceptions;

public class RepositoryException extends RuntimeException
{
	private static final long serialVersionUID = 3325256658018581461L;

	public RepositoryException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public RepositoryException(String message)
	{
		super(message);
	}
}

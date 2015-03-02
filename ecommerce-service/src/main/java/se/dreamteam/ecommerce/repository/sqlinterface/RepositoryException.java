package se.dreamteam.ecommerce.repository.sqlinterface;

public class RepositoryException extends Exception
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

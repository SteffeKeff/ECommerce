package se.dreamteam.exceptions;

public class MessageStorageException extends RuntimeException
{
	private static final long serialVersionUID = 8847760671695985110L;

	public MessageStorageException(String message)
	{
		super(message);
	}
}
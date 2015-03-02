package se.dreamteam.model;

public final class Order
{
	private int id;
	private final String date;
	private final boolean isShipped;

	public Order(String date, boolean isShipped)
	{
		this.date = date;
		this.isShipped = isShipped;
	}

	public int getId()
	{
		return id;
	}

	public String getDate()
	{
		return date;
	}

	public boolean isShipped()
	{
		return isShipped;
	}
}
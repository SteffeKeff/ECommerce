package se.dreamteam.model;

public final class Order
{
	private int id;
	private String date;
	private boolean isShipped;

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
package se.dreamteam.model;

public final class Product
{
	private int id;
	private final String title;
	private final int price;
	private final int quantity;
	private final String description;

	public Product(String title, int price, int quantity, String description)
	{
		this.title = title;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
	}

	public int getId()
	{
		return id;
	}

	public String getTitle()
	{
		return title;
	}

	public int getPrice()
	{
		return price;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public String getDescription()
	{
		return description;
	}
}

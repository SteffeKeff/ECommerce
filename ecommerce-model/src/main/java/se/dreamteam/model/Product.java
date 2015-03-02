package se.dreamteam.model;

public final class Product
{
	private int productId;
	private String title;
	private int price;
	private int quantity;
	private String description;

	public Product(String title, int price, int quantity, String description, int productId)//fix en till konstuktor utan id
	{
		this.title = title;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
		this.productId = productId;
	}

	public int getId()
	{
		return productId;
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

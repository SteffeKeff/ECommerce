package se.dreamteam.model;

public final class Product
{
<<<<<<< HEAD
	private int productId;
	private String title;
	private int price;
	private int quantity;
	private String description;
=======
	private int id;
	private final String title;
	private final int price;
	private final int quantity;
	private final String description;
>>>>>>> 3f684954850ccd9e82858e833d8360971a4d580c

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

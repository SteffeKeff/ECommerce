package se.dreamteam.models;

public final class Product implements Comparable<Object>
{
	private static final int EMPTY_PRODUCT_ID = -1;
	private final int productId;
	private final String title;
	private final int price;
	private final int quantity;
	private final String description;

	public Product(String title, int price, int quantity, String description, int productId)
	{
		this.title = title;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
		this.productId = productId;
	}

	public Product(String title, int price, int quantity, String description)
	{
		this(title, price, quantity, description, EMPTY_PRODUCT_ID);
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

	public int compareTo(Object obj)
	{
		return this.title.compareTo( ((Product) obj).getTitle() );
	}
}
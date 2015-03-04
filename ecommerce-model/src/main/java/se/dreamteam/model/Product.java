package se.dreamteam.model;

public final class Product
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

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Product other = (Product) obj;
		if (title == null)
		{
			if (other.title != null) return false;
		}
		else if (!title.equals(other.title)) return false;
		return true;
	}
	
	
	
}
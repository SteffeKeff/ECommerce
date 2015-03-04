package se.dreamteam.model;

import java.util.TreeSet;

public final class Order
{
	private static final int EMPTY_ORDER_ID = -1;
	private int orderId;
	private final String date;
	private final boolean isShipped;
	TreeSet<Product> orderProducts;

	public Order(String date, boolean isShipped, int orderId, TreeSet<Product> orderProducts)
	{
		this.date = date;
		this.isShipped = isShipped;
		this.orderId = orderId;
		this.orderProducts = orderProducts;
	}
	
	public Order(String date, boolean isShipped, TreeSet<Product> orderProducts)
	{
		this(date, isShipped, EMPTY_ORDER_ID, orderProducts);
	}
	
	public Order(String date, boolean isShipped, int orderId)
	{
		this(date, isShipped, orderId, new TreeSet<Product>());
	}

	public int getId()
	{
		return orderId;
	}

	public String getDate()
	{
		return date;
	}

	public boolean isShipped()
	{
		return isShipped;
	}
	
	public TreeSet<Product> getOrderedProducts()
	{
		return orderProducts;
	}
}
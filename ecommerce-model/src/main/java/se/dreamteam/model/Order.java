package se.dreamteam.model;

import java.sql.Timestamp;
import java.util.TreeSet;

public final class Order
{
	private static final int EMPTY_ORDER_ID = -1;
	private int orderId;
	private final Timestamp date;
	private final boolean isShipped;
	TreeSet<Integer> orderProducts;

	public Order(Timestamp date, boolean isShipped, int orderId, TreeSet<Integer> orderProducts)
	{
		this.date = date;
		this.isShipped = isShipped;
		this.orderId = orderId;
		this.orderProducts = orderProducts;
	}
	
	public Order(Timestamp date, boolean isShipped, TreeSet<Integer> orderProducts)
	{
		this(date, isShipped, EMPTY_ORDER_ID, orderProducts);
	}
	
	public Order(Timestamp date, boolean isShipped, int orderId)
	{
		this(date, isShipped, orderId, new TreeSet<Integer>());
	}

	public int getId()
	{
		return orderId;
	}

	public Timestamp getTimestamp()
	{
		return date;
	}

	public boolean isShipped()
	{
		return isShipped;
	}
	
	public TreeSet<Integer> getOrderedProducts()
	{
		return orderProducts;
	}
}
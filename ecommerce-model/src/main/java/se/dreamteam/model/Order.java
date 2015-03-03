package se.dreamteam.model;

import java.util.Date;

public final class Order
{
	private int id;
	private final String date;
	private final Product products;
	private final boolean isShipped;
	
	public Order(int id, String date,, Product products, boolean isShipped)
	{
		this.id = id;
		this.products = products;
		this.isShipped = isShipped;
	}
	
	public Order(){
		
	}
	
	
}
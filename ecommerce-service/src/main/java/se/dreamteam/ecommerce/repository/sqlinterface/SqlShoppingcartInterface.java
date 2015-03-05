package se.dreamteam.ecommerce.repository.sqlinterface;

import java.util.ArrayList;

public interface SqlShoppingcartInterface
{
	public ArrayList<Integer> getShoppingcart(String username);
	
	public int addProductToShoppingcart(String username, String title);
	
	public String deleteShoppingcart(String username);
	
	public String removeProduct(String username, int productId);
}
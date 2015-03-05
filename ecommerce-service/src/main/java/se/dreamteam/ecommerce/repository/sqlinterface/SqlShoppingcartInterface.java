package se.dreamteam.ecommerce.repository.sqlinterface;

import java.util.ArrayList;
import java.util.Map;

public interface SqlShoppingcartInterface
{
	public ArrayList<Integer> getShoppingcart(String username);
	
	public Map<String,Integer> addProductToShoppingcart(String username, int productId);
	
	public String deleteShoppingcart(String username);
	
	public String removeProduct(String username, int productId);
}
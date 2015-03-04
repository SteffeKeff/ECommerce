package se.dreamteam.ecommerce.repository.sqlinterface;

import java.util.Map;
import java.util.TreeSet;

public interface SqlShoppingcartInterface
{
	public TreeSet<Integer> getShoppingcart(String username);
	
	public Map<String,Integer> addProductToShoppingcart(String username, int productId);
	
	public String deleteShoppingcart(String username);
	
	public String removeProduct(String username, int productId);
}
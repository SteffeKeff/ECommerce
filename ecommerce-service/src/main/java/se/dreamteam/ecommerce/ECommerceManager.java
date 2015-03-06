package se.dreamteam.ecommerce;

import java.util.ArrayList;
import java.util.TreeSet;

import se.dreamteam.ecommerce.exceptions.RepositoryException;
import se.dreamteam.ecommerce.repository.sqlinterface.SqlOrderInterface;
import se.dreamteam.ecommerce.repository.sqlinterface.SqlProductInterface;
import se.dreamteam.ecommerce.repository.sqlinterface.SqlShoppingcartInterface;
import se.dreamteam.ecommerce.repository.sqlinterface.SqlUserInterface;
import se.dreamteam.model.Order;
import se.dreamteam.model.Product;
import se.dreamteam.model.User;

public final class ECommerceManager
{
	SqlOrderInterface orders;
	SqlProductInterface products;
	SqlUserInterface users;
	SqlShoppingcartInterface shoppingcart;
	
	public ECommerceManager(SqlOrderInterface orders, SqlProductInterface products, SqlUserInterface users, SqlShoppingcartInterface shoppingcart)
	{
		this.orders = orders;
		this.products = products;
		this.users = users;
		this.shoppingcart = shoppingcart;
	}

	// Users
	public String createUser(User user) throws RepositoryException
	{
		return users.createUser(user);
	}

	public User updateUser(String username, User user) throws RepositoryException
	{
		return users.updateUser(username, user);
	}

	public User getUserByUsername(String username) throws RepositoryException
	{
		return users.getUserByUsername(username);
	}

	public User deleteUser(String username) throws RepositoryException
	{
		return users.deleteUser(username);
	}

	// Products
	public TreeSet<Product> getAllProducts() throws RepositoryException
	{
		return products.getAllProducts();
	}

	public Product getProductWithId(int id) throws RepositoryException
	{
		return products.getProductWithId(id);

	}

	public String createProduct(Product product) throws RepositoryException
	{
		return products.createProduct(product);
	}

	public Product updateProduct(Product product) throws RepositoryException
	{
		return products.updateProduct(product);
	}

	public Product deleteProduct(int id) throws RepositoryException
	{
		return products.deleteProduct(id);
	}

	// Orders
	public String createOrder(String username, ArrayList<Integer> products) throws RepositoryException
	{
		return orders.createOrder(username, products);
	}

	public TreeSet<Order> getAllOrders(String username) throws RepositoryException
	{
		return orders.getAllOrders(username);
	}

	public Order getOrder(int orderId, String username) throws RepositoryException
	{
		return orders.getOrder(orderId, username);
	}
//
//	public Order updateOrder(Order order) throws RepositoryException
//	{
//		return orders.updateOrder(order);
//	}
//
//	public Order removeOrder(Order order) throws RepositoryException
//	{
//		return orders.removeOrder(order);
//	}
	
	//shoppingcart
	
	public ArrayList<Integer> getShoppingCart(String username) throws RepositoryException
	{
		return shoppingcart.getShoppingcart(username);
	}
	
	public int addProductToShoppingCart(String username, String title) throws RepositoryException
	{
		return shoppingcart.addProductToShoppingcart(username, title);
	}
	
	public String deleteShoppingCart(String username) throws RepositoryException
	{
		return shoppingcart.deleteShoppingcart(username);
	}
	
	public String deleteProductFromShoppingCart(String username, int productId) throws RepositoryException
	{
		return shoppingcart.removeProduct(username, productId);
	}
}

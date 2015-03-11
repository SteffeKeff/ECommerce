package se.dreamteam.ecommerce;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

import se.dreamteam.ecommerce.exceptions.RepositoryException;
import se.dreamteam.ecommerce.interfaces.SqlOrderInterface;
import se.dreamteam.ecommerce.interfaces.SqlProductInterface;
import se.dreamteam.ecommerce.interfaces.SqlUserInterface;
import se.dreamteam.models.Order;
import se.dreamteam.models.Product;
import se.dreamteam.models.User;

public final class ECommerceManager
{
	private SqlUserInterface users;
	private SqlOrderInterface orders;
	private SqlProductInterface products;

	public ECommerceManager(SqlUserInterface users)
	{
		this.users = users;
	}

	public ECommerceManager(SqlOrderInterface orders)
	{
		this.orders = orders;
	}

	public ECommerceManager(SqlProductInterface products)
	{
		this.products = products;
	}
	
	//Users
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

	public HashSet<Order> getAllOrders(String username) throws RepositoryException
	{
		return orders.getAllOrders(username);
	}

	public Order getOrder(int orderId, String username) throws RepositoryException
	{
		return orders.getOrder(orderId, username);
	}

	public Order updateOrder(int orderId, String username) throws RepositoryException
	{
		return orders.updateOrder(orderId, username);
	}

	public int removeOrder(int orderId, String username) throws RepositoryException
	{
		return orders.removeOrder(orderId, username);
	}
}
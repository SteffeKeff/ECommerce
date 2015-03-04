package se.dreamteam.ecommerce;

import java.util.Map;
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
	public String createOrder(User user, Order order, TreeSet<Product> products) throws RepositoryException
	{
		return orders.createOrder(user, order, products);
	}

	public TreeSet<Order> getAllOrders(Order order) throws RepositoryException
	{
		return orders.getAllOrders(order);
	}

	public Order getOrder(Order order) throws RepositoryException
	{
		return orders.getOrder(order);
	}

	public Order updateOrder(Order order) throws RepositoryException
	{
		return orders.updateOrder(order);
	}

	public Order removeOrder(Order order) throws RepositoryException
	{
		return orders.removeOrder(order);
	}
	
	//shoppingcart
	
	public TreeSet<Integer> getShoppingCart(String username) throws RepositoryException
	{
		return shoppingcart.getShoppingcart(username);
	}
	
	public Map<String, Integer> addProductToShoppingCart(String username, int productId) throws RepositoryException
	{
		return shoppingcart.addProductToShoppingcart(username, productId);
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

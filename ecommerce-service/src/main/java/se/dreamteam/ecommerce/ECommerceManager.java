package se.dreamteam.ecommerce;

import java.util.TreeSet;

import se.dreamteam.ecommerce.repository.sqlinterface.RepositoryException;
import se.dreamteam.ecommerce.repository.sqlinterface.SqlOrderInterface;
import se.dreamteam.ecommerce.repository.sqlinterface.SqlProductInterface;
import se.dreamteam.ecommerce.repository.sqlinterface.SqlUserInterface;
import se.dreamteam.model.Order;
import se.dreamteam.model.Product;
import se.dreamteam.model.User;

public final class ECommerceManager
{
	SqlOrderInterface orders;
	SqlProductInterface products;
	SqlUserInterface users;

	public ECommerceManager(SqlOrderInterface orders, SqlProductInterface products, SqlUserInterface users)
	{
		this.orders = orders;
		this.products = products;
		this.users = users;
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

	public String deleteUser(String username) throws RepositoryException
	{
		return users.deleteUser(username);
	}

	// Products
	TreeSet<Product> getAllProducts() throws RepositoryException
	{
		return products.getAllProducts();
	}

	Product getProductWithId(int id) throws RepositoryException
	{
		return products.getProductWithId(id);

	}

	Product createProduct(Product product) throws RepositoryException
	{
		return products.createProduct(product);
	}

	Product updateProduct(Product product) throws RepositoryException
	{
		return products.updateProduct(product);
	}

	Product deleteProduct(int id) throws RepositoryException
	{
		return products.deleteProduct(id);
	}

	// Orders
	Order createOrder(User user, Order order) throws RepositoryException
	{
		return orders.createOrder(user, order);
	}

	Order getAllOrders(Order order) throws RepositoryException
	{
		return orders.getAllOrders(order);
	}

	Order getOrder(Order order) throws RepositoryException
	{
		return orders.getOrder(order);
	}

	Order updateOrder(Order order) throws RepositoryException
	{
		return orders.updateOrder(order);
	}

	Order removeOrder(Order order) throws RepositoryException
	{
		return orders.removeOrder(order);
	}

}

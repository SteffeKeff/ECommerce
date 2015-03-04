package se.dreamteam.ecommerce.repository.sqlinterface;

import se.dreamteam.ecommerce.exceptions.RepositoryException;
import se.dreamteam.model.Order;
import se.dreamteam.model.Product;
import se.dreamteam.model.User;

public interface SqlOrderInterface
{
	String createOrder(User user, Order order, Product product) throws RepositoryException;

	Order getAllOrders(Order order) throws RepositoryException;

	Order getOrder(Order order) throws RepositoryException;

	Order updateOrder(Order order) throws RepositoryException;

	Order removeOrder(Order order) throws RepositoryException;

}

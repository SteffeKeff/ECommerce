package se.dreamteam.ecommerce.repository.sqlinterface;

import se.dreamteam.model.Order;
import se.dreamteam.model.User;

public interface SqlOrderInterface
{
	Order createOrder(User user) throws RepositoryException;

	Order getAllOrders(User user) throws RepositoryException;

	Order getOrder(User user) throws RepositoryException;

	Order updateOrder(User user) throws RepositoryException;

	Order removeOrder(User user) throws RepositoryException;

}

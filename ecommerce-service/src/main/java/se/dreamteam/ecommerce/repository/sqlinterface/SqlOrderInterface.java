package se.dreamteam.ecommerce.repository.sqlinterface;

import java.util.ArrayList;
import java.util.HashSet;

import se.dreamteam.ecommerce.exceptions.RepositoryException;
import se.dreamteam.model.Order;

public interface SqlOrderInterface
{
	String createOrder(String username, ArrayList<Integer> products) throws RepositoryException;

	HashSet<Order> getAllOrders(String username) throws RepositoryException;

	Order getOrder(int orderId, String username) throws RepositoryException;

	Order updateOrder(int orderId, String username) throws RepositoryException;

	int removeOrder(int orderId, String username) throws RepositoryException;
}

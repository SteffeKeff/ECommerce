package se.dreamteam.ecommerce.repository.sqldatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import se.dreamteam.ecommerce.repository.sqlinterface.RepositoryException;
import se.dreamteam.ecommerce.repository.sqlinterface.SqlOrderInterface;
import se.dreamteam.model.Order;
import se.dreamteam.model.User;

public class SqlOrderRepository implements SqlOrderInterface
{

	private final static String CONNECTION = "jdbc:mysql://localhost:3306/dreamteam";

	@Override
	public Order createOrder(User user, Order order) throws RepositoryException
	{
		try (final Connection con = getConnection())
		{
			try (PreparedStatement stmt = con.prepareStatement("INSERT INTO dreamteam.Orders VALUES (null,null,?);"))
			{

			}

		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Order getAllOrders(Order order) throws RepositoryException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order getOrder(Order order) throws RepositoryException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order updateOrder(Order order) throws RepositoryException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order removeOrder(Order order) throws RepositoryException
	{
		// TODO Auto-generated method stub
		return null;
	}

	private Connection getConnection()
	{
		try
		{
			return DriverManager.getConnection(CONNECTION, "root", "");
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		return null;
	}

}

package se.dreamteam.ecommerce.repository.sqldatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import se.dreamteam.ecommerce.exceptions.RepositoryException;
import se.dreamteam.ecommerce.repository.sqlinterface.SqlOrderInterface;
import se.dreamteam.model.Order;
import se.dreamteam.model.Product;
import se.dreamteam.model.User;

public class SqlOrderRepository implements SqlOrderInterface
{

	private final static String CONNECTION = "jdbc:mysql://80.217.176.187:3306/dreamteam";
	private final static String USERNAME = "admin";
	private final static String PASSWORD = "dr3amt3am";

	@Override
	public String createOrder(User user, Order order, Product product) throws RepositoryException
	{
		try (final Connection con = getConnection())
		{
			try (PreparedStatement stmt = con.prepareStatement("INSERT INTO dreamteam.Orders VALUES (null,null,?);", Statement.RETURN_GENERATED_KEYS))
			{
				stmt.setInt(1, 1);
				int affectedRows = stmt.executeUpdate();
				if (affectedRows == 1)
				{
					ResultSet key = stmt.getGeneratedKeys();
					
					try (PreparedStatement secondStmt = con.prepareStatement("INSERT INTO dreamteam.UserHasOrder (userid,orderid) VALUES(null,?,?)"))
					{
						secondStmt.setInt(1, user.getId());
						secondStmt.setInt(2, key.getInt(1));
						secondStmt.executeUpdate();
						try (PreparedStatement thirdStmt = con.prepareStatement("INSERT INTO dreamteam.UserHasProducts(userid, productid) VALUES (null,?,?)"))
						{
							thirdStmt.setInt(1, user.getId());
							thirdStmt.setInt(2, product.getId());
							thirdStmt.executeUpdate();
							return Integer.toString(key.getInt(1));
						}

					}

				}

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
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
		}
		catch (SQLException | ClassNotFoundException e)
		{
			System.out.println(e);
		}
		return null;
	}

}

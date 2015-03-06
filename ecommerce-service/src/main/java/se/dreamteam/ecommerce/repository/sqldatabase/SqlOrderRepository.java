package se.dreamteam.ecommerce.repository.sqldatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.TreeSet;

import se.dreamteam.ecommerce.exceptions.RepositoryException;
import se.dreamteam.ecommerce.repository.sqlinterface.SqlOrderInterface;
import se.dreamteam.model.Order;

public class SqlOrderRepository implements SqlOrderInterface

{
	private static final String DB_URL = "jdbc:mysql://80.217.176.187:3306/dreamteam";
	private static final String USER = "admin";
	private static final String PW = "dr3amt3am";

	@Override
	public String createOrder(String username, ArrayList<Integer> products) throws RepositoryException
	{
		try (final Connection con = getConnection())
		{
			try (PreparedStatement stmt = con.prepareStatement("INSERT INTO dreamteam.Orders VALUES (null,null,?,1);", Statement.RETURN_GENERATED_KEYS))
			{
				stmt.setInt(1, 0);
				int affectedRows = stmt.executeUpdate();
				if (affectedRows == 1)
				{
					ResultSet rs = stmt.getGeneratedKeys();
					if (rs.next())
					{
						try (PreparedStatement secondStmt = con.prepareStatement("INSERT INTO dreamteam.UserHasOrder VALUES(null,?,?);"))
						{
							secondStmt.setString(1, username);
							int orderId = rs.getInt(1);
							secondStmt.setInt(2, orderId);
							secondStmt.executeUpdate();
							for (Integer integer : products)
							{
								try (PreparedStatement thirdStmt = con.prepareStatement("INSERT INTO dreamteam.OrderHasProducts VALUES (null,?,?)"))
								{
									thirdStmt.setInt(1, orderId);
									thirdStmt.setInt(2, integer);
									thirdStmt.executeUpdate();
								}
							}
							return Integer.toString(orderId);
						}
					}
					else
					{
						throw new RepositoryException("Fel när du skapar Order!");
					}
				}
			}
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Coud not create order", e);
		}
		return null;
	}

	@Override
	public TreeSet<Order> getAllOrders(String username) throws RepositoryException
	{
		TreeSet<Order> orders = new TreeSet<Order>();
		
		try(Connection con = getConnection();
			PreparedStatement stmt = con.prepareStatement(
					"SELECT Orders.id AS orderid, Orders.date, Orders.shipped, OrderHasProducts.productid, Orders.status from Orders "
					+ "left join OrderHasProducts "
					+ "on Orders.`id` = OrderHasProducts.`orderid` "
					+ "left join UserHasOrder "
					+ "on UserHasOrder.`orderid` = OrderHasProducts.`orderid` "
					+ "WHERE username = ? AND Orders.status = 1", ResultSet.TYPE_SCROLL_INSENSITIVE);)
		{
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			
			ArrayList<Integer> ordersIds = new ArrayList<Integer>();
			
			while(rs.next()){									//hämtar alla orderIdn
				if(!ordersIds.contains(rs.getInt("orderid"))){ 
					ordersIds.add(rs.getInt("orderid"));
				}
			}
			rs.beforeFirst();
			
			System.out.println(ordersIds.size());
			for (int i = 0; i < ordersIds.size(); i++) { 		//för varje order
				TreeSet<Integer> products = new TreeSet<Integer>();
				Timestamp date = null;
				Boolean isShipped = null;
				Integer orderId = null;
				
				while(rs.next()){								//lägg till product för den ordern
					System.out.println("orderId från rs: " + rs.getInt("orderid"));
					System.out.println("orderId från orderIds: " + ordersIds.get(i));
					if(ordersIds.get(i) == rs.getInt("orderid")){
						System.out.println("inne i if!");
						products.add(rs.getInt("productid"));
						date = rs.getTimestamp("date");
						isShipped = rs.getBoolean("shipped");
						orderId = rs.getInt("orderid");
					}
				}
				System.out.println("date: " + date + " isShipped: " + isShipped + " orderId: " + orderId + " products: " + products);
				orders.add(new Order(date, isShipped, orderId, products));
				rs.first();
			}
			return orders;
		}catch (SQLException e) {
			throw new RepositoryException("Could not get all orders for user: " + username, e);
		}
	}

	@Override
	public Order getOrder(int orderId, String username) throws RepositoryException
	{
		try (Connection con = getConnection())
		{

			try (PreparedStatement stmt = con.prepareStatement(
					"SELECT * FROM dreamteam.UserHasOrder as userHasOrder"
							+ " inner join dreamteam.Orders as orders "
							+ "on orders.id = userHasOrder.orderid "
							+ "inner join dreamteam.OrderHasProducts as orderHasProducts "
							+ "on orderHasProducts.orderid = orders.id "
							+ "inner join dreamteam.Users as users "
							+ "on users.username = userHasOrder.username "
							+ "AND orders.id = ? "
							+ "AND users.username = ?"
							+ "AND orders.status = 1;"))
			{
				stmt.setInt(1, orderId);
				stmt.setString(2, username);
				ResultSet rs = stmt.executeQuery();
				TreeSet<Integer> products = new TreeSet<Integer>();
				if (rs.next())
				{
					Timestamp date = rs.getTimestamp("date");
					Boolean isShipped = rs.getBoolean("shipped");
					products.add(rs.getInt("productid"));
					while (rs.next())
					{
						products.add(rs.getInt("productid"));
						date = rs.getTimestamp("date");
						isShipped = rs.getBoolean("shipped");
					}
					return new Order(date, isShipped, orderId, products);
				}
				else
				{
					throw new RepositoryException("You do not have this order!");
				}
			}
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not get order.", e);
		}
	}

	@Override
	public Order updateOrder(int orderId, String username) throws RepositoryException
	{
		try (Connection con = getConnection())
		{
			try (PreparedStatement firstStmt = con
					.prepareStatement("SELECT * from Orders left join OrderHasProducts on Orders.`id` = OrderHasProducts.`orderid` left join  UserHasOrder on UserHasOrder.`orderid` = OrderHasProducts.`orderid` WHERE username = ? AND Orders.status = 1 AND Orders.id = ?;"))
			{
				firstStmt.setString(1, username);
				firstStmt.setInt(1, orderId);
				ResultSet rs = firstStmt.executeQuery();
				if (rs.next())
				{
					try (PreparedStatement stmt = con.prepareStatement("UPDATE Orders SET shipped = 1 WHERE id = ?"))
					{
						stmt.setInt(1, orderId);
						stmt.executeUpdate();

						return new Order(rs.getTimestamp("date"), rs.getBoolean("shipped"), orderId);
					}
				}
				else
				{
					throw new RepositoryException("Could not update that order");
				}
			}
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not update order.", e);
		}
	}

	@Override
	public int removeOrder(int orderId, String username) throws RepositoryException
	{
		try (Connection con = getConnection())
		{
			try (PreparedStatement stmt = con
					.prepareStatement("UPDATE Orders SET status = 0 WHERE id = ?;"))
			{
				stmt.setInt(1, orderId);

				stmt.executeUpdate();
				return orderId;

			}catch(SQLException e){
				throw new RepositoryException("problem with statement!",e);
			}
		}
		catch (SQLException e)
		{
			throw new RepositoryException("problem with connection to database.", e);
		}
	}

	private Connection getConnection()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(DB_URL, USER, PW);
		}
		catch (SQLException | ClassNotFoundException e)
		{
			throw new RepositoryException("Could not connect to data source", e);
		}
	}
}

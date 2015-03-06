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
			try (PreparedStatement stmt = con.prepareStatement("INSERT INTO dreamteam.Orders VALUES (null,null,?);", Statement.RETURN_GENERATED_KEYS))
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public TreeSet<Order> getAllOrders(String username) throws RepositoryException
	{
		return null;
		// ArrayList<Order> ordersQueryResult = new ArrayList<Order>();
		// HashMap<Integer, Integer> orderHasProductsQueryResult = new
		// HashMap<Integer, Integer>();
		//
		// TreeSet<Order> orders = new TreeSet<Order>();
		// boolean hasResults = false;
		// int rsCount = 0;
		//
		// try(Connection con = getConnection();
		// Statement stmt = con.createStatement();)
		// {
		// hasResults = stmt.execute("SELECT * FROM dreamteam.Orders;"
		// + "SELECT * FROM dreamteam.OrderHasProducts;");
		//
		// do{
		// ResultSet rs = stmt.getResultSet();
		// rsCount++;
		//
		// while(rs.next())
		// {
		// if(rsCount == 1){ //orders table query
		// Order orderWithNoProducts = null;
		// ordersQueryResult.add(orderWithNoProducts);
		// }else if(rsCount == 2){ //orderHasProducts table query
		// orderHasProductsQueryResult.put(rs.getInt(2), rs.getInt(3));
		// }
		// }
		// rs.close();
		//
		// hasResults = stmt.getMoreResults();
		// }while(hasResults);
		//
		// }catch (SQLException e) {
		// throw new RepositoryException("Failed to get all products", e);
		// }
		//
		// HashMap<Integer, TreeSet<Integer>> ordersProductIds = new
		// HashMap<Integer, TreeSet<Integer>>();
		//
		// //mergar orders med product idn
		// for (Order orderWithoutProd : ordersQueryResult) {
		// TreeSet<Integer> orderProductIds = new TreeSet<Integer>();
		//
		// for (Map.Entry<Integer, Integer> entry :
		// orderHasProductsQueryResult.entrySet())
		// {
		// if (orderWithoutProd.getId() == entry.getKey()) { //kör om samma
		// orderId
		// orderProductIds.add(entry.getValue()); //sparar orders product idn
		// }
		// }
		// ordersProductIds.put(orderWithoutProd.getId(), orderProductIds);
		// //sparar en order med product idn i en map
		// }
		//
		// //hämtar orders fysiska produkter baserat på prodId
		// SqlProductRepository productQueries = new SqlProductRepository();
		// HashMap<Integer, TreeSet<Integer>> orderWithProducts = new
		// HashMap<Integer, TreeSet<Integer>>();
		//
		// for (Map.Entry<Integer, TreeSet<Integer>> entry :
		// ordersProductIds.entrySet()) //för varje order
		// {
		// TreeSet<Integer> orderProducts = new TreeSet<Integer>();
		//
		// for (Integer productId : entry.getValue()) { //för varje productId
		// //lägger till i orders lista
		// orderProducts.add(productQueries.getProductWithId(productId));
		// //hämtar och lägger in en product i order arr
		// }
		// orderWithProducts.put(entry.getKey(), orderProducts);
		// }
		//
		// //skapar order obj
		// for (Entry<Integer, TreeSet<Integer>> entry :
		// orderWithProducts.entrySet()) //för varje order
		// {
		// for (Order orderInfo : ordersQueryResult) {
		// if(entry.getKey() == orderInfo.getId()){
		// Order completeOrder = new Order(orderInfo.getDate(),
		// orderInfo.isShipped() , orderInfo.getId(), entry.getValue());
		// orders.add(completeOrder);
		// }
		// }
		// }
		//
		// return orders;
	}

	@Override
	public Order getOrder(int orderId, String username) throws RepositoryException
	{
		try (Connection con = getConnection())
		{
			try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM dreamteam.UserHasOrder as userHasOrder inner join dreamteam.Orders as orders on orders.id = userHasOrder.orderid inner join dreamteam.OrderHasProducts as orderHasProducts on orderHasProducts.orderid = orders.id inner join dreamteam.Users as users on users.username = userHasOrder.username AND orders.id = ? AND users.username = ?;"))
			{
				stmt.setInt(1, orderId);
				stmt.setString(2, username);
				ResultSet rs = stmt.executeQuery();
				TreeSet<Integer> products = new TreeSet<Integer>();
				if(rs.next()){
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
				}else{
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

		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not update order.", e);
		}
		return null;
	}

	@Override
	public Order removeOrder(int orderId, String username) throws RepositoryException
	{
		try (Connection con = getConnection())
		{

		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not remove order.", e);
		}
		return null;
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

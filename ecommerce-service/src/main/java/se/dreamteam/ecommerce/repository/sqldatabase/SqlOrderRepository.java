package se.dreamteam.ecommerce.repository.sqldatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

import com.sun.security.auth.SolarisPrincipal;
=======
import java.util.TreeSet;
>>>>>>> 7f05ca0bd8c447cf861941591a6fba542819d338

import se.dreamteam.ecommerce.exceptions.RepositoryException;
import se.dreamteam.ecommerce.repository.sqlinterface.SqlOrderInterface;
import se.dreamteam.model.Order;
import se.dreamteam.model.Product;
import se.dreamteam.model.User;

public class SqlOrderRepository implements SqlOrderInterface
{

	private static final String DB_URL = "jdbc:mysql://80.217.176.187:3306/dreamteam";
	private static final String USER = "admin";
	private static final String PW = "dr3amt3am";

	@Override
	public String createOrder(User user, Order order, TreeSet<Product> products) throws RepositoryException
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
						for(Product product: products){
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

		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public TreeSet<Order> getAllOrders(Order order) throws RepositoryException
	{
		//HashMap<Date, Boolean> ordersQueryResult = new HashMap<Date, Boolean>();
		ArrayList<Order> ordersQueryResult = new ArrayList<Order>();
		HashMap<Integer, Integer> orderHasProductsQueryResult = new HashMap<Integer, Integer>();
		
		TreeSet<Order> orders = new TreeSet<Order>(); 
		boolean hasResults = false;
		int rsCount = 0;
		
		try(Connection con = getConnection(); 
			Statement stmt = con.createStatement();)
		{
			hasResults = stmt.execute("SELECT * FROM dreamteam.Orders;"
									+ "SELECT * FROM dreamteam.OrderHasProducts;"
									+ "");
			
			do{
				ResultSet rs = stmt.getResultSet();
				rsCount++;
				
				while(rs.next()) 
				{
					if(rsCount == 1){ //orders table query
						Order orderWithNoProducts = new Order(rs.getDate(2), rs.getBoolean(3), rs.getInt(1));
						ordersQueryResult.add(orderWithNoProducts);
					}else if(rsCount == 2){ //orderHasProducts table query
						orderHasProductsQueryResult.put(rs.getInt(2), rs.getInt(3));
					}
		        }
				rs.close();
				
				hasResults = stmt.getMoreResults();
			}while(hasResults);
			
		}catch (SQLException e) {
			throw new RepositoryException("Failed to get all products", e);
		}
		
		HashMap<Integer, TreeSet<Integer>> ordersProductIds = new HashMap<Integer, TreeSet<Integer>>();
		
		//mergar orders med product idn
		for (Order orderWithoutProd : ordersQueryResult) {
			TreeSet<Integer> orderProductIds = new TreeSet<Integer>();
			
			for (Map.Entry<Integer, Integer> entry : orderHasProductsQueryResult.entrySet()) 
			{
			    if (orderWithoutProd.getId() == entry.getKey()) { //kör om samma orderId
			    	orderProductIds.add(entry.getValue()); //sparar orders product idn
				}
			}
			ordersProductIds.put(orderWithoutProd.getId(), orderProductIds); //sparar en order med product idn i en map
		}
		
		//hämtar orders fysiska produkter baserat på prodId		
		SqlProductRepository productQueries = new SqlProductRepository();
		HashMap<Integer, TreeSet<Product>> orderWithProducts = new HashMap<Integer, TreeSet<Product>>();
		
		for (Map.Entry<Integer, TreeSet<Integer>> entry : ordersProductIds.entrySet()) //för varje order
		{
			TreeSet<Product> orderProducts = new TreeSet<Product>(); //flytta in i for scopet
			
			for (Integer productId : entry.getValue()) { //för  varje productId //lägger till i orders lista
				 orderProducts.add(productQueries.getProductWithId(productId)); //hämtar och lägger in en product i order arr
			}
			orderWithProducts.put(entry.getKey(), orderProducts);
		}
				
		//skapar order obj
		for (Entry<Integer, TreeSet<Product>> entry : orderWithProducts.entrySet()) //för varje order
		{
			for (Order orderInfo : ordersQueryResult) {
				if(entry.getKey() == orderInfo.getId()){
					Order completeOrder = new Order(orderInfo.getDate(), orderInfo.isShipped() , orderInfo.getId(), entry.getValue());
					orders.add(completeOrder);
				}
			}
		}
		
		return orders;
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
<<<<<<< HEAD
			return DriverManager.getConnection(DB_URL, USER, PW);
=======
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
>>>>>>> 7f05ca0bd8c447cf861941591a6fba542819d338
		}
		catch (SQLException | ClassNotFoundException e)
		{
			throw new RepositoryException("Could not connect to data source", e);
		}
	}

}

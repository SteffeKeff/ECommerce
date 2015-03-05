package se.dreamteam.ecommerce.repository.sqldatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import se.dreamteam.ecommerce.exceptions.RepositoryException;
import se.dreamteam.ecommerce.repository.sqlinterface.SqlShoppingcartInterface;

public class SqlShoppingcartRepository implements SqlShoppingcartInterface
{
	private final static String CONNECTION = "jdbc:mysql://80.217.176.187:3306/dreamteam";
	private final static String USERNAME = "admin";
	private final static String PASSWORD = "dr3amt3am";

	@Override
	public ArrayList<Integer> getShoppingcart(String username)
	{
		try (final Connection con = getConnection())
		{
			try (final PreparedStatement stmt = con.prepareStatement("SELECT * FROM dreamteam.UserHasProducts WHERE username = ?"))
			{

				stmt.setString(1, username);
				ResultSet rs = stmt.executeQuery();
				ArrayList<Integer> products = new ArrayList<>();
				while (rs.next())
				{
					products.add(rs.getInt("productid"));
				}
				return products;
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			throw new RepositoryException("Could not get products", e);
		}

	}

	@Override
	public int addProductToShoppingcart(String username, String title)
	{
		try (final Connection con = getConnection())
		{
			try (final PreparedStatement veryFirstStmt = con.prepareStatement("SELECT * FROM dreamteam.Users WHERE username = ?"))
			{
				veryFirstStmt.setString(1,username);
				ResultSet rs = veryFirstStmt.executeQuery();
				if(rs.next()){
					try (final PreparedStatement firstStmt = con.prepareStatement("SELECT * FROM dreamteam.Products WHERE title = ?"))
					{
						firstStmt.setString(1, title);
						rs = firstStmt.executeQuery();
						if(rs.next()){
							int productId = rs.getInt("id");
						try (final PreparedStatement stmt = con.prepareStatement("INSERT INTO dreamteam.UserHasProducts VALUES(null, ?, ?)"))
						{
							stmt.setString(1, username);
							stmt.setInt(2, productId);
							stmt.executeUpdate();
							return productId;
						}
						}else{
							throw new RepositoryException("Product does not exist");
						}
					}
				}else{
					throw new RepositoryException("User does not exist");
				}
			}
			
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			throw new RepositoryException("Could not add product to cart", e);
		}

	}
	
	@Override
	public String removeProduct(String username, int productId)
	{
		try (final Connection con = getConnection())
		{
			try(final PreparedStatement firstStmt = con.prepareStatement("SELECT * FROM dreamteam.UserHasProducts WHERE productid = ? AND username = ?"))
			{
				firstStmt.setInt(1, productId);
				firstStmt.setString(2, username);
				ResultSet rs = firstStmt.executeQuery();
				if(rs.next()){
					try (final PreparedStatement stmt = con.prepareStatement("DELETE FROM dreamteam.UserHasProducts WHERE productid = ? AND username = ?"))
					{
						stmt.setInt(1, productId);
						stmt.setString(2, username);
						stmt.executeUpdate();
						return Integer.toString(productId);
					}
				}else{
					throw new RepositoryException("User does not exist or does not have this product");
				}
				
			}
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not delete product " + productId, e);
		}
	}

	@Override
	public String deleteShoppingcart(String username)
	{
		try (final Connection con = getConnection())
		{
//			try(final PreparedStatement firstStmt = con.prepareStatement("SELECT * FROM dreamteam.UserHasProducts WHERE username =?"))
			try (final PreparedStatement stmt = con.prepareStatement("DELETE FROM dreamteam.UserHasProducts WHERE username = ?"))
			{
				stmt.setString(1, username);
				stmt.executeUpdate();
				return username;
			}
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not delete user " + username, e);
		}

	}

	private Connection getConnection() throws SQLException
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
		}
		catch (SQLException | ClassNotFoundException e)
		{
			throw new RepositoryException("Connection problemo", e);
		}
	}
	
}
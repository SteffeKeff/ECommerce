package se.dreamteam.ecommerce.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeSet;
import java.sql.Statement;

import se.dreamteam.ecommerce.exceptions.RepositoryException;
import se.dreamteam.ecommerce.interfaces.SqlProductInterface;
import se.dreamteam.models.Product;

public class SqlProductRepository implements SqlProductInterface{
	
	private static final String DB_URL = "jdbc:mysql://80.217.176.187:3306/dreamteam";
	private static final String USER = "admin";
	private static final String PW = "dr3amt3am";
	
	@Override
	public TreeSet<Product> getAllProducts() throws RepositoryException 
	{
		TreeSet<Product> products = new TreeSet<Product>(); 
		
		try(Connection con = getConnection(); 
			Statement stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery("SELECT * FROM dreamteam.Products;");)
		{
			while(rs.next()){
				Product product = new Product(rs.getString("title"), rs.getInt("price") , rs.getInt("quantity"), rs.getString("description"), rs.getInt("id"));
				products.add(product);
			}
			return products;
			
		}catch (SQLException e) {
			throw new RepositoryException("Failed to get all products", e);
		}
	}
	
	@Override
	public Product getProductWithId(int productId) throws RepositoryException 
	{
		Product product = null;
		try(Connection con = getConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM dreamteam.Products WHERE id = ?;");) 
		{
			stmt.setInt(1, productId);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				product = new Product(rs.getString("title"), rs.getInt("price") , rs.getInt("quantity"), rs.getString("description"), rs.getInt("id"));
			}else{
				throw new RepositoryException("Product does not exist!");
			}
		}catch (SQLException e) {
			throw new RepositoryException("Failed to get product by id", e);
		}
		return product;
	}
	
	@Override
	public String createProduct(Product product) throws RepositoryException 
	{
		try (final Connection con = getConnection())
		{
			con.setAutoCommit(false);
			try (PreparedStatement stmt = con.prepareStatement("INSERT INTO dreamteam.Products VALUES (null, ?, ?, ?, ?);",
																	  Statement.RETURN_GENERATED_KEYS))
			{
				stmt.setString(1, product.getTitle());
				stmt.setInt(2, product.getPrice());
				stmt.setInt(3, product.getQuantity());
				stmt.setString(4, product.getDescription());
				int affectedRows = stmt.executeUpdate();

				if (affectedRows == 1)
				{
					ResultSet rs = stmt.getGeneratedKeys();

					if (rs.next())
					{
						int id = rs.getInt(1);
						con.commit();
						
						return Integer.toString(id);
					}
				}
			}
			catch (SQLException e)
			{
				con.rollback();
				throw new RepositoryException("Could not add product", e);
			}
			
			throw new RepositoryException("Could not add product");
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not add product", e);
		}
	}
	
	@Override
	public Product updateProduct(Product product) throws RepositoryException 
	{
		try(Connection con = getConnection();
			PreparedStatement stmt = con.prepareStatement("UPDATE dreamteam.Products SET title=?, price=?, quantity=?, description=? WHERE id = ?;"))
		{
			stmt.setString(1, product.getTitle());
			stmt.setInt(2, product.getPrice());
			stmt.setInt(3, product.getQuantity());
			stmt.setString(4, product.getDescription());
			stmt.setInt(5, product.getId());
			
			stmt.executeUpdate();
			
			return product;
			
		}catch (SQLException e) 
		{
			throw new RepositoryException("Could not update product", e);
		}
	}
	
	@Override
	public Product deleteProduct(int productId) throws RepositoryException
	{

		try (Connection con = getConnection())
		{
			try (PreparedStatement firstStmt = con.prepareStatement("SELECT * FROM dreamteam.Products WHERE id = ?"))
			{
				firstStmt.setInt(1, productId);
				ResultSet rs = firstStmt.executeQuery();
				if (rs.next())
				{
					try (PreparedStatement stmt = con.prepareStatement("DELETE FROM dreamteam.Products WHERE id = ?;"))
					{
						stmt.setInt(1, productId);
						stmt.executeUpdate();
						
						return new Product(rs.getString("title"), rs.getInt("price"), rs.getInt("quantity"), rs.getString("description"), productId);
					}
				}
				else
				{
					throw new RepositoryException("Product does not exist!");
				}
			}
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not delete product.", e);
		}

	}
	private Connection getConnection() throws RepositoryException
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

package se.dreamteam.ecommerce.repository.sqldatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import se.dreamteam.ecommerce.exceptions.RepositoryException;
import se.dreamteam.ecommerce.repository.sqlinterface.SqlUserInterface;
import se.dreamteam.model.User;

public class SqlUserRepository implements SqlUserInterface
{
	private final static String CONNECTION = "jdbc:mysql://80.217.176.187:3306/dreamteam";
	private final static String USERNAME = "admin";
	private final static String PASSWORD = "dr3amt3am";

	@Override
	public User getUserByUsername(String username) throws RepositoryException
	{

		try (final Connection con = getConnection())
		{

			try (final PreparedStatement stmt = con.prepareStatement("SELECT * FROM dreamteam.Users WHERE username = ?;"))
			{
				stmt.setString(1, username);
				ResultSet rs = stmt.executeQuery();
				rs.next();
				return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
			}

		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not find user with username: " + username, e);
		}
	}

	@Override
	public String createUser(User user) throws RepositoryException
	{
		try (final Connection con = getConnection())
		{

			try (final PreparedStatement stmt = con.prepareStatement("INSERT INTO dreamteam.Users VALUES (null,?,?);", Statement.RETURN_GENERATED_KEYS))
			{
				stmt.setString(1, user.getUsername());
				stmt.setString(2, user.getPassword());
				int affectedRows = stmt.executeUpdate();
				if (affectedRows == 1)
				{

					ResultSet key = stmt.getGeneratedKeys();

					if (key.next())
					{
						User returnedUser = new User(key.getInt(1), user.getUsername(), user.getPassword());
						return returnedUser.getUsername();
					}
				}
				throw new RepositoryException("Could not add user");
			}
		}
		catch (SQLException e)
		{

			throw new RepositoryException("Could not add user", e);
		}

	}

	@Override
	public User updateUser(String username, User user) throws RepositoryException
	{
		try (final Connection con = getConnection())
		{

			try (final PreparedStatement stmt = con.prepareStatement("UPDATE dreamteam.Users SET username = ?, password = ? WHERE username = ?"))
			{
				stmt.setString(1, user.getUsername());
				stmt.setString(2, user.getPassword());
				stmt.setString(3, username);
				stmt.executeUpdate();

				return user;
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			throw new RepositoryException("Could not update user with id " + user.getId(), e);
		}

	}

	@Override
	public String deleteUser(String username) throws RepositoryException
	{
		
		try (final Connection con = getConnection())
		{
			try (final PreparedStatement stmt = con.prepareStatement("DELETE FROM dreamteam.Users WHERE username = ?"))
			{
				stmt.setString(1, username);
				stmt.executeUpdate();
				
				return username;
			}
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not delete user: " + username, e);
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

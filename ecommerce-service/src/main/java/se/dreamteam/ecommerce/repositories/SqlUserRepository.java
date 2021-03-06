package se.dreamteam.ecommerce.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import se.dreamteam.ecommerce.exceptions.DatabaseException;
import se.dreamteam.ecommerce.exceptions.LoginException;
import se.dreamteam.ecommerce.exceptions.RepositoryException;
import se.dreamteam.ecommerce.interfaces.SqlUserInterface;
import se.dreamteam.models.User;

public final class SqlUserRepository implements SqlUserInterface
{
	
	@Override
	public User loginUser(String username, String password)
	{
		try(final Connection con = getConnection())
		{
			try(final PreparedStatement stmt = con.prepareStatement("SELECT * FROM dreamteam.Users WHERE username = ? AND password = ?;"))
			{
				stmt.setString(1, username);
				stmt.setString(2, password);
				ResultSet rs = stmt.executeQuery();
				rs.next();
				return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
			}
			catch(SQLException e)
			{
				throw new LoginException("Could not authenticate " + username + ".", e);
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseException("Problem with connection to database.", e);
		}
	}
	
	@Override
	public User getUserByUsername(String username) throws RepositoryException
	{
		try(final Connection con = getConnection())
		{
			try(final PreparedStatement stmt = con.prepareStatement("SELECT * FROM dreamteam.Users WHERE username = ?;"))
			{
				stmt.setString(1, username);
				ResultSet rs = stmt.executeQuery();
				rs.next();
				return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
			}
			catch(SQLException e)
			{
				throw new RepositoryException("Problem with finding user with username: " + username + ".", e);
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseException("Problem with connection to database.", e);
		}
	}

	@Override
	public String createUser(User user) throws RepositoryException
	{
		try(final Connection con = getConnection())
		{
			try(final PreparedStatement stmt = con.prepareStatement("INSERT INTO dreamteam.Users VALUES (null,?,?);", Statement.RETURN_GENERATED_KEYS))
			{
				stmt.setString(1, user.getUsername());
				stmt.setString(2, user.getPassword());
				int affectedRows = stmt.executeUpdate();
				if(affectedRows == 1)
				{
					ResultSet key = stmt.getGeneratedKeys();

					if(key.next())
					{
						User returnedUser = new User(key.getInt(1), user.getUsername(), user.getPassword());
						return returnedUser.getUsername();
					}
				}
				throw new RepositoryException("Problem with adding user.");
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseException("Problem with connection to database.", e);
		}
	}

	@Override
	public User updateUser(String username, User user) throws RepositoryException
	{
		try(final Connection con = getConnection())
		{
			try(final PreparedStatement stmt = con.prepareStatement("UPDATE dreamteam.Users SET username = ?, password = ? WHERE username = ?"))
			{
				stmt.setString(1, user.getUsername());
				stmt.setString(2, user.getPassword());
				stmt.setString(3, username);
				stmt.executeUpdate();

				return user;
			}
			catch(SQLException e)
			{
				throw new RepositoryException("Problem with updating user with id " + user.getId() + ".", e);
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseException("Problem with connection to database.", e);
		}
	}

	@Override
	public User deleteUser(String username) throws RepositoryException
	{
		try(final Connection con = getConnection())
		{
			try(final PreparedStatement firstStmt = con.prepareStatement("SELECT * FROM dreamteam.Users WHERE username = ?"))
			{
				firstStmt.setString(1, username);
				ResultSet rs = firstStmt.executeQuery();
				rs.next();

				try(final PreparedStatement stmt = con.prepareStatement("DELETE FROM dreamteam.Users WHERE username = ?;"))
				{
					stmt.setString(1, username);
					stmt.executeUpdate();
				}
				return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
			}
			catch(SQLException e)
			{
				throw new RepositoryException("Problem with deleteting user: " + username + ".", e);
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseException("Problem with connection to database.", e);
		}
	}

	private Connection getConnection() throws SQLException
	{
		final String CONNECTION = "jdbc:mysql://80.217.176.187:3306/dreamteam";
		final String USERNAME = "admin";
		final String PASSWORD = "dr3amt3am";
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
		}
		catch(SQLException | ClassNotFoundException e)
		{
			throw new DatabaseException("Problem with connection to database.", e);
		}
	}
}

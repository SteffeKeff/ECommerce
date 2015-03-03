package se.dreamteam.ecommerce.repository.sqldatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import se.dreamteam.ecommerce.repository.sqlinterface.RepositoryException;
import se.dreamteam.ecommerce.repository.sqlinterface.SqlUserInterface;
import se.dreamteam.model.User;

public class SqlUserRepository implements SqlUserInterface
{
	private final static String CONNECTION = "jdbc:mysql://localhost:3306/dreamteam";

	@Override
	public User getUserById(int id) throws RepositoryException
	{

		try (final Connection con = getConnection())
		{

			try (final PreparedStatement stmt = con.prepareStatement("SELECT * FROM dreamteam.Users WHERE id = ?;"))
			{
				stmt.setInt(1, id);
				ResultSet rs = stmt.executeQuery();
				rs.next();
				return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
			}

		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not find user with id: " + id, e);
		}
	}

	@Override
	public User createUser(User user) throws RepositoryException
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
						return new User(key.getInt(1), user.getUsername(), user.getPassword());
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
	public User updateUser(User user) throws RepositoryException
	{
		try (final Connection con = getConnection())
		{

			try (final PreparedStatement stmt = con.prepareStatement("UPDATE dreamteam.Users SET username = ?, password = ? WHERE id = ?"))
			{
				stmt.setString(1, user.getUsername());
				stmt.setString(2, user.getPassword());
				stmt.setInt(3, user.getId());
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
	public User deleteUser(String username) throws RepositoryException
	{
		try (final Connection con = getConnection())
		{

			try (final PreparedStatement stmt = con.prepareStatement("DELETE FROM dreamteam.Users WHERE username = ?"))
			{
				stmt.setString(1, username);
				ResultSet rs = stmt.executeQuery();

				return new User(rs.getInt(1), rs.getString("username"), rs.getString("password"));

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
			return DriverManager.getConnection(CONNECTION, "root", "");
		}
		catch (SQLException | ClassNotFoundException e)
		{
			throw new RepositoryException("Connection problemo", e);
		}
	}

}

package se.dreamteam.ecommerce.repository.sqlinterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connecttest
{

	private final static String CONNECTION = "jdbc:mysql://localhost:3306/dreamteam";

	public static void main(String args[])
	{

		try (final Connection con = DriverManager.getConnection(CONNECTION, "root", ""))
		{
			con.setAutoCommit(false);
			String sql = "Select * From dreamteam.Users";
			try (final PreparedStatement stmt = con.prepareStatement(sql))
			{

				ResultSet rs = stmt.executeQuery();
				con.commit();
				while (rs.next())
				{
					System.out.println(rs.getString("username"));
				}

			}
			catch (SQLException e)
			{
				con.rollback();
			}
		}
		catch (SQLException e)
		{
			throw new RepositoryException("Could not add user", e);
		}

	}
}

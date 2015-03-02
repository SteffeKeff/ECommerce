package se.dreamteam.ecommerce;

import se.dreamteam.ecommerce.repository.sqlinterface.RepositoryException;
import se.dreamteam.ecommerce.repository.sqlinterface.SqlOrderInterface;
import se.dreamteam.ecommerce.repository.sqlinterface.SqlProductInterface;
import se.dreamteam.ecommerce.repository.sqlinterface.SqlUserInterface;
import se.dreamteam.model.User;

public final class ECommerceManager
{
	SqlOrderInterface orders;
	SqlProductInterface products;
	SqlUserInterface users;

	public ECommerceManager(SqlOrderInterface orders, SqlProductInterface products, SqlUserInterface users)
	{
		this.orders = orders;
		this.products = products;
		this.users = users;
	}

	// Users
	public User createUser(User user) throws RepositoryException
	{
		try
		{
			return users.createUser(user);
		}
		catch (RepositoryException e)
		{
			throw new RepositoryException("Could not create user", e);
		}
	}

	public User updateUser(User user) throws RepositoryException
	{
		try
		{
			return users.updateUser(user);
		}
		catch (RepositoryException e)
		{
			throw new RepositoryException("Could not update user: " + user.getUsername(), e);
		}
	}

	public User getUserById(int id) throws RepositoryException
	{
		try
		{
			return users.getUserById(id);
		}
		catch (RepositoryException e)
		{
			throw new RepositoryException("Could not find user with id: " + id, e);
		}
	}

	public User deleteUser(User user) throws RepositoryException
	{
		try
		{
			return users.deleteUser(user);
		}
		catch (RepositoryException e)
		{
			throw new RepositoryException("Could not delete user: " + user.getUsername(), e);
		}
	}
	
	//Products
	

}

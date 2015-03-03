package se.dreamteam.ecommerce.repository.sqlinterface;

import se.dreamteam.model.User;

public interface SqlUserInterface
{
	User getUserByUsername(String username) throws RepositoryException;

	String createUser(User user) throws RepositoryException;

	User updateUser(User user) throws RepositoryException;

	User deleteUser(String username) throws RepositoryException;

}

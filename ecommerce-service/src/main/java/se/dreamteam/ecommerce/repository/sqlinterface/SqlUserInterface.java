package se.dreamteam.ecommerce.repository.sqlinterface;

import se.dreamteam.ecommerce.exceptions.RepositoryException;
import se.dreamteam.model.User;

public interface SqlUserInterface
{
	User getUserByUsername(String username) throws RepositoryException;

	String createUser(User user) throws RepositoryException;

	User updateUser(String username, User user) throws RepositoryException;

	String deleteUser(String username) throws RepositoryException;

}

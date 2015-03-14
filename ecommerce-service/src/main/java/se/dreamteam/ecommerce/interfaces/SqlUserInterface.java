package se.dreamteam.ecommerce.interfaces;

import se.dreamteam.ecommerce.exceptions.RepositoryException;
import se.dreamteam.models.User;

public interface SqlUserInterface
{
	User getUserByUsername(String username) throws RepositoryException;

	String createUser(User user) throws RepositoryException;

	User updateUser(String username, User user) throws RepositoryException;

	User deleteUser(String username) throws RepositoryException;
	
	User loginUser(String username, String password) throws RepositoryException;
}

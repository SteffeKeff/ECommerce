package se.dreamteam.ecommerce.repository.sqlinterface;

import se.dreamteam.model.User;

public interface SqlUserInterface
{
	User getUserById(int id) throws RepositoryException;

	User createUser(User user) throws RepositoryException;

	User updateUser(User user) throws RepositoryException;

	User deleteUser(User user) throws RepositoryException;

}

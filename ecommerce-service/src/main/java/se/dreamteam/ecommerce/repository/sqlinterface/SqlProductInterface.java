package se.dreamteam.ecommerce.repository.sqlinterface;

import se.dreamteam.model.Product;

public interface SqlProductInterface
{
	Product getAllProducts() throws RepositoryException;

	Product getProductWithId(int id) throws RepositoryException;

	Product createProduct(Product product) throws RepositoryException;

	Product updateProduct(Product product) throws RepositoryException;

	Product deleteProduct(int id) throws RepositoryException;

}

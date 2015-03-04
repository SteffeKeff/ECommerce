package se.dreamteam.ecommerce.repository.sqlinterface;

import java.util.TreeSet;

import se.dreamteam.ecommerce.exceptions.RepositoryException;
import se.dreamteam.model.Product;

public interface SqlProductInterface
{
	TreeSet<Product> getAllProducts() throws RepositoryException;

	Product getProductWithId(int productId) throws RepositoryException;

	Product createProduct(Product product) throws RepositoryException;

	Product updateProduct(Product product) throws RepositoryException;

	Product deleteProduct(int productId) throws RepositoryException;

}

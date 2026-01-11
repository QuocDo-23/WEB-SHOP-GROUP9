package code.web.webgroup9.service;

import code.web.webgroup9.dao.ProductDAO;
import code.web.webgroup9.model.ProductWithDetails;

import java.util.List;
import java.util.Optional;

public class ProductService {

    private final ProductDAO productDAO;

    public ProductService() {
        this.productDAO = new ProductDAO();
    }

    public List<ProductWithDetails> getAllProductsWithDetails() {
        return productDAO.getAllProductsWithDetails();
    }

    public List<ProductWithDetails> getProductsByCategory(int categoryId) {
        return productDAO.getProductsByCategory(categoryId);
    }

    public Optional<ProductWithDetails> getProductById(int id) {
        return productDAO.getProductById(id);
    }

//    public List<ProductWithDetails> searchProducts(String keyword) {
//        return productDAO.searchProducts(keyword);
//    }

    public void updateRating(int productId, double rating) {
        productDAO.updateRating(productId, rating);
    }

    public List<ProductWithDetails> getFeaturedProducts() {
        return productDAO.getFeaturedProductsByReview(8);

    }

    public int countProductsByCategory(int categoryId) {
        return productDAO.countProductsByCategory(categoryId);

    }
    public List<ProductWithDetails> getProductsByCategoryWithPagination(int categoryId, int offset, int limit) {
        return productDAO.getProductsByCategoryWithPagination(categoryId, offset, limit);

    }

}
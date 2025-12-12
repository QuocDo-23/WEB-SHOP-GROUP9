package code.web.webgroup9.dao;

import code.web.webgroup9.model.ProductWithDetails;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Optional;

public class ProductDAO {
    private final Jdbi jdbi;

    public ProductDAO() {
        this.jdbi = BaseDao.get();
    }

    /**
     * Lấy tất cả sản phẩm với thông tin category và discount
     */
    public List<ProductWithDetails> getAllProductsWithDetails() {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT p.*, " +
                                        "p.name as product_name, " +
                                        "c.name as category_name, " +
                                        "d.discount_rate, " +
                                        "pd.description, pd.warranty, pd.material, pd.voltage, " +
                                        "pd.dimensions, pd.type, pd.color, pd.style, " +
                                        "(SELECT img FROM Image WHERE type = 'product' AND ref_id = p.id LIMIT 1) as main_image, " +
                                        "(SELECT img FROM Image WHERE type = 'product' AND ref_id = p.id LIMIT 1 OFFSET 1) as hover_image " +
                                        "FROM Product p " +
                                        "LEFT JOIN Categories c ON p.category_id = c.id " +
                                        "LEFT JOIN Discount d ON p.discount_id = d.id " +
                                        "LEFT JOIN Product_Detail pd ON p.id = pd.product_id " +
                                        "WHERE p.status = 'active' " +
                                        "ORDER BY c.sort_order, p.id"
                        )
                        .mapToBean(ProductWithDetails.class)
                        .list()
        );
    }

    /**
     * Lấy sản phẩm theo category
     */
    public List<ProductWithDetails> getProductsByCategory(int categoryId) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT p.*, " +
                                        "p.name as product_name, " +
                                        "c.name as category_name, " +
                                        "d.discount_rate, " +
                                        "pd.description, pd.warranty, pd.material, pd.voltage, " +
                                        "pd.dimensions, pd.type, pd.color, pd.style, " +
                                        "(SELECT img FROM Image WHERE type = 'product' AND ref_id = p.id LIMIT 1) as main_image, " +
                                        "(SELECT img FROM Image WHERE type = 'product' AND ref_id = p.id LIMIT 1 OFFSET 1) as hover_image " +
                                        "FROM Product p " +
                                        "LEFT JOIN Categories c ON p.category_id = c.id " +
                                        "LEFT JOIN Discount d ON p.discount_id = d.id " +
                                        "LEFT JOIN Product_Detail pd ON p.id = pd.product_id " +
                                        "WHERE p.status = 'active' AND p.category_id = :categoryId " +
                                        "ORDER BY p.id"
                        )
                        .bind("categoryId", categoryId)
                        .mapToBean(ProductWithDetails.class)
                        .list()
        );
    }

    /**
     * Lấy chi tiết một sản phẩm
     */
    public Optional<ProductWithDetails> getProductById(int id) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT p.*, " +
                                        "p.name as product_name, " +
                                        "c.name as category_name, " +
                                        "d.discount_rate, " +
                                        "pd.description, pd.warranty, pd.material, pd.voltage, " +
                                        "pd.dimensions, pd.type, pd.color, pd.style, " +
                                        "(SELECT img FROM Image WHERE type = 'product' AND ref_id = p.id LIMIT 1) as main_image, " +
                                        "(SELECT img FROM Image WHERE type = 'product' AND ref_id = p.id LIMIT 1 OFFSET 1) as hover_image " +
                                        "FROM Product p " +
                                        "LEFT JOIN Categories c ON p.category_id = c.id " +
                                        "LEFT JOIN Discount d ON p.discount_id = d.id " +
                                        "LEFT JOIN Product_Detail pd ON p.id = pd.product_id " +
                                        "WHERE p.id = :id"
                        )
                        .bind("id", id)
                        .mapToBean(ProductWithDetails.class)
                        .findFirst()
        );
    }

    /**
     * Tìm kiếm sản phẩm theo tên
     */
    public List<ProductWithDetails> searchProducts(String keyword) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT p.*, " +
                                        "c.name as category_name, " +
                                        "d.discount_rate, " +
                                        "pd.description, " +
                                        "(SELECT img FROM Image WHERE type = 'product' AND ref_id = p.id LIMIT 1) as main_image " +
                                        "FROM Product p " +
                                        "LEFT JOIN Categories c ON p.category_id = c.id " +
                                        "LEFT JOIN Discount d ON p.discount_id = d.id " +
                                        "LEFT JOIN Product_Detail pd ON p.id = pd.product_id " +
                                        "WHERE p.status = 'active' AND pd.description LIKE :keyword " +
                                        "ORDER BY p.id LIMIT 5"
                        )
                        .bind("keyword", "%" + keyword + "%")
                        .mapToBean(ProductWithDetails.class)
                        .list()
        );
    }
    /**
     * Update rating của sản phẩm
     */
    public void updateRating(int productId, double rating) {
        jdbi.useHandle(handle ->
                handle.createUpdate(
                                "UPDATE Product SET review = :rating WHERE id = :productId"
                        )
                        .bind("productId", productId)
                        .bind("rating", rating)
                        .execute()
        );
    }
}


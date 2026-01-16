package code.web.webgroup9.dao;

import code.web.webgroup9.model.Product;
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
     * lấy tất cả sản phẩm nổi bật
     */
    /**
     * Lấy sản phẩm nổi bật theo đánh giá trung bình cao nhất
     * Ưu tiên: review cao + có ít nhất một số lượng review nhất định
     * Giới hạn số lượng (ví dụ top 12)
     */
    public List<ProductWithDetails> getFeaturedProductsByReview(int limit) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT p.*, " +
                                        "d.discount_rate, " +
                                        "(SELECT img FROM Image WHERE type = 'product' AND ref_id = p.id LIMIT 1) as main_image, " +
                                        "(SELECT img FROM Image WHERE type = 'product' AND ref_id = p.id LIMIT 1 OFFSET 1) as hover_image, " +
                                        "(SELECT COUNT(*) FROM Review_Product WHERE product_id = p.id) as review_count " +
                                        "FROM Product p " +
                                        "LEFT JOIN Categories c ON p.category_id = c.id " +
                                        "LEFT JOIN Discount d ON p.discount_id = d.id " +
                                        "WHERE p.status = 'active' " +
                                        "AND EXISTS (SELECT 1 FROM Review_Product rp WHERE rp.product_id = p.id) " +
                                        "ORDER BY " +
                                        "p.review DESC, " +
                                        "(SELECT COUNT(*) FROM Review_Product WHERE product_id = p.id) DESC, " +
                                        "p.id DESC " +
                                        "LIMIT :limit"
                        )
                        .bind("limit", limit)
                        .mapToBean(ProductWithDetails.class)
                        .list()
        );
    }

    /**
     * Lấy tất cả sản phẩm với thông tin category và discount
     */
    public List<ProductWithDetails> getAllProductsWithDetails() {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT p.*, " +
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
                                        "WHERE p.status = 'active' " +
                                        "AND (p.name LIKE :keyword OR pd.description LIKE :keyword OR c.name LIKE :keyword) " +
                                        "ORDER BY " +
                                        "CASE " +
                                        "  WHEN p.name LIKE :exactKeyword THEN 1 " +
                                        "  WHEN p.name LIKE :keyword THEN 2 " +
                                        "  WHEN pd.description LIKE :keyword THEN 3 " +
                                        "  ELSE 4 " +
                                        "END, " +
                                        "p.review DESC " +
                                        "LIMIT 5"
                        )
                        .bind("keyword", "%" + keyword + "%")
                        .bind("exactKeyword", keyword + "%")
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


    /**
     * Lấy 8 sản phẩm đầu tiên theo category (dùng cho trang products)
     */
    public List<ProductWithDetails> getTop8ProductsByCategory(int categoryId) {
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
                                        "ORDER BY p.id " +
                                        "LIMIT 8"
                        )
                        .bind("categoryId", categoryId)
                        .mapToBean(ProductWithDetails.class)
                        .list()
        );
    }

    // ===== THÊM MỚI =====
    /**
     * Đếm tổng số sản phẩm theo category
     * Dùng để kiểm tra có hiển thị "Xem thêm" hay không
     */
    public int countProductsByCategory(int categoryId) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT COUNT(*) FROM Product " +
                                        "WHERE status = 'active' AND category_id = :categoryId"
                        )
                        .bind("categoryId", categoryId)
                        .mapTo(Integer.class)
                        .one()
        );
    }
    /**
     * Lấy sản phẩm theo category với phân trang
     */
    public List<ProductWithDetails> getProductsByCategoryWithPagination(int categoryId, int offset, int limit) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT p.*, " +
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
                                        "ORDER BY p.id " +
                                        "LIMIT :limit OFFSET :offset"
                        )
                        .bind("categoryId", categoryId)
                        .bind("limit", limit)
                        .bind("offset", offset)
                        .mapToBean(ProductWithDetails.class)
                        .list()
        );
    }
    // ====================


}

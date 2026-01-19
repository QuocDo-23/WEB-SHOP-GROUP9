package code.web.webgroup9.dao;

import code.web.webgroup9.model.Product;
import code.web.webgroup9.model.ProductWithDetails;
import org.jdbi.v3.core.Jdbi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    /**
     * Giảm số lượng sản phẩm khi có đơn hàng
     */
    public boolean decreaseProductQuantity(int productId, int quantity) {
        String sql = "UPDATE product SET inventory_quantity = inventory_quantity - ? WHERE id = ? AND inventory_quantity >= ?";

        return jdbi.withHandle(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind(0, quantity)
                    .bind(1, productId)
                    .bind(2, quantity)
                    .execute();
            return rows > 0;
        });
    }
    /**
     * Đếm tổng số sản phẩm
     */
    public int getTotalProductCount() {
        String sql = "SELECT COUNT(*) FROM product";

        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .mapTo(Integer.class)
                    .findOne()
                    .orElse(0);
        });
    }

    /**
     * Đếm sản phẩm theo trạng thái (dựa vào quantity)
     */
    public int getProductCountByStatus(String status) {
        String sql;
        if ("active".equals(status)) {
            sql = "SELECT COUNT(*) FROM product WHERE inventory_quantity > 0";
        } else {
            sql = "SELECT COUNT(*) FROM product WHERE inventory_quantity = 0";
        }

        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .mapTo(Integer.class)
                    .findOne()
                    .orElse(0);
        });
    }

    /**
     * Đếm sản phẩm sắp hết hàng
     */
    public int getLowStockProductCount(int threshold) {
        String sql = "SELECT COUNT(*) FROM product WHERE inventory_quantity > 0 AND inventory_quantity <= ?";

        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .bind(0, threshold)
                    .mapTo(Integer.class)
                    .findOne()
                    .orElse(0);
        });
    }

    /**
     * Đếm sản phẩm hết hàng
     */
    public int getOutOfStockProductCount() {
        String sql = "SELECT COUNT(*) FROM product WHERE inventory_quantity = 0";

        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .mapTo(Integer.class)
                    .findOne()
                    .orElse(0);
        });
    }

    /**
     * Insert sản phẩm mới
     */
    public boolean insertProduct(ProductWithDetails product) {
        return jdbi.inTransaction(handle -> {

            // 1. Insert Product
            int productId = handle.createUpdate(
                            "INSERT INTO Product (name, category_id, price, inventory_quantity, discount_id, status) " +
                                    "VALUES (?, ?, ?, ?, ?, 'active')")
                    .bind(0, product.getName())
                    .bind(1, product.getCategoryId())
                    .bind(2, product.getPrice())
                    .bind(3, product.getInventoryQuantity())
                    .bind(4, product.getDiscountId())
                    .executeAndReturnGeneratedKeys("id")
                    .mapTo(int.class)
                    .one();

            // 2. Insert Product_Detail
            handle.createUpdate(
                            "INSERT INTO Product_Detail (product_id, description, material, voltage, dimensions, " +
                                    "type, color, style, warranty) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")
                    .bind(0, productId)
                    .bind(1, product.getDescription())
                    .bind(2, product.getMaterial())
                    .bind(3, product.getVoltage())
                    .bind(4, product.getDimensions())
                    .bind(5, product.getType())
                    .bind(6, product.getColor())
                    .bind(7, product.getStyle())
                    .bind(8, product.getWarranty())
                    .execute();

            // 3. Insert main image
            if (product.getMainImage() != null) {
                handle.createUpdate(
                                "INSERT INTO Image (type, ref_id, img) VALUES ('product', ?, ?)")
                        .bind(0, productId)
                        .bind(1, product.getMainImage())
                        .execute();
            }

            return true;
        });
    }


    /**
     * Update sản phẩm
     */
    public boolean updateProduct(ProductWithDetails product) {
        return jdbi.inTransaction(handle -> {

            // 1. Update Product
            int productRows = handle.createUpdate(
                            "UPDATE Product SET name = ?, category_id = ?, price = ?, inventory_quantity = ?, discount_id = ? WHERE id = ?")
                    .bind(0, product.getName())
                    .bind(1, product.getCategoryId())
                    .bind(2, product.getPrice())
                    .bind(3, product.getInventoryQuantity())
                    .bind(4, product.getDiscountId())
                    .bind(5, product.getId())
                    .execute();

            // 2. Update Product_Detail
            int detailRows = handle.createUpdate(
                            "UPDATE Product_Detail SET description = ?, material = ?, voltage = ?, dimensions = ?, " +
                                    "type = ?, color = ?, style = ?, warranty = ? WHERE product_id = ?")
                    .bind(0, product.getDescription())
                    .bind(1, product.getMaterial())
                    .bind(2, product.getVoltage())
                    .bind(3, product.getDimensions())
                    .bind(4, product.getType())
                    .bind(5, product.getColor())
                    .bind(6, product.getStyle())
                    .bind(7, product.getWarranty())
                    .bind(8, product.getId())
                    .execute();

            // 3. Update main image
            if (product.getMainImage() != null) {
                handle.createUpdate(
                                "UPDATE Image SET img = ? WHERE type = 'product' AND ref_id = ? LIMIT 1")
                        .bind(0, product.getMainImage())
                        .bind(1, product.getId())
                        .execute();
            }

            return productRows > 0 && detailRows > 0;
        });
    }


    /**
     * Xóa sản phẩm
     */
    public boolean deleteProduct(int productId) {
        String sql = "DELETE FROM product WHERE id = ?";

        return jdbi.withHandle(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind(0, productId)
                    .execute();
            return rows > 0;
        });
    }
    // Thêm vào ProductDAO.java

    /**
     * LẤY SẢN PHẨM VỚI PHÂN TRANG NGAY TỪ DATABASE
     * Tránh lấy toàn bộ rồi mới filter ở Java
     */
    public List<ProductWithDetails> getProductsWithPagination(
            String search, Integer categoryId, String status,
            int offset, int limit) {

        StringBuilder sql = new StringBuilder(
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
                        "WHERE 1=1 "
        );

        // Thêm điều kiện filter
        if (search != null && !search.trim().isEmpty()) {
            sql.append("AND p.name LIKE :search ");
        }

        if (categoryId != null) {
            sql.append("AND p.category_id = :categoryId ");
        }

        if (status != null && !status.trim().isEmpty()) {
            if ("active".equals(status)) {
                sql.append("AND p.inventory_quantity > 0 ");
            } else if ("out_of_stock".equals(status)) {
                sql.append("AND p.inventory_quantity = 0 ");
            }
        }

        sql.append("ORDER BY p.id DESC ");
        sql.append("LIMIT :limit OFFSET :offset");

        return jdbi.withHandle(handle -> {
            var query = handle.createQuery(sql.toString())
                    .bind("limit", limit)
                    .bind("offset", offset);

            if (search != null && !search.trim().isEmpty()) {
                query.bind("search", "%" + search + "%");
            }

            if (categoryId != null) {
                query.bind("categoryId", categoryId);
            }

            return query.mapToBean(ProductWithDetails.class).list();
        });
    }

    /**
     * ĐẾM SỐ LƯỢNG SẢN PHẨM VỚI FILTER
     * Dùng cho phân trang
     */
    public int countProductsWithFilter(String search, Integer categoryId, String status) {
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) FROM Product p WHERE 1=1 "
        );

        if (search != null && !search.trim().isEmpty()) {
            sql.append("AND p.name LIKE :search ");
        }

        if (categoryId != null) {
            sql.append("AND p.category_id = :categoryId ");
        }

        if (status != null && !status.trim().isEmpty()) {
            if ("active".equals(status)) {
                sql.append("AND p.inventory_quantity > 0 ");
            } else if ("out_of_stock".equals(status)) {
                sql.append("AND p.inventory_quantity = 0 ");
            }
        }

        return jdbi.withHandle(handle -> {
            var query = handle.createQuery(sql.toString());

            if (search != null && !search.trim().isEmpty()) {
                query.bind("search", "%" + search + "%");
            }

            if (categoryId != null) {
                query.bind("categoryId", categoryId);
            }

            return query.mapTo(Integer.class).one();
        });
    }


}

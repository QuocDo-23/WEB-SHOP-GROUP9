package code.web.webgroup9.dao;

import code.web.webgroup9.model.Order;
import org.jdbi.v3.core.Jdbi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import code.web.webgroup9.model.OrderItem;


public class OrderDAO {

    private final Jdbi jdbi;

    public OrderDAO() {
        this.jdbi = BaseDao.get();
    }


    public int insertOrder(Order order) {
        String sql = "INSERT INTO orders (user_id, recipient_name, recipient_phone, recipient_email, " +
                "shipping_house_number, shipping_commune, shipping_district, shipping_address_detail, " +
                "order_date, total, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        return jdbi.withHandle(handle -> handle.createUpdate(sql)
                .bind(0, order.getUserId())
                .bind(1, order.getRecipientName())
                .bind(2, order.getRecipientPhone())
                .bind(3, order.getRecipientEmail())
                .bind(4, order.getShippingHouseNumber())
                .bind(5, order.getShippingCommune())
                .bind(6, order.getShippingDistrict())
                .bind(7, order.getShippingAddressDetail())
                .bind(8, order.getOrderDate())
                .bind(9, order.getTotal())
                .bind(10, order.getStatus())
                .executeAndReturnGeneratedKeys("id")
                .mapTo(Integer.class)
                .findOne()
                .orElse(-1));
    }

    /**
     * Lấy order theo ID
     */
    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM orders WHERE id = ?";

        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .bind(0, orderId)
                    .map((rs, ctx) -> {
                        Order order = new Order();
                        order.setId(rs.getInt("id"));

                        Integer userId = (Integer) rs.getObject("user_id");
                        order.setUserId(userId);

                        order.setRecipientName(rs.getString("recipient_name"));
                        order.setRecipientPhone(rs.getString("recipient_phone"));
                        order.setRecipientEmail(rs.getString("recipient_email"));
                        order.setShippingHouseNumber(rs.getString("shipping_house_number"));
                        order.setShippingCommune(rs.getString("shipping_commune"));
                        order.setShippingDistrict(rs.getString("shipping_district"));
                        order.setShippingAddressDetail(rs.getString("shipping_address_detail"));
                        order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
                        order.setTotal(rs.getDouble("total"));
                        order.setStatus(rs.getString("status"));

                        return order;
                    })
                    .findOne()
                    .orElse(null);
        });
    }

    /**
     * Lấy tất cả orders của user
     */
    public List<Order> getOrdersByUserId(int userId) {
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";

        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .bind(0, userId)
                    .map((rs, ctx) -> {
                        Order order = new Order();
                        order.setId(rs.getInt("id"));

                        Integer uid = (Integer) rs.getObject("user_id");
                        order.setUserId(uid);

                        order.setRecipientName(rs.getString("recipient_name"));
                        order.setRecipientPhone(rs.getString("recipient_phone"));
                        order.setRecipientEmail(rs.getString("recipient_email"));
                        order.setShippingHouseNumber(rs.getString("shipping_house_number"));
                        order.setShippingCommune(rs.getString("shipping_commune"));
                        order.setShippingDistrict(rs.getString("shipping_district"));
                        order.setShippingAddressDetail(rs.getString("shipping_address_detail"));
                        order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
                        order.setTotal(rs.getDouble("total"));
                        order.setStatus(rs.getString("status"));

                        return order;
                    })
                    .list();
        });
    }

    /**
     * Cập nhật trạng thái order
     */
    public boolean updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";

        return jdbi.withHandle(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind(0, status)
                    .bind(1, orderId)
                    .execute();
            return rows > 0;
        });
    }

    /**
     * Xóa order
     */
    public boolean deleteOrder(int orderId) {
        String sql = "DELETE FROM orders WHERE id = ?";

        return jdbi.withHandle(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind(0, orderId)
                    .execute();
            return rows > 0;
        });
    }

    /**
     * Lấy tất cả orders (cho admin)
     */
    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM orders ORDER BY order_date DESC";

        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .map((rs, ctx) -> {
                        Order order = new Order();
                        order.setId(rs.getInt("id"));

                        Integer userId = (Integer) rs.getObject("user_id");
                        order.setUserId(userId);

                        order.setRecipientName(rs.getString("recipient_name"));
                        order.setRecipientPhone(rs.getString("recipient_phone"));
                        order.setRecipientEmail(rs.getString("recipient_email"));
                        order.setShippingHouseNumber(rs.getString("shipping_house_number"));
                        order.setShippingCommune(rs.getString("shipping_commune"));
                        order.setShippingDistrict(rs.getString("shipping_district"));
                        order.setShippingAddressDetail(rs.getString("shipping_address_detail"));
                        order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
                        order.setTotal(rs.getDouble("total"));
                        order.setStatus(rs.getString("status"));

                        return order;
                    })
                    .list();
        });
    }

    /**
     * Insert order item mới
     */
    public boolean insertOrderItem(OrderItem item) {
        String sql = "INSERT INTO order_details (order_id, product_id, product_name, img, product_material, " +
                "price, quantity, subtotal) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        return jdbi.withHandle(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind(0, item.getOrderId())
                    .bind(1, item.getProductId())
                    .bind(2, item.getProductName())
                    .bind(3, item.getImg())
                    .bind(4, item.getProductMaterial())
                    .bind(5, item.getPrice())
                    .bind(6, item.getQuantity())
                    .bind(7, item.getSubtotal())
                    .execute();
            return rows > 0;
        });
    }

    /**
     * Lấy tất cả items của một order
     */
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        String sql = "SELECT * FROM order_details WHERE order_id = ?";

        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .bind(0, orderId)
                    .map((rs, ctx) -> {
                        OrderItem item = new OrderItem();
                        item.setId(rs.getInt("id"));
                        item.setOrderId(rs.getInt("order_id"));
                        item.setProductId(rs.getInt("product_id"));
                        item.setProductName(rs.getString("product_name"));
                        item.setImg(rs.getString("img"));
                        item.setProductMaterial(rs.getString("product_material"));
                        item.setPrice(rs.getDouble("price"));
                        item.setQuantity(rs.getInt("quantity"));
                        item.setSubtotal(rs.getDouble("subtotal"));
                        return item;
                    })
                    .list();
        });
    }

    /**
     * Lấy order item theo ID
     */
    public OrderItem getOrderItemById(int itemId) {
        String sql = "SELECT * FROM order_details WHERE id = ?";

        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .bind(0, itemId)
                    .map((rs, ctx) -> {
                        OrderItem item = new OrderItem();
                        item.setId(rs.getInt("id"));
                        item.setOrderId(rs.getInt("order_id"));
                        item.setProductId(rs.getInt("product_id"));
                        item.setProductName(rs.getString("product_name"));
                        item.setImg(rs.getString("img"));
                        item.setProductMaterial(rs.getString("product_material"));
                        item.setPrice(rs.getDouble("price"));
                        item.setQuantity(rs.getInt("quantity"));
                        item.setSubtotal(rs.getDouble("subtotal"));
                        return item;
                    })
                    .findOne()
                    .orElse(null);
        });
    }

    /**
     * Xóa order item
     */
    public boolean deleteOrderItem(int itemId) {
        String sql = "DELETE FROM order_details WHERE id = ?";

        return jdbi.withHandle(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind(0, itemId)
                    .execute();
            return rows > 0;
        });
    }

    /**
     * Xóa tất cả items của order
     */
    public boolean deleteOrderItemsByOrderId(int orderId) {
        String sql = "DELETE FROM order_details WHERE order_id = ?";

        return jdbi.withHandle(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind(0, orderId)
                    .execute();
            return rows > 0;
        });
    }
    /**
     * Đếm số đơn hàng chưa giao của user
     */
    public int countOrdersByUserId(int userId) {
        String sql = "SELECT COUNT(*) FROM orders WHERE user_id = ? AND status != 'delivered' AND status != 'cancelled'";

        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .bind(0, userId)
                    .mapTo(Integer.class)
                    .findOne()
                    .orElse(0);
        });
    }
    /**
     * Lấy tổng doanh thu
     */
    public double getTotalRevenue() {
        String sql = "SELECT COALESCE(SUM(total), 0) FROM orders WHERE status != 'cancelled'";

        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .mapTo(Double.class)
                    .findOne()
                    .orElse(0.0);
        });
    }

    /**
     * Đếm tổng số đơn hàng
     */
    public int getTotalOrderCount() {
        String sql = "SELECT COUNT(*) FROM orders";

        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .mapTo(Integer.class)
                    .findOne()
                    .orElse(0);
        });
    }

    /**
     * Đếm đơn hàng theo trạng thái
     */
    public int getOrderCountByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM orders WHERE status = ?";

        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .bind(0, status)
                    .mapTo(Integer.class)
                    .findOne()
                    .orElse(0);
        });
    }

    /**
     * Đếm đơn hàng hôm nay
     */
    public int getTodayOrderCount() {
        String sql = "SELECT COUNT(*) FROM orders WHERE DATE(order_date) = CURDATE()";

        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .mapTo(Integer.class)
                    .findOne()
                    .orElse(0);
        });
    }

    /**
     * Lấy doanh thu tháng hiện tại
     */
    public double getCurrentMonthRevenue() {
        String sql = "SELECT COALESCE(SUM(total), 0) FROM orders " +
                "WHERE MONTH(order_date) = MONTH(CURDATE()) " +
                "AND YEAR(order_date) = YEAR(CURDATE()) " +
                "AND status != 'cancelled'";

        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .mapTo(Double.class)
                    .findOne()
                    .orElse(0.0);
        });
    }

    /**
     * Đếm đơn hàng tháng hiện tại
     */
    public int getCurrentMonthOrderCount() {
        String sql = "SELECT COUNT(*) FROM orders " +
                "WHERE MONTH(order_date) = MONTH(CURDATE()) " +
                "AND YEAR(order_date) = YEAR(CURDATE())";

        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .mapTo(Integer.class)
                    .findOne()
                    .orElse(0);
        });
    }

    /**
     * Lấy top sản phẩm bán chạy
     */
    public List<Map<String, Object>> getTopSellingProducts(int limit) {
        String sql = "SELECT od.product_name, " +
                "(SELECT img FROM Image WHERE type = 'product' AND ref_id = p.id LIMIT 1) as img, " +
                "c.name, SUM(od.quantity) as total_sold, " +
                "SUM(od.subtotal) as revenue " +
                "FROM order_details od " +
                "JOIN orders o ON od.order_id = o.id " +
                "JOIN product p ON od.product_id = p.id " +
                "JOIN categories c ON p.category_id = c.id " +
                "WHERE o.status != 'cancelled' " +
                "GROUP BY od.product_name, p.id, c.name " +
                "ORDER BY total_sold DESC " +
                "LIMIT ?";

        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .bind(0, limit)
                    .map((rs, ctx) -> {
                        Map<String, Object> product = new HashMap<>();
                        product.put("productName", rs.getString("product_name"));
                        product.put("img", rs.getString("img"));
                        product.put("category", rs.getString("name"));
                        product.put("totalSold", rs.getInt("total_sold"));
                        product.put("revenue", rs.getDouble("revenue"));
                        return product;
                    })
                    .list();
        });
    }

    /**
     * Lấy đơn hàng gần đây
     */
    public List<Order> getRecentOrders(int limit) {
        String sql = "SELECT * FROM orders ORDER BY order_date DESC, id DESC LIMIT ?";

        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .bind(0, limit)
                    .map((rs, ctx) -> {
                        Order order = new Order();
                        order.setId(rs.getInt("id"));
                        order.setUserId((Integer) rs.getObject("user_id"));
                        order.setRecipientName(rs.getString("recipient_name"));
                        order.setRecipientPhone(rs.getString("recipient_phone"));
                        order.setRecipientEmail(rs.getString("recipient_email"));
                        order.setShippingHouseNumber(rs.getString("shipping_house_number"));
                        order.setShippingCommune(rs.getString("shipping_commune"));
                        order.setShippingDistrict(rs.getString("shipping_district"));
                        order.setShippingAddressDetail(rs.getString("shipping_address_detail"));
                        order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
                        order.setTotal(rs.getDouble("total"));
                        order.setStatus(rs.getString("status"));
                        return order;
                    })
                    .list();
        });
    }

    /**
     * Lấy doanh thu theo tháng
     */
    public List<Map<String, Object>> getMonthlyRevenue(int months) {
        String sql = "SELECT DATE_FORMAT(order_date, '%Y-%m') as month, " +
                "SUM(total) as revenue, COUNT(*) as order_count " +
                "FROM orders " +
                "WHERE order_date >= DATE_SUB(CURDATE(), INTERVAL ? MONTH) " +
                "AND status != 'cancelled' " +
                "GROUP BY DATE_FORMAT(order_date, '%Y-%m') " +
                "ORDER BY month DESC";

        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .bind(0, months)
                    .map((rs, ctx) -> {
                        Map<String, Object> data = new HashMap<>();
                        data.put("month", rs.getString("month"));
                        data.put("revenue", rs.getDouble("revenue"));
                        data.put("orderCount", rs.getInt("order_count"));
                        return data;
                    })
                    .list();
        });
    }
}
package code.web.webgroup9.dao;

import code.web.webgroup9.model.Order;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
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
                        order.setOrderDate(rs.getDate("order_date").toLocalDate());
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
                        order.setOrderDate(rs.getDate("order_date").toLocalDate());
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
                        order.setOrderDate(rs.getDate("order_date").toLocalDate());
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
        String sql = "INSERT INTO order_details (order_id, product_id, product_name, product_material, " +
                "price, quantity, subtotal) VALUES (?, ?, ?, ?, ?, ?, ?)";

        return jdbi.withHandle(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind(0, item.getOrderId())
                    .bind(1, item.getProductId())
                    .bind(2, item.getProductName())
                    .bind(3, item.getProductMaterial())
                    .bind(4, item.getPrice())
                    .bind(5, item.getQuantity())
                    .bind(6, item.getSubtotal())
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
}
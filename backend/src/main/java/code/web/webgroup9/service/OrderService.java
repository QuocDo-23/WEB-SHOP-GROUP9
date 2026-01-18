package code.web.webgroup9.service;

import code.web.webgroup9.dao.OrderDAO;
import code.web.webgroup9.model.Order;
import code.web.webgroup9.model.OrderItem;

import java.util.List;

public class OrderService {
    private final OrderDAO orderDAO;

    public OrderService() {
        this.orderDAO = new OrderDAO();
    }

    public int insertOrder(Order order) {
        return orderDAO.insertOrder(order);
    }

    public Order getOrderById(int orderId) {
        return orderDAO.getOrderById(orderId);
    }

    public List<Order> getOrdersByUserId(int userId) {
        return orderDAO.getOrdersByUserId(userId);
    }

    public boolean updateOrderStatus(int orderId, String status) {
        return orderDAO.updateOrderStatus(orderId, status);
    }

    public boolean deleteOrder(int orderId) {
        return orderDAO.deleteOrder(orderId);
    }

    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }

    public boolean insertOrderItem(OrderItem item) {
        return orderDAO.insertOrderItem(item);
    }

    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        return orderDAO.getOrderItemsByOrderId(orderId);

    }

    public OrderItem getOrderItemById(int itemId) {
        return orderDAO.getOrderItemById(itemId);
    }

    public boolean deleteOrderItem(int itemId) {
        return orderDAO.deleteOrderItem(itemId);
    }

    public boolean deleteOrderItemsByOrderId(int orderId) {
        return orderDAO.deleteOrderItemsByOrderId(orderId);
    }
    public int countOrdersByUserId(int userId) {
        return orderDAO.countOrdersByUserId(userId);
    }
}

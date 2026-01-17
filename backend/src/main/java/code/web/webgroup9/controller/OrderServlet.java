package code.web.webgroup9.controller;

import code.web.webgroup9.dao.OrderDAO;
import code.web.webgroup9.dao.PaymentDAO;
import code.web.webgroup9.model.Order;
import code.web.webgroup9.model.OrderItem;
import code.web.webgroup9.model.Payment;
import code.web.webgroup9.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "OrderServlet", urlPatterns = {"/orders"})
public class OrderServlet extends HttpServlet {

    private OrderDAO orderDAO;
    private PaymentDAO paymentDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
        paymentDAO = new PaymentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Kiểm tra đăng nhập
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy danh sách đơn hàng của user
        List<Order> orders = orderDAO.getOrdersByUserId(user.getId());

        // Lấy chi tiết từng đơn hàng và thông tin thanh toán
        Map<Integer, List<OrderItem>> orderItemsMap = new HashMap<>();
        Map<Integer, Payment> paymentMap = new HashMap<>();

        for (Order order : orders) {
            List<OrderItem> items = orderDAO.getOrderItemsByOrderId(order.getId());
            orderItemsMap.put(order.getId(), items);

            Payment payment = paymentDAO.getPaymentByOrderId(order.getId());
            if (payment != null) {
                paymentMap.put(order.getId(), payment);
            }
        }

        request.setAttribute("orders", orders);
        request.setAttribute("orderItemsMap", orderItemsMap);
        request.setAttribute("paymentMap", paymentMap);
        request.setAttribute("activeTab", "orders");;

        request.getRequestDispatcher("order.jsp").forward(request, response);
    }
}
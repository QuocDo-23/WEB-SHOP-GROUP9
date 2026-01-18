package code.web.webgroup9.controller;

import code.web.webgroup9.dao.*;
import code.web.webgroup9.model.*;
import code.web.webgroup9.service.OrderService;
import code.web.webgroup9.service.PaymentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@WebServlet("/order-success")
public class OrderSuccessServlet extends HttpServlet {

    private OrderService orderDAO;
    private PaymentService paymentDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderService();
        paymentDAO = new PaymentService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Lấy orderId từ session
        Integer orderId = (Integer) session.getAttribute("orderId");

        if (orderId == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        // Lấy thông tin đơn hàng
        Order order = orderDAO.getOrderById(orderId);
        if (order == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        List<OrderItem> orderItems = orderDAO.getOrderItemsByOrderId(orderId);

        Payment payment = paymentDAO.getPaymentByOrderId(orderId);

        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

        request.setAttribute("order", order);
        request.setAttribute("orderItems", orderItems);
        request.setAttribute("payment", payment);
        request.setAttribute("currencyFormat", currencyFormat);

        session.removeAttribute("orderId");

        request.getRequestDispatcher("/order-success.jsp").forward(request, response);
    }
}
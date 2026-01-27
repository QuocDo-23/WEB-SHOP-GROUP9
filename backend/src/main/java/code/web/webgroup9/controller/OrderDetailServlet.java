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
import java.util.List;

@WebServlet(name = "UserOrderDetailServlet", urlPatterns = {"/order_detail"})
public class OrderDetailServlet extends HttpServlet {

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

        // Lấy order ID từ request
        String orderIdParam = request.getParameter("id");

        if (orderIdParam == null || orderIdParam.trim().isEmpty()) {
            session.setAttribute("errorMessage", "Không tìm thấy mã đơn hàng!");
            response.sendRedirect("orders");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdParam);

            // Lấy thông tin đơn hàng
            Order order = orderDAO.getOrderById(orderId);

            if (order == null) {
                session.setAttribute("errorMessage", "Đơn hàng không tồn tại!");
                response.sendRedirect("orders");
                return;
            }

            // Kiểm tra đơn hàng có thuộc về user hiện tại không
            if (order.getUserId() != user.getId()) {
                session.setAttribute("errorMessage", "Bạn không có quyền xem đơn hàng này!");
                response.sendRedirect("orders");
                return;
            }

            // Lấy chi tiết sản phẩm trong đơn hàng
            List<OrderItem> orderItems = orderDAO.getOrderItemsByOrderId(orderId);

            // Lấy thông tin thanh toán
            Payment payment = paymentDAO.getPaymentByOrderId(orderId);

            // Set attributes để hiển thị trên JSP
            request.setAttribute("order", order);
            request.setAttribute("orderItems", orderItems);
            request.setAttribute("payment", payment);

            // Forward đến trang chi tiết đơn hàng
            request.getRequestDispatcher("/order_detail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Mã đơn hàng không hợp lệ!");
            response.sendRedirect("orders");
        } catch (Exception e) {
            session.setAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("orders");
        }
    }
}
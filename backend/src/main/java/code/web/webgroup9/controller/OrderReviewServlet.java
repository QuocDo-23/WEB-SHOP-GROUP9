package code.web.webgroup9.controller;

import code.web.webgroup9.dao.OrderDAO;
import code.web.webgroup9.model.Order;
import code.web.webgroup9.model.OrderItem;
import code.web.webgroup9.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserOrderReviewServlet", urlPatterns = {"/order-review"})
public class OrderReviewServlet extends HttpServlet {

    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
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

        // Lấy order ID từ parameter
        String orderIdStr = request.getParameter("id");
        if (orderIdStr == null || orderIdStr.isEmpty()) {
            response.sendRedirect("orders");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdStr);

            // Lấy thông tin đơn hàng
            Order order = orderDAO.getOrderById(orderId);

            // Kiểm tra đơn hàng có tồn tại và thuộc về user này không
            if (order == null || order.getUserId() != user.getId()) {
                response.sendRedirect("orders");
                return;
            }

            // Kiểm tra đơn hàng đã được giao chưa
            if (!"delivered".equals(order.getStatus())) {
                request.setAttribute("errorMessage", "Chỉ có thể đánh giá đơn hàng đã giao thành công!");
                request.getRequestDispatcher("orders").forward(request, response);
                return;
            }

            // Kiểm tra đã đánh giá chưa
            if (orderDAO.hasOrderReview(orderId)) {
                request.setAttribute("errorMessage", "Đơn hàng này đã được đánh giá!");
                request.getRequestDispatcher("orders").forward(request, response);
                return;
            }

            // Lấy danh sách sản phẩm trong đơn hàng
            List<OrderItem> orderItems = orderDAO.getOrderItemsByOrderId(orderId);

            // Set attributes để hiển thị
            request.setAttribute("order", order);
            request.setAttribute("orderItems", orderItems);

            // Forward đến trang đánh giá
            request.getRequestDispatcher("order_review.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("orders");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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

        try {
            // Lấy dữ liệu từ form
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            int rating = Integer.parseInt(request.getParameter("rating"));
            String reviewText = request.getParameter("reviewText");

            // Validate
            if (rating < 1 || rating > 5) {
                request.setAttribute("errorMessage", "Đánh giá không hợp lệ!");
                doGet(request, response);
                return;
            }

            // Kiểm tra đơn hàng thuộc về user
            Order order = orderDAO.getOrderById(orderId);
            if (order == null || order.getUserId() != user.getId()) {
                response.sendRedirect("orders");
                return;
            }

            // Kiểm tra đơn hàng đã delivered chưa
            if (!"delivered".equals(order.getStatus())) {
                request.setAttribute("errorMessage", "Chỉ có thể đánh giá đơn hàng đã giao thành công!");
                doGet(request, response);
                return;
            }

            // Kiểm tra đã đánh giá chưa
            if (orderDAO.hasOrderReview(orderId)) {
                request.setAttribute("errorMessage", "Đơn hàng này đã được đánh giá!");
                doGet(request, response);
                return;
            }

            // Thêm đánh giá vào database
            boolean success = orderDAO.addOrderReview(orderId, user.getId(), rating, reviewText);

            if (success) {
                // Thành công - redirect về trang orders với thông báo
                session.setAttribute("successMessage", "Cảm ơn bạn đã đánh giá đơn hàng!");
                response.sendRedirect("orders");
            } else {
                // Thất bại
                request.setAttribute("errorMessage", "Có lỗi xảy ra khi gửi đánh giá. Vui lòng thử lại!");
                doGet(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
            doGet(request, response);
        }
    }
}
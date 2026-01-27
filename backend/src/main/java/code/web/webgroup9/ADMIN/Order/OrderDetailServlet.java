package code.web.webgroup9.ADMIN.Order;

import code.web.webgroup9.dao.OrderDAO;
import code.web.webgroup9.dao.ReviewDAO;
import code.web.webgroup9.model.Order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "OrderDetailServlet", urlPatterns = {"/admin/orders/detail"})
public class OrderDetailServlet extends HttpServlet {

    private OrderDAO orderDAO;
    private ReviewDAO reviewDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
        reviewDAO = new ReviewDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Kiểm tra quyền admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        viewOrderDetail(request, response);
    }

    /**
     * Xem chi tiết đơn hàng
     */
    private void viewOrderDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int orderId = Integer.parseInt(request.getParameter("id"));

            Order order = orderDAO.getOrderById(orderId);

            if (order == null) {
                request.setAttribute("errorMessage", "Không tìm thấy đơn hàng!");
                request.getRequestDispatcher("/admin/orders").forward(request, response);
                return;
            }

            // Lấy danh sách sản phẩm trong đơn
            order.setItems(orderDAO.getOrderItemsByOrderId(orderId));

            // Kiểm tra có đánh giá không
            boolean hasReview = reviewDAO.hasOrderReview(orderId);

            request.setAttribute("order", order);
            request.setAttribute("hasReview", hasReview);
            request.setAttribute("currentPage", "orders");

            request.getRequestDispatcher("/Admin/order-detail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Mã đơn hàng không hợp lệ!");
            request.getRequestDispatcher("/admin/orders").forward(request, response);
        }
    }
}

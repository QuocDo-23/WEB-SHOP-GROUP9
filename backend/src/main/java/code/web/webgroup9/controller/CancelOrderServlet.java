package code.web.webgroup9.controller;

import code.web.webgroup9.dao.OrderDAO;
import code.web.webgroup9.model.Order;
import code.web.webgroup9.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "CancelOrderServlet", urlPatterns = {"/cancel-order"})
public class CancelOrderServlet extends HttpServlet {

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
                session.setAttribute("errorMessage", "Bạn không có quyền hủy đơn hàng này!");
                response.sendRedirect("orders");
                return;
            }
            
            // Kiểm tra trạng thái đơn hàng có được phép hủy không
            String currentStatus = order.getStatus();
            if (!currentStatus.equals("pending") && !currentStatus.equals("processing")) {
                session.setAttribute("errorMessage", "Không thể hủy đơn hàng đang ở trạng thái: " + 
                    getStatusText(currentStatus));
                response.sendRedirect("orders");
                return;
            }
            
            // Cập nhật trạng thái đơn hàng thành 'cancelled'
            boolean success = orderDAO.updateOrderStatus(orderId, "cancelled");
            
            if (success) {
                session.setAttribute("successMessage", "Hủy đơn hàng thành công!");
            } else {
                session.setAttribute("errorMessage", "Có lỗi xảy ra khi hủy đơn hàng!");
            }
            
        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Mã đơn hàng không hợp lệ!");
        } catch (Exception e) {
            session.setAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
            e.printStackTrace();
        }
        
        response.sendRedirect("orders");
    }

    /**
     * Helper method để chuyển đổi status code sang text tiếng Việt
     */
    private String getStatusText(String status) {
        switch (status) {
            case "pending":
                return "Chờ xác nhận";
            case "processing":
                return "Đã xác nhận";
            case "shipped":
                return "Đang giao hàng";
            case "delivered":
                return "Đã giao hàng";
            case "cancelled":
                return "Đã hủy";
            default:
                return status;
        }
    }
}

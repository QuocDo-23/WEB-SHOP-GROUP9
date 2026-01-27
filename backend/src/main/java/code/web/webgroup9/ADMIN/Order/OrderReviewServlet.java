package code.web.webgroup9.ADMIN.Order;

import code.web.webgroup9.dao.OrderDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "OrderReviewServlet", urlPatterns = {"/admin/orders/review"})
public class OrderReviewServlet extends HttpServlet {

    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Kiểm tra quyền admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"success\": false, \"message\": \"Unauthorized\"}");
            return;
        }

        viewOrderReview(request, response);
    }

    /**
     * Xem đánh giá đơn hàng (trả về JSON cho AJAX)
     */
    private void viewOrderReview(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            int orderId = Integer.parseInt(request.getParameter("id"));

            Map<String, Object> review = orderDAO.getOrderReview(orderId);

            if (review == null) {
                response.getWriter().write("{\"success\": false, \"message\": \"Đơn hàng chưa có đánh giá\"}");
                return;
            }

            // Tạo JSON response
            StringBuilder json = new StringBuilder();
            json.append("{");
            json.append("\"success\": true,");
            json.append("\"orderId\": \"#DH").append(String.format("%03d", orderId)).append("\",");
            json.append("\"userName\": \"").append(review.get("userName")).append("\",");
            json.append("\"rating\": ").append(review.get("rating")).append(",");
            json.append("\"content\": \"").append(escapeJson((String) review.get("content"))).append("\"");
            json.append("}");

            response.getWriter().write(json.toString());

        } catch (NumberFormatException e) {
            response.getWriter().write("{\"success\": false, \"message\": \"Mã đơn hàng không hợp lệ\"}");
        }
    }

    /**
     * Escape JSON string
     */
    private String escapeJson(String text) {
        if (text == null) return "";
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}

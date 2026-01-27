package code.web.webgroup9.ADMIN.Order;

import code.web.webgroup9.dao.OrderDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "OrderBulkUpdateServlet", urlPatterns = {"/admin/orders/bulkUpdate"})
public class OrderBulkUpdateServlet extends HttpServlet {

    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Kiểm tra quyền admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"success\": false, \"message\": \"Unauthorized\"}");
            return;
        }

        bulkUpdateStatus(request, response);
    }

    /**
     * Cập nhật hàng loạt đơn hàng
     */
    private void bulkUpdateStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String[] orderIdStrings = request.getParameterValues("orderIds[]");
            String newStatus = request.getParameter("status");

            if (orderIdStrings == null || orderIdStrings.length == 0) {
                response.getWriter().write("{\"success\": false, \"message\": \"Chưa chọn đơn hàng nào\"}");
                return;
            }

            List<Integer> orderIds = new ArrayList<>();
            for (String idStr : orderIdStrings) {
                orderIds.add(Integer.parseInt(idStr));
            }

            int updated = orderDAO.bulkUpdateOrderStatus(orderIds, newStatus);

            response.getWriter().write("{\"success\": true, \"message\": \"Đã cập nhật " + updated + " đơn hàng\"}");

        } catch (Exception e) {
            response.getWriter().write("{\"success\": false, \"message\": \"Lỗi: " + e.getMessage() + "\"}");
        }
    }
}

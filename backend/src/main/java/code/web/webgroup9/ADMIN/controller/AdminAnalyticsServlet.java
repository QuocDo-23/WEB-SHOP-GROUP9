package code.web.webgroup9.ADMIN.controller;

import code.web.webgroup9.dao.OrderDAO;
import code.web.webgroup9.dao.UserDAO;
import code.web.webgroup9.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/analytics")
public class AdminAnalyticsServlet extends HttpServlet {

    private OrderDAO orderDAO;
    private UserDAO userDAO;

    @Override
    public void init() {
        orderDAO = new OrderDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // 🔐 check admin
        if (user == null || !"Admin".equalsIgnoreCase(user.getRoleName())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        /* ================= 1️⃣ BIỂU ĐỒ DOANH THU 6 THÁNG ================= */
        List<Map<String, Object>> monthlyRevenue = orderDAO.getMonthlyRevenue(6);

        /* ================= 2️⃣ TRẠNG THÁI ĐƠN HÀNG ================= */
        int pending     = orderDAO.getOrderCountByStatus("pending");
        int delivering  = orderDAO.getOrderCountByStatus("delivering");
        int delivered   = orderDAO.getOrderCountByStatus("delivered");
        int cancelled   = orderDAO.getOrderCountByStatus("cancelled");

        /* ================= 3️⃣ 4 Ô THỐNG KÊ TỔNG ================= */
        double totalRevenue = orderDAO.getTotalRevenue();
        int totalOrders = orderDAO.getTotalOrderCount();
        int totalCustomers = userDAO.getTotalCustomerCount();
        int processingOrders = pending + delivering;

        /* ================= SET ATTRIBUTE ================= */
        request.setAttribute("monthlyRevenue", monthlyRevenue);

        request.setAttribute("pending", pending);
        request.setAttribute("delivering", delivering);
        request.setAttribute("delivered", delivered);
        request.setAttribute("cancelled", cancelled);

        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("totalOrders", totalOrders);
        request.setAttribute("totalCustomers", totalCustomers);
        request.setAttribute("processingOrders", processingOrders);

        request.setAttribute("currentPage", "analytics");

        request.getRequestDispatcher("/Admin/analytics.jsp")
                .forward(request, response);
    }
}

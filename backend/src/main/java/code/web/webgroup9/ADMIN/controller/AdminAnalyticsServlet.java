package code.web.webgroup9.ADMIN.controller;

import code.web.webgroup9.dao.OrderDAO;
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

    @Override
    public void init() {
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !"Admin".equalsIgnoreCase(user.getRoleName())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // 1️⃣ Doanh thu 6 tháng
        List<Map<String, Object>> monthlyRevenue = orderDAO.getMonthlyRevenue(6);

        // 2️⃣ Đơn hàng theo trạng thái (ĐỦ 4 LOẠI)
        int pending     = orderDAO.getOrderCountByStatus("pending");
        int delivering  = orderDAO.getOrderCountByStatus("delivering");
        int delivered   = orderDAO.getOrderCountByStatus("delivered");
        int cancelled   = orderDAO.getOrderCountByStatus("cancelled");

        // 3️⃣ Top sản phẩm
        List<Map<String, Object>> topProducts = orderDAO.getTopSellingProducts(5);

        // set attribute
        request.setAttribute("monthlyRevenue", monthlyRevenue);
        request.setAttribute("pending", pending);
        request.setAttribute("delivering", delivering);
        request.setAttribute("delivered", delivered);
        request.setAttribute("cancelled", cancelled);
        request.setAttribute("topProducts", topProducts);
        request.setAttribute("currentPage", "analytics");

        request.getRequestDispatcher("/Admin/analytics.jsp")
                .forward(request, response);
    }
}



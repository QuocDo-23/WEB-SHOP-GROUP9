package code.web.webgroup9.ADMIN.controller;

import code.web.webgroup9.dao.*;
import code.web.webgroup9.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private OrderDAO orderDAO;
    private ProductDAO productDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
        productDAO = new ProductDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !"Admin".equals(user.getRoleName())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Lấy thống kê tổng quan
        Map<String, Object> stats = new HashMap<>();

        // Tổng khách hàng
        int totalCustomers = userDAO.getTotalCustomerCount();
        stats.put("totalCustomers", totalCustomers);


        // Đơn hàng chờ xử lý
        int pendingOrders = orderDAO.getOrderCountByStatus("pending");
        stats.put("pendingOrders", pendingOrders);


        // Doanh thu tháng này
        double monthRevenue = orderDAO.getCurrentMonthRevenue();
        stats.put("monthRevenue", monthRevenue);

        // Đơn hàng tháng này
        int monthOrders = orderDAO.getCurrentMonthOrderCount();
        stats.put("monthOrders", monthOrders);


        // Top 5 sản phẩm bán chạy
        List<Map<String, Object>> topProducts = orderDAO.getTopSellingProducts(8);
        stats.put("topProducts", topProducts);

        // Format tiền tệ
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

        request.setAttribute("stats", stats);
        request.setAttribute("currencyFormat", currencyFormat);
        request.setAttribute("currentPage", "dashboard");


        request.getRequestDispatcher("/Admin/dashboard.jsp").forward(request, response);
    }
}
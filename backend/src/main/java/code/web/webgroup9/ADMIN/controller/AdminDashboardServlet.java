package code.web.webgroup9.ADMIN.controller;

import code.web.webgroup9.dao.OrderDAO;
import code.web.webgroup9.dao.UserDAO;
import code.web.webgroup9.model.DashboardStats;
import code.web.webgroup9.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private OrderDAO orderDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        //  Giữ nguyên kiểm tra quyền Admin (RẤT QUAN TRỌNG)
        if (user == null || !"Admin".equalsIgnoreCase(user.getRoleName())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        //  Dùng DashboardStats thay cho Map
        DashboardStats stats = new DashboardStats();

        stats.setTotalCustomers(userDAO.getTotalCustomerCount());
        stats.setPendingOrders(orderDAO.getOrderCountByStatus("pending"));
        stats.setMonthRevenue(orderDAO.getCurrentMonthRevenue());
        stats.setMonthOrders(orderDAO.getCurrentMonthOrderCount());
        stats.setTopProducts(orderDAO.getTopSellingProducts(5));

        request.setAttribute("stats", stats);
        request.setAttribute("currentPage", "dashboard");

        request.getRequestDispatcher("/Admin/dashboard.jsp")
                .forward(request, response);
    }
}

package code.web.webgroup9.ADMIN.controller;

import code.web.webgroup9.dao.UserDAO;
import code.web.webgroup9.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
@WebServlet("/admin/customers")
public class CustomerAdminServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);


        // Kiểm tra đăng nhập và quyền admin
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user.getRoleId() != 1) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        // Lấy danh sách khách hàng từ DAO
        List<User> customers = userDAO.getAllCustomers();

        request.setAttribute("customers", customers);
        request.setAttribute("currentPage", "customers");

        request.getRequestDispatcher("/Admin/customers.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
package code.web.webgroup9.ADMIN.controller;

import code.web.webgroup9.dao.AddressDAO;
import code.web.webgroup9.dao.OrderDAO;
import code.web.webgroup9.dao.UserDAO;
import code.web.webgroup9.model.Address;
import code.web.webgroup9.model.Order;
import code.web.webgroup9.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "CustomerDetailServlet", value = "/admin/customer-detail")
public class CustomerDetailServlet extends HttpServlet {

    private UserDAO userDAO;
    private AddressDAO addressDAO;
    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        addressDAO = new AddressDAO();
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Kiểm tra quyền admin
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        User admin = (User) session.getAttribute("user");
        if (admin.getRoleId() != 1) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        // Lấy ID khách hàng
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/customers");
            return;
        }

        try {
            int customerId = Integer.parseInt(idStr);

            //Lấy thông tin khách hàng
            Optional<User> customerOpt = userDAO.getUserById(customerId);
            if (!customerOpt.isPresent()) {
                response.sendRedirect(request.getContextPath() + "/admin/customers");
                return;
            }
            User customer = customerOpt.get();

            //Lấy danh sách địa chỉ
            List<Address> addresses = addressDAO.getAddressByUserId(customerId);

            // Lấy lịch sử đơn hàng
            List<Order> orders = orderDAO.getOrdersByUserId(customerId);

            // Set attributes
            request.setAttribute("customer", customer);
            request.setAttribute("addresses", addresses);
            request.setAttribute("orders", orders);

            request.getRequestDispatcher("/Admin/customer_detail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/customers");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Kiểm tra quyền admin
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        User admin = (User) session.getAttribute("user");
        if (admin.getRoleId() != 1) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String action = request.getParameter("action");
        String idStr = request.getParameter("id");

        if (idStr != null && !idStr.isEmpty()) {
            try {
                int customerId = Integer.parseInt(idStr);

                if ("lock".equals(action)) {
                    userDAO.updateUserStatus(customerId, "banned");
                } else if ("unlock".equals(action)) {
                    userDAO.updateUserStatus(customerId, "active");
                }

                // Redirect lại trang chi tiết để thấy thay đổi
                response.sendRedirect(request.getContextPath() + "/admin/customer-detail?id=" + customerId);
                return;

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin/customers");
    }
}
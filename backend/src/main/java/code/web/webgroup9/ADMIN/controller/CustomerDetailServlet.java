package code.web.webgroup9.ADMIN.controller;

import code.web.webgroup9.dao.AddressDAO;
import code.web.webgroup9.dao.OrderDAO;
import code.web.webgroup9.dao.UserDAO;
import code.web.webgroup9.model.Address;
import code.web.webgroup9.model.Order;
import code.web.webgroup9.model.User;
import code.web.webgroup9.util.PasswordUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@WebServlet("/admin/customer-detail")
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

        //ID khách hàng từ request
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/customers");
            return;
        }

        try {
            int customerId = Integer.parseInt(idStr);

            //thông tin khách hàng
            Optional<User> customerOpt = userDAO.getUserById(customerId);
            if (!customerOpt.isPresent()) {
                response.sendRedirect(request.getContextPath() + "/admin/customers");
                return;
            }
            User customer = customerOpt.get();

            //danh sách địa chỉ
            List<Address> addresses = addressDAO.getAddressByUserId(customerId);

            //lịch sử đơn hàng
            List<Order> orders = orderDAO.getOrdersByUserId(customerId);

            request.setAttribute("customer", customer);
            request.setAttribute("addresses", addresses);
            request.setAttribute("orders", orders);
            request.setAttribute("currentPage", "customers");

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
                }  else if ("changeRole".equals(action)) {
                    String roleIdStr = request.getParameter("newRoleId");
                    if (roleIdStr != null && !roleIdStr.isEmpty()) {
                        try {
                            int newRoleId = Integer.parseInt(roleIdStr);
                            userDAO.updateUserRole(customerId, newRoleId);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            // Log lỗi hoặc thông báo cho admin
                        }
                    }
                } else if ("resetPassword".equals(action)) {
                    String newPassword = generateRandomPassword();
                    String hashedPassword = PasswordUtil.hashPassword(newPassword);

                    boolean success = userDAO.updatePasswordById(customerId, hashedPassword);

                    if (success) {
                        //lưu mật khẩu mới vào session
                        session.setAttribute("newPassword", newPassword);
                        session.setAttribute("passwordResetFor", customerId);
                    }
                }

                // Redirect lại trang
                response.sendRedirect(request.getContextPath() + "/admin/customer-detail?id=" + customerId);
                return;

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin/customers");
    }

    private String generateRandomPassword() {
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        String NUMBER = "0123456789";
        String OTHER_CHAR = "!@#$%&";

        String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            int rndCharAt = random.nextInt(PASSWORD_ALLOW_BASE.length());
            char rndChar = PASSWORD_ALLOW_BASE.charAt(rndCharAt);
            sb.append(rndChar);
        }

        return sb.toString();
    }
}
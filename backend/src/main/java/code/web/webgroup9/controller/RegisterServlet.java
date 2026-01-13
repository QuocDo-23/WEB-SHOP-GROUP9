package code.web.webgroup9.controller;

import code.web.webgroup9.dao.UserDAO;
import code.web.webgroup9.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validation
        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập họ tên");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            request.setAttribute("error", "Email không hợp lệ");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (password == null || password.length() < 6) {
            request.setAttribute("error", "Mật khẩu phải có ít nhất 6 ký tự");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu xác nhận không khớp");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Check email exists
        if (userDAO.emailExists(email)) {
            request.setAttribute("error", "Email đã được sử dụng");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Create user
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        boolean success = userDAO.register(user);

        if (success) {
            request.setAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
            response.sendRedirect("login.jsp?success=registered");
        } else {
            request.setAttribute("error", "Đã xảy ra lỗi. Vui lòng thử lại.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
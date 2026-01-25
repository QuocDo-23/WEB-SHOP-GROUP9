package code.web.webgroup9.controller;

import code.web.webgroup9.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        if (email == null || email.isEmpty()) {
            request.setAttribute("error", "Email không hợp lệ");
            request.getRequestDispatcher("/forgot_password.jsp").forward(request, response);
            return;
        }

        boolean exists = userService.checkEmailExists(email);

        if (!exists) {
            request.setAttribute("error", "Email không tồn tại trong hệ thống");
        } else {
            // demo: chưa gửi mail, chỉ xác nhận tồn tại
            request.setAttribute("message", "Vui lòng kiểm tra email để đặt lại mật khẩu");
        }

        request.getRequestDispatcher("/forgot_password.jsp").forward(request, response);
    }
}

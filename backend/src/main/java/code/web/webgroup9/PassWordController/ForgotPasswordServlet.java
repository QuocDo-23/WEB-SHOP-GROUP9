package code.web.webgroup9.PassWordController;

import code.web.webgroup9.service.EmailService;
import code.web.webgroup9.service.OTPService;
import code.web.webgroup9.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {

    private UserService userService;
    private EmailService emailService;
    private OTPService otpService;

    @Override
    public void init() {
        userService = new UserService();
        emailService = new EmailService();
        otpService = new OTPService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/forgot_password.jsp").forward(request, response);
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

        // Kiểm tra email có tồn tại không
        boolean exists = userService.checkEmailExists(email);

        if (!exists) {
            request.setAttribute("error", "Email không tồn tại trong hệ thống");
            request.getRequestDispatcher("/forgot_password.jsp").forward(request, response);
            return;
        }

        // Tạo mã OTP
        String otp = emailService.generateOTP();

        // Lưu OTP vào bộ nhớ
        otpService.saveOTP(email, otp);

        // Gửi email
        boolean emailSent = emailService.sendOTPEmail(email, otp);

        if (emailSent) {
            // Lưu email vào session để sử dụng ở bước tiếp theo
            request.getSession().setAttribute("resetEmail", email);

            // Chuyển đến trang nhập OTP
            response.sendRedirect(request.getContextPath() + "/verify-otp");
        } else {
            request.setAttribute("error", "Không thể gửi email. Vui lòng thử lại sau.");
            request.getRequestDispatcher("/forgot_password.jsp").forward(request, response);
        }
    }
}
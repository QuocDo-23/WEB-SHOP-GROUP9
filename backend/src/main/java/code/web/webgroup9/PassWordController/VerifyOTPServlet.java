package code.web.webgroup9.PassWordController;

import code.web.webgroup9.service.OTPService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/verify-otp")
public class VerifyOTPServlet extends HttpServlet {

    private OTPService otpService;

    @Override
    public void init() {
        otpService = new OTPService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Kiểm tra xem có email trong session không
        String email = (String) request.getSession().getAttribute("resetEmail");
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/forgot-password");
            return;
        }

        request.getRequestDispatcher("/verify_otp.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String otp = request.getParameter("otp");
        String email = (String) request.getSession().getAttribute("resetEmail");

        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/forgot-password");
            return;
        }

        if (otp == null || otp.isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập mã OTP");
            request.getRequestDispatcher("/verify_otp.jsp").forward(request, response);
            return;
        }

        // Xác thực OTP
        boolean isValid = otpService.verifyOTP(email, otp);

        if (isValid) {
            // Lưu trạng thái đã xác thực OTP
            request.getSession().setAttribute("otpVerified", true);

            // Chuyển đến trang đặt lại mật khẩu
            response.sendRedirect(request.getContextPath() + "/reset-password");
        } else {
            request.setAttribute("error", "Mã OTP không đúng hoặc đã hết hạn");
            request.getRequestDispatcher("/verify_otp.jsp").forward(request, response);
        }
    }
}
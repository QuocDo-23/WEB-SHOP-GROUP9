package code.web.webgroup9.service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Random;

public class EmailService {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String EMAIL_USERNAME = "23130261@st.hcmuaf.edu.vn"; // Thay bằng email của bạn
    private static final String EMAIL_PASSWORD = "iwbg vbwj vimu dcnz"; // Thay bằng App Password của Gmail

    /**
     * Tạo mã OTP ngẫu nhiên 6 chữ số
     */
    public String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    /**
     * Gửi email chứa mã OTP
     */
    public boolean sendOTPEmail(String toEmail, String otp) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Mã OTP đặt lại mật khẩu");

            String htmlContent = "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "    <style>" +
                    "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }" +
                    "        .container { max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }" +
                    "        .header { text-align: center; color: #333; }" +
                    "        .otp-box { background-color: #f8f9fa; border: 2px dashed #007bff; padding: 20px; margin: 20px 0; text-align: center; border-radius: 5px; }" +
                    "        .otp-code { font-size: 32px; font-weight: bold; color: #007bff; letter-spacing: 5px; }" +
                    "        .info { color: #666; line-height: 1.6; }" +
                    "        .warning { color: #dc3545; font-size: 14px; margin-top: 20px; }" +
                    "        .footer { text-align: center; margin-top: 30px; color: #999; font-size: 12px; }" +
                    "    </style>" +
                    "</head>" +
                    "<body>" +
                    "    <div class='container'>" +
                    "        <h2 class='header'>🔐 Đặt lại mật khẩu</h2>" +
                    "        <p class='info'>Xin chào,</p>" +
                    "        <p class='info'>Bạn đã yêu cầu đặt lại mật khẩu. Vui lòng sử dụng mã OTP dưới đây để tiếp tục:</p>" +
                    "        <div class='otp-box'>" +
                    "            <div class='otp-code'>" + otp + "</div>" +
                    "        </div>" +
                    "        <p class='info'>Mã OTP này sẽ hết hạn sau <strong>5 phút</strong>.</p>" +
                    "        <p class='warning'>⚠️ Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.</p>" +
                    "        <div class='footer'>" +
                    "            <p>© 2025 Your Company. All rights reserved.</p>" +
                    "        </div>" +
                    "    </div>" +
                    "</body>" +
                    "</html>";

            message.setContent(htmlContent, "text/html; charset=utf-8");

            Transport.send(message);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
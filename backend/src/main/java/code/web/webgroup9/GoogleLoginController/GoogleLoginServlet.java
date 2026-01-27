package code.web.webgroup9.controller;

import code.web.webgroup9.config.GoogleOAuthConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/google-login")
public class GoogleLoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Tạo state để bảo mật
        String state = java.util.UUID.randomUUID().toString();
        request.getSession().setAttribute("oauth_state", state);

        // Lưu redirect nếu có
        String redirectParam = request.getParameter("redirect");
        if (redirectParam != null) {
            request.getSession().setAttribute("oauth_redirect", redirectParam);
        }

        // Build redirect URI động
        String redirectUri = GoogleOAuthConfig.getRedirectUri(request);

        // Tạo URL để chuyển đến Google OAuth
        String authUrl = GoogleOAuthConfig.AUTH_URL +
                "?client_id=" + GoogleOAuthConfig.CLIENT_ID +
                "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8) +
                "&response_type=code" +
                "&scope=" + URLEncoder.encode(GoogleOAuthConfig.SCOPE, StandardCharsets.UTF_8) +
                "&state=" + state;

        response.sendRedirect(authUrl);
    }
}
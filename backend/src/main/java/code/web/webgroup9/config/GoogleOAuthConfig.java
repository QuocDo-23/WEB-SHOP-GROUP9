package code.web.webgroup9.config;

import jakarta.servlet.http.HttpServletRequest;

public class GoogleOAuthConfig {
    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";

    // Để trống, sẽ build động trong servlet
    public static final String REDIRECT_URI_PATH = "/google-callback";

    public static final String AUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth";
    public static final String TOKEN_URL = "https://oauth2.googleapis.com/token";
    public static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v2/userinfo";

    public static final String SCOPE = "openid email profile";

    /**
     * Build redirect URI động dựa trên request
     */
    public static String getRedirectUri(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        String portPart = "";
        if ((scheme.equals("http") && serverPort != 80) ||
                (scheme.equals("https") && serverPort != 443)) {
            portPart = ":" + serverPort;
        }

        return scheme + "://" + serverName + portPart + contextPath + REDIRECT_URI_PATH;
    }
}
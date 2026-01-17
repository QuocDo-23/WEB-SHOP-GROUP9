package code.web.webgroup9.controller;

import code.web.webgroup9.config.UploadConfig;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy tên file từ URL
        String fileName = request.getPathInfo();
        if (fileName == null || fileName.equals("/")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        fileName = fileName.substring(1);

        String uploadPath = UploadConfig.getUploadPath();
        String filePath = uploadPath + File.separator + fileName;

        File file = new File(filePath);

        if (!file.exists() || !file.isFile()) {
            System.err.println("File not found: " + filePath);
            // Trả về ảnh mặc định
            response.sendRedirect(request.getContextPath() + "/images/https://i.postimg.cc/26JnYsPT/Logo-Photoroom.png");
            return;
        }

        // Xác định content type
        String mimeType = getServletContext().getMimeType(fileName);
        if (mimeType == null) {
            mimeType = "image/jpeg"; // Default
        }

        // Set headers
        response.setContentType(mimeType);
        response.setContentLength((int) file.length());
        response.setHeader("Cache-Control", "max-age=86400");

        // Đọc và gửi file
        try (FileInputStream in = new FileInputStream(file);
             OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        System.out.println("Served image: " + fileName);
    }
}
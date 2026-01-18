package code.web.webgroup9.controller;

import java.io.*;
import java.nio.file.*;

import code.web.webgroup9.config.UploadConfig;
import code.web.webgroup9.model.User;
import code.web.webgroup9.dao.UserDAO;
import code.web.webgroup9.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/update-avatar")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
        maxFileSize = 1024 * 1024 * 10,       // 10MB
        maxRequestSize = 1024 * 1024 * 50     // 50MB
)
public class UpdateAvatarServlet extends HttpServlet {
    private UserService userService;


    @Override
    public void init() throws ServletException {
        userService = new UserService();
        UploadConfig.initUploadDirectory();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            Part filePart = request.getPart("avatarFile");

            if (filePart == null || filePart.getSize() == 0) {
                session.setAttribute("error", "Vui lòng chọn ảnh!");
                response.sendRedirect(request.getContextPath() + "/profile");
                return;
            }

            // Lấy tên file và extension
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();

            // Validate file type
            if (!fileExtension.matches("\\.(jpg|jpeg|png|gif)")) {
                session.setAttribute("error", "Chỉ chấp nhận file ảnh (JPG, PNG, GIF)!");
                response.sendRedirect(request.getContextPath() + "/profile");
                return;
            }

            // Tạo tên file unique
            String uniqueFileName = "avatar_" + user.getId() + "_" + System.currentTimeMillis() + fileExtension;

            String uploadPath = UploadConfig.getUploadPath();
            String filePath = uploadPath + File.separator + uniqueFileName;



            // Xóa ảnh cũ nếu có
            if (user.getAvatarImg() != null && !user.getAvatarImg().isEmpty()) {
                deleteOldAvatar(user.getAvatarImg());
            }

            // Lưu file mới
            filePart.write(filePath);

            // Chỉ lưu TÊN FILE vào database (không lưu đường dẫn đầy đủ)
            String avatarFileName = uniqueFileName;

            boolean updated = userService.updateAvatar(user.getId(), avatarFileName);

            if (updated) {
                user.setAvatarImg(avatarFileName);
                session.setAttribute("user", user);
                session.setAttribute("success", "Cập nhật ảnh đại diện thành công!");
                session.setAttribute("activeTab", "profile");
            } else {
                session.setAttribute("error", "Lỗi khi cập nhật database!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Lỗi: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/profile");
    }

    /**
     * Xóa ảnh cũ
     */
    private void deleteOldAvatar(String fileName) {
        try {
            String uploadPath = UploadConfig.getUploadPath();
            String filePath = uploadPath + File.separator + fileName;
            File oldFile = new File(filePath);
            if (oldFile.exists() && oldFile.isFile()) {
                boolean deleted = oldFile.delete();
                System.out.println("🗑️ Deleted old avatar: " + deleted);
            }
        } catch (Exception e) {
            System.err.println("❌ Error deleting old avatar: " + e.getMessage());
        }
    }
}
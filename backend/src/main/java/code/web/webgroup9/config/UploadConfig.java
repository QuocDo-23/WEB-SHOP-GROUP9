package code.web.webgroup9.config;

import java.io.File;

public class UploadConfig {

    private static final String WINDOWS_UPLOAD_PATH = "E:\\webs\\uploads\\avatars";
    private static final String LINUX_UPLOAD_PATH = "/var/uploads/avatars";

    /**
     * Tự động detect OS và trả về đường dẫn phù hợp
     */
    public static String getUploadPath() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return WINDOWS_UPLOAD_PATH;
        } else {
            return LINUX_UPLOAD_PATH;
        }
    }

    /**
     * Khởi tạo thư mục nếu chưa tồn tại
     */
    public static void initUploadDirectory() {
        File uploadDir = new File(getUploadPath());
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            if (created) {
                System.out.println("✅ Created upload directory: " + uploadDir.getAbsolutePath());
            } else {
                System.err.println("❌ Failed to create upload directory!");
            }
        }
    }
}
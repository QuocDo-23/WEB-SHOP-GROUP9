package code.web.webgroup9.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OTPService {

    // Lưu trữ OTP với email và thời gian hết hạn
    private static final Map<String, OTPData> otpStorage = new ConcurrentHashMap<>();
    private static final int OTP_EXPIRY_MINUTES = 5;

    /**
     * Lưu OTP cho email
     */
    public void saveOTP(String email, String otp) {
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES);
        otpStorage.put(email, new OTPData(otp, expiryTime));
    }

    /**
     * Xác thực OTP
     */
    public boolean verifyOTP(String email, String otp) {
        OTPData otpData = otpStorage.get(email);

        if (otpData == null) {
            return false;
        }

        // Kiểm tra hết hạn
        if (LocalDateTime.now().isAfter(otpData.getExpiryTime())) {
            otpStorage.remove(email);
            return false;
        }

        // Kiểm tra mã OTP
        if (otpData.getOtp().equals(otp)) {
            return true;
        }

        return false;
    }

    /**
     * Xóa OTP sau khi đã sử dụng
     */
    public void removeOTP(String email) {
        otpStorage.remove(email);
    }

    /**
     * Kiểm tra OTP có tồn tại không
     */
    public boolean hasOTP(String email) {
        return otpStorage.containsKey(email);
    }

    /**
     * Class lưu trữ OTP và thời gian hết hạn
     */
    private static class OTPData {
        private final String otp;
        private final LocalDateTime expiryTime;

        public OTPData(String otp, LocalDateTime expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }

        public String getOtp() {
            return otp;
        }

        public LocalDateTime getExpiryTime() {
            return expiryTime;
        }
    }
}
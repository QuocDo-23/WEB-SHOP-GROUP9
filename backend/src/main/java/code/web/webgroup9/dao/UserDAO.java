package code.web.webgroup9.dao;


import code.web.webgroup9.model.User;
import org.jdbi.v3.core.Jdbi;
import code.web.webgroup9.util.PasswordUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.util.Optional;

public class UserDAO {
    private final Jdbi jdbi;

    public UserDAO() {
        this.jdbi = BaseDao.get();
    }

    /**
     * Đăng ký user mới
     */
    public boolean register(User user) {
        try {
            // Hash password
            String hashedPassword = PasswordUtil.hashPassword(user.getPassword());

            return jdbi.withHandle(handle -> {
                int result = handle.createUpdate(
                                "INSERT INTO User (role_id, name, email, password) " +
                                        "VALUES (:roleId, :name, :email, :password)"
                        )
                        .bind("roleId", 2) // Default role: Customer
                        .bind("name", user.getName())
                        .bind("email", user.getEmail())
                        .bind("password", hashedPassword)
                        .execute();

                return result > 0;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Đăng nhập
     */
    public Optional<User> login(String email, String password) {
        try {
            Optional<User> userOpt = jdbi.withHandle(handle ->
                    handle.createQuery(
                                    "SELECT u.*, r.name as role_name " +
                                            "FROM User u " +
                                            "LEFT JOIN Role r ON u.role_id = r.id " +
                                            "WHERE u.email = :email"
                            )
                            .bind("email", email)
                            .mapToBean(User.class)
                            .findFirst()
            );

            if (userOpt.isPresent()) {
                User user = userOpt.get();
                // Verify password
                if (PasswordUtil.verifyPassword(password, user.getPassword())) {
                    return Optional.of(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Kiểm tra email đã tồn tại
     */
    public boolean emailExists(String email) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT COUNT(*) FROM User WHERE email = :email"
                        )
                        .bind("email", email)
                        .mapTo(Integer.class)
                        .one() > 0
        );
    }

    /**
     * Lấy user theo ID
     */
    public Optional<User> getUserById(int id) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT u.*, r.name as role_name " +
                                        "FROM User u " +
                                        "LEFT JOIN Role r ON u.role_id = r.id " +
                                        "WHERE u.id = :id"
                        )
                        .bind("id", id)
                        .mapToBean(User.class)
                        .findFirst()
        );
    }

    /**
     * Lấy user theo email
     */
    public Optional<User> getUserByEmail(String email) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT u.*, r.name as role_name " +
                                        "FROM User u " +
                                        "LEFT JOIN Role r ON u.role_id = r.id " +
                                        "WHERE u.email = :email"
                        )
                        .bind("email", email)
                        .mapToBean(User.class)
                        .findFirst()
        );
    }

    /**
     * Cập nhật mật khẩu
     */
    public boolean updatePassword(String email, String newPassword) {
        try {
            String hashedPassword = PasswordUtil.hashPassword(newPassword);

            return jdbi.withHandle(handle -> {
                int result = handle.createUpdate(
                                "UPDATE User SET password = :password WHERE email = :email"
                        )
                        .bind("email", email)
                        .bind("password", hashedPassword)
                        .execute();

                return result > 0;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cập nhật thông tin user
     */
    public boolean updateUser(User user) {
        try {
            return jdbi.withHandle(handle -> {
                int result = handle.createUpdate(
                                "UPDATE User SET name = :name, phone = :phone, " +
                                        "gender = :gender, date_of_birth = :dob, avatar_img = :avatar " +
                                        "WHERE id = :id"
                        )
                        .bind("id", user.getId())
                        .bind("name", user.getName())
                        .bind("phone", user.getPhone())
                        .bind("gender", user.getGender())
                        .bind("dob", user.getDateOfBirth())
                        .bind("avatar", user.getAvatarImg())
                        .execute();

                return result > 0;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Cập nhật ảnh đại diện
     */

    public boolean updateAvatar(int userId, String avatarUrl) {
        String sql = "UPDATE user SET avatar_img = :avatar WHERE id = :id";

        int rows = jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind("avatar", avatarUrl)
                        .bind("id", userId)
                        .execute()
        );

        return rows > 0;
    }



}
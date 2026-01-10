package code.web.webgroup9.dao;

import code.web.webgroup9.model.Address;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class AddressDAO {

    private final Jdbi jdbi;

    public AddressDAO() {
        this.jdbi = BaseDao.get();
    }

    /**
     * Insert địa chỉ mới
     */
    public boolean insertAddress(Address address) {
        String sql = "INSERT INTO address (user_id, recipient_name, phone, email, " +
                "house_number, commune, district, address_detail, is_default) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        return jdbi.withHandle(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind(0, address.getUserId())
                    .bind(1, address.getRecipientName())
                    .bind(2, address.getPhone())
                    .bind(3, address.getEmail())
                    .bind(4, address.getHouse_number())
                    .bind(5, address.getCommune())
                    .bind(6, address.getDistrict())
                    .bind(7, address.getAddressDetail())
                    .bind(8, address.isDefault())
                    .execute();
            return rows > 0;
        });
    }

    /**
     * Lấy tất cả địa chỉ của user
     */
    public List<Address> getAddressByUserId(int userId) {
        String sql = "SELECT * FROM address WHERE user_id = ? ORDER BY is_default DESC, id DESC";

        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .bind(0, userId)
                    .map((rs, ctx) -> {
                        Address address = new Address();
                        address.setId(rs.getInt("id"));
                        address.setUserId(rs.getInt("user_id"));
                        address.setRecipientName(rs.getString("recipient_name"));
                        address.setPhone(rs.getString("phone"));
                        address.setEmail(rs.getString("email"));
                        address.setHouse_number(rs.getString("house_number"));
                        address.setCommune(rs.getString("commune"));
                        address.setDistrict(rs.getString("district"));
                        address.setAddressDetail(rs.getString("address_detail"));
                        address.setDefault(rs.getBoolean("is_default"));
                        return address;
                    })
                    .list();
        });
    }

    /**
     * Lấy địa chỉ theo ID
     */
    public Address getAddressById(int addressId) {
        String sql = "SELECT * FROM address WHERE id = ?";

        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .bind(0, addressId)
                    .map((rs, ctx) -> {
                        Address address = new Address();
                        address.setId(rs.getInt("id"));
                        address.setUserId(rs.getInt("user_id"));
                        address.setRecipientName(rs.getString("recipient_name"));
                        address.setPhone(rs.getString("phone"));
                        address.setEmail(rs.getString("email"));
                        address.setHouse_number(rs.getString("house_number"));
                        address.setCommune(rs.getString("commune"));
                        address.setDistrict(rs.getString("district"));
                        address.setAddressDetail(rs.getString("address_detail"));
                        address.setDefault(rs.getBoolean("is_default"));
                        return address;
                    })
                    .findOne()
                    .orElse(null);
        });
    }

    /**
     * Lấy địa chỉ mặc định của user
     */
    public Address getDefaultAddress(int userId) {
        String sql = "SELECT * FROM address WHERE user_id = ? AND is_default = true LIMIT 1";

        return jdbi.withHandle(handle -> {
            return handle.createQuery(sql)
                    .bind(0, userId)
                    .map((rs, ctx) -> {
                        Address address = new Address();
                        address.setId(rs.getInt("id"));
                        address.setUserId(rs.getInt("user_id"));
                        address.setRecipientName(rs.getString("recipient_name"));
                        address.setPhone(rs.getString("phone"));
                        address.setEmail(rs.getString("email"));
                        address.setHouse_number(rs.getString("house_number"));
                        address.setCommune(rs.getString("commune"));
                        address.setDistrict(rs.getString("district"));
                        address.setAddressDetail(rs.getString("address_detail"));
                        address.setDefault(rs.getBoolean("is_default"));
                        return address;
                    })
                    .findOne()
                    .orElse(null);
        });
    }

    /**
     * Cập nhật địa chỉ
     */
    public boolean updateAddress(Address address) {
        String sql = "UPDATE address SET recipient_name = ?, phone = ?, email = ?, " +
                "house_number = ?, commune = ?, district = ?, address_detail = ?, " +
                "is_default = ? WHERE id = ?";

        return jdbi.withHandle(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind(0, address.getRecipientName())
                    .bind(1, address.getPhone())
                    .bind(2, address.getEmail())
                    .bind(3, address.getHouse_number())
                    .bind(4, address.getCommune())
                    .bind(5, address.getDistrict())
                    .bind(6, address.getAddressDetail())
                    .bind(7, address.isDefault())
                    .bind(8, address.getId())
                    .execute();
            return rows > 0;
        });
    }

    /**
     * Xóa địa chỉ
     */
    public boolean deleteAddress(int addressId) {
        String sql = "DELETE FROM address WHERE id = ?";

        return jdbi.withHandle(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind(0, addressId)
                    .execute();
            return rows > 0;
        });
    }

    /**
     * Bỏ mặc định tất cả địa chỉ của user
     */
    public boolean unsetAllDefault(int userId) {
        String sql = "UPDATE address SET is_default = false WHERE user_id = ?";

        return jdbi.withHandle(handle -> {
            int rows = handle.createUpdate(sql)
                    .bind(0, userId)
                    .execute();
            return rows > 0;
        });
    }

    /**
     * Đặt địa chỉ làm mặc định
     */
    public boolean setAsDefault(int addressId, int userId) {
        return jdbi.inTransaction(handle -> {

            int reset = handle.createUpdate(
                            "UPDATE address SET is_default = false WHERE user_id = :userId")
                    .bind("userId", userId)
                    .execute();

            int set = handle.createUpdate(
                            "UPDATE address SET is_default = true WHERE id = :addressId AND user_id = :userId")
                    .bind("addressId", addressId)
                    .bind("userId", userId)
                    .execute();

            return set > 0; // true nếu update thành công
        });
    }

}
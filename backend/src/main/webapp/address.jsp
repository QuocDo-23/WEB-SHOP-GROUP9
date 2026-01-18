<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Icon -->
    <link rel="icon" type="image/png" sizes="32x32"
          href="https://i.postimg.cc/26JnYsPT/Logo-Photoroom.png">

    <!-- Bootstrap Icons -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">

    <!-- CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/profile.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">

    <title>Địa chỉ của tôi</title>

    <style>
        .message {
            padding: 12px 20px;
            margin-bottom: 20px;
            border-radius: 4px;
            font-weight: 500;
        }
        .message.success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .message.error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        .address-card {
            border: 1px solid #ddd;
            padding: 15px;
            margin-bottom: 15px;
            border-radius: 8px;
            background: #f9f9f9;
            position: relative;
        }
        .address-card.default {
            border-color: #28a745;
            background: #e8f5e9;
        }
        .address-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 10px;
        }
        .default-badge {
            background: #28a745;
            color: white;
            padding: 4px 12px;
            border-radius: 4px;
            font-size: 12px;
        }
        .address-actions {
            margin-top: 10px;
            display: flex;
            gap: 10px;
        }
        .address-actions button {
            padding: 6px 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }
        .btn-edit {
            background: #007bff;
            color: white;
        }
        .btn-delete {
            background: #dc3545;
            color: white;
        }
        .btn-default {
            background: #28a745;
            color: white;
        }
        .address-section {
            margin-top: 30px;
        }
        .add-address-btn {
            background: #007bff;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-bottom: 20px;
        }
        .form-container {
            display: none;
            margin-bottom: 20px;
            padding: 20px;
            background: #f0f0f0;
            border-radius: 8px;
            border: 1px solid #ddd;
        }
    </style>
</head>

<body>
<main>

    <!-- HEADER -->
    <div class="header">
        <div class="header-cont">
            <a href="${pageContext.request.contextPath}/index.jsp" class="logo-link">
                <img src="https://i.postimg.cc/26JnYsPT/Logo-Photoroom.png" alt="logo">
                <h1>Thông Tin</h1>
            </a>
            <a href="${pageContext.request.contextPath}/logout">
                <div class="logout-btn">Đăng xuất</div>
            </a>
        </div>
    </div>

    <!-- CONTENT -->
    <div class="container">
        <div class="main-content">

            <!-- SIDEBAR -->
            <div class="sidebar">
                <div class="edit-avatar">
                    <img src="${user.avatarImg != null ? user.avatarImg : 'https://i.postimg.cc/26JnYsPT/Logo-Photoroom.png'}"
                         alt="Avatar" class="profile-pic">

                    <div class="cont">
                        <label class="edit-avatar-btn">
                            Chỉnh Sửa Ảnh
                            <input type="file" name="avatar" accept="image/*">
                        </label>
                    </div>
                </div>

                <a href="${pageContext.request.contextPath}/profile" class="menu-item">
                    <i class="bi bi-person"></i> Thông Tin Cá Nhân
                </a>

                <a href="${pageContext.request.contextPath}/order" class="menu-item">
                    <i class="bi bi-clipboard-check"></i> Đơn Hàng
                </a>

                <div class="menu-item active">
                    <i class="bi bi-geo-alt"></i> Địa Chỉ
                </div>
            </div>

            <!-- ADDRESS CONTENT -->
            <div class="profile-container">
                <div class="content-area">

                    <!-- Hiển thị thông báo -->
                    <c:if test="${not empty message}">
                        <div class="message ${messageType}">
                                ${message}
                        </div>
                    </c:if>

                    <!-- QUẢN LÝ ĐỊA CHỈ -->
                    <div class="address-section" style="margin-top: 0;">
                        <h2 class="section-title">Địa Chỉ Của Tôi</h2>

                        <button class="add-address-btn" onclick="showAddAddressForm()">
                            <i class="bi bi-plus-circle"></i> Thêm Địa Chỉ Mới
                        </button>

                        <!-- Form thêm địa chỉ -->
                        <div id="addAddressForm" class="form-container">
                            <h3>Thêm Địa Chỉ Mới</h3>
                            <form action="${pageContext.request.contextPath}/address" method="post">
                                <input type="hidden" name="action" value="addAddress">

                                <div class="form-row">
                                    <div class="form-group">
                                        <label>Tên người nhận</label>
                                        <input type="text" name="recipientName" required>
                                    </div>
                                    <div class="form-group">
                                        <label>Số điện thoại</label>
                                        <input type="tel" name="phone" pattern="[0-9]{10,11}" required>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>Email</label>
                                    <input type="email" name="email" value="${user.email}">
                                </div>

                                <div class="form-row">
                                    <div class="form-group">
                                        <label>Số nhà</label>
                                        <input type="text" name="houseNumber" required>
                                    </div>
                                    <div class="form-group">
                                        <label>Phường/Xã</label>
                                        <input type="text" name="commune" required>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>Quận/Huyện</label>
                                    <input type="text" name="district" required>
                                </div>

                                <div class="form-group">
                                    <label>Chi tiết địa chỉ</label>
                                    <textarea name="addressDetail" rows="3" placeholder="Ví dụ: Gần chợ, đối diện trường học..."></textarea>
                                </div>

                                <div class="form-group">
                                    <label>
                                        <input type="checkbox" name="isDefault" value="true">
                                        Đặt làm địa chỉ mặc định
                                    </label>
                                </div>

                                <button type="submit" class="save-btn">Lưu Địa Chỉ</button>
                                <button type="button" class="save-btn" onclick="hideAddAddressForm()" style="background: #6c757d; margin-left: 10px;">Hủy</button>
                            </form>
                        </div>

                        <!-- Form sửa địa chỉ -->
                        <div id="editAddressForm" class="form-container">
                            <h3>Cập Nhật Địa Chỉ</h3>
                            <form action="${pageContext.request.contextPath}/address" method="post">
                                <input type="hidden" name="action" value="updateAddress">
                                <input type="hidden" name="addressId" id="edit_addressId">

                                <div class="form-row">
                                    <div class="form-group">
                                        <label>Tên người nhận</label>
                                        <input type="text" name="recipientName" id="edit_recipientName" required>
                                    </div>
                                    <div class="form-group">
                                        <label>Số điện thoại</label>
                                        <input type="tel" name="phone" id="edit_phone" pattern="[0-9]{10,11}" required>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>Email</label>
                                    <input type="email" name="email" id="edit_email">
                                </div>

                                <div class="form-row">
                                    <div class="form-group">
                                        <label>Số nhà</label>
                                        <input type="text" name="houseNumber" id="edit_houseNumber" required>
                                    </div>
                                    <div class="form-group">
                                        <label>Phường/Xã</label>
                                        <input type="text" name="commune" id="edit_commune" required>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>Quận/Huyện</label>
                                    <input type="text" name="district" id="edit_district" required>
                                </div>

                                <div class="form-group">
                                    <label>Chi tiết địa chỉ</label>
                                    <textarea name="addressDetail" id="edit_addressDetail" rows="3"></textarea>
                                </div>

                                <div class="form-group">
                                    <label>
                                        <input type="checkbox" name="isDefault" id="edit_isDefault" value="true">
                                        Đặt làm địa chỉ mặc định
                                    </label>
                                </div>

                                <button type="submit" class="save-btn">Cập Nhật</button>
                                <button type="button" class="save-btn" onclick="hideEditAddressForm()" style="background: #6c757d; margin-left: 10px;">Hủy</button>
                            </form>
                        </div>

                        <!-- Danh sách địa chỉ -->
                        <c:choose>
                            <c:when test="${empty addresses}">
                                <p style="color: #666; padding: 20px; text-align: center;">
                                    Bạn chưa có địa chỉ nào. Hãy thêm địa chỉ mới!
                                </p>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${addresses}" var="addr">
                                    <div class="address-card ${addr['default'] ? 'default' : ''}">
                                        <div class="address-header">
                                            <strong>${addr.recipientName}</strong>
                                            <c:if test="${addr['default']}">
                                                <span class="default-badge">Mặc định</span>
                                            </c:if>
                                        </div>
                                        <p><strong>SĐT:</strong> ${addr.phone}</p>
                                        <p><strong>Email:</strong> ${addr.email}</p>
                                        <p><strong>Địa chỉ:</strong> ${addr.house_number}, ${addr.commune}, ${addr.district}</p>
                                        <c:if test="${not empty addr.addressDetail}">
                                            <p><strong>Chi tiết:</strong> ${addr.addressDetail}</p>
                                        </c:if>

                                        <div class="address-actions">
                                            <!-- Nút Sửa -->
                                            <button type="button" class="btn-edit"
                                                    onclick="showEditAddressForm(
                                                            '${addr.id}',
                                                            '${addr.recipientName}',
                                                            '${addr.phone}',
                                                            '${addr.email}',
                                                            '${addr.house_number}',
                                                            '${addr.commune}',
                                                            '${addr.district}',
                                                            '${addr.addressDetail}',
                                                            ${addr['default']}
                                                            )">
                                                Sửa
                                            </button>

                                            <c:if test="${!addr['default']}">
                                                <form action="${pageContext.request.contextPath}/address" method="post" style="display: inline;">
                                                    <input type="hidden" name="action" value="setDefaultAddress">
                                                    <input type="hidden" name="addressId" value="${addr.id}">
                                                    <button type="submit" class="btn-default">Mặc định</button>
                                                </form>
                                            </c:if>

                                            <form action="${pageContext.request.contextPath}/address" method="post" style="display: inline;" onsubmit="return confirm('Bạn có chắc muốn xóa địa chỉ này?');">
                                                <input type="hidden" name="action" value="deleteAddress">
                                                <input type="hidden" name="addressId" value="${addr.id}">
                                                <button type="submit" class="btn-delete">Xóa</button>
                                            </form>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>

                </div>
            </div>

        </div>
    </div>

    <!-- FOOTER -->
    <jsp:include page="footer.jsp"/>

</main>

<script>
    function showAddAddressForm() {
        document.getElementById('addAddressForm').style.display = 'block';
        document.getElementById('editAddressForm').style.display = 'none';
    }

    function hideAddAddressForm() {
        document.getElementById('addAddressForm').style.display = 'none';
    }

    function showEditAddressForm(id, name, phone, email, house, commune, district, detail, isDefault) {
        document.getElementById('addAddressForm').style.display = 'none';
        document.getElementById('editAddressForm').style.display = 'block';

        // Fill data into form
        document.getElementById('edit_addressId').value = id;
        document.getElementById('edit_recipientName').value = name;
        document.getElementById('edit_phone').value = phone;
        document.getElementById('edit_email').value = email;
        document.getElementById('edit_houseNumber').value = house;
        document.getElementById('edit_commune').value = commune;
        document.getElementById('edit_district').value = district;
        document.getElementById('edit_addressDetail').value = detail;
        document.getElementById('edit_isDefault').checked = isDefault;

        // Scroll to form
        document.getElementById('editAddressForm').scrollIntoView({ behavior: 'smooth' });
    }

    function hideEditAddressForm() {
        document.getElementById('editAddressForm').style.display = 'none';
    }

    // Tự động ẩn thông báo sau 5 giây
    window.onload = function() {
        const message = document.querySelector('.message');
        if (message) {
            setTimeout(function() {
                message.style.display = 'none';
            }, 5000);
        }
    };
</script>

</body>
</html>
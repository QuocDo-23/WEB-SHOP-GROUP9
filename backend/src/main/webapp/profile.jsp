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

    <title>Thông tin của tôi</title>

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
        }
        .address-actions button {
            margin-right: 10px;
            padding: 6px 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
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

                <div class="menu-item active">
                    <i class="bi bi-person"></i> Thông Tin Cá Nhân
                </div>

                <a href="${pageContext.request.contextPath}/order" class="menu-item">
                    <i class="bi bi-clipboard-check"></i> Đơn Hàng
                </a>

                <a href="${pageContext.request.contextPath}/address" class="menu-item">
                    <i class="bi bi-house-check"></i> Địa chỉ
                </a>
            </div>

            <!-- PROFILE FORM -->
            <div class="profile-container">
                <div class="content-area">

                    <!-- Hiển thị thông báo -->
                    <c:if test="${not empty message}">
                        <div class="message ${messageType}">
                                ${message}
                        </div>
                    </c:if>

                    <h2 class="section-title">Thông Tin Cá Nhân</h2>

                    <form action="${pageContext.request.contextPath}/profile" method="post">
                        <input type="hidden" name="action" value="updateProfile">

                        <div class="form-row">
                            <div class="form-group">
                                <label>Họ và Tên</label>
                                <input type="text" name="fullName"
                                       value="${user.name}"
                                       placeholder="Nhập họ và tên"
                                       required>
                            </div>

                            <div class="form-group">
                                <label>Email</label>
                                <input type="email" name="email"
                                       value="${user.email}"
                                       placeholder="Nhập email"
                                       readonly
                                       style="background-color: #f0f0f0;">
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label>Giới Tính</label>
                                <select name="gender">
                                    <option value="">Chọn giới tính</option>
                                    <option value="male"   ${user.gender == 'male' ? 'selected' : ''}>Nam</option>
                                    <option value="female" ${user.gender == 'female' ? 'selected' : ''}>Nữ</option>
                                    <option value="other"  ${user.gender == 'other' ? 'selected' : ''}>Khác</option>
                                </select>

                            </div>

                            <div class="form-group">
                                <label>Ngày Sinh</label>
                                <input type="date" name="dob" value="${user.dateOfBirth}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label>Số Điện Thoại</label>
                            <input type="tel" name="phone"
                                   value="${user.phone}"
                                   placeholder="Nhập số điện thoại"
                                   pattern="[0-9]{10,11}">
                        </div>

                        <button type="submit" class="save-btn">Lưu Thay Đổi</button>
                    </form>
                </div>
            </div>
    </div>

    <!-- FOOTER -->
    <jsp:include page="footer.jsp"/>

</main>

<script>
    function showAddAddressForm() {
        document.getElementById('addAddressForm').style.display = 'block';
    }

    function hideAddAddressForm() {
        document.getElementById('addAddressForm').style.display = 'none';
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
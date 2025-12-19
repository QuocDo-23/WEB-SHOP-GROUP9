<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng Nhập - LightUp</title>
    <link rel="icon" type="image/png" sizes="32x32" href="https://i.postimg.cc/26JnYsPT/Logo-Photoroom.png">
    <link rel="stylesheet" href="<c:url value='/CSS/login.css'/>">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
</head>
<body>
<div class="container" id="container">
    <!-- Form Login -->
    <div class="form-container login-container">
        <form action="login" method="post">
            <h1>Đăng Nhập</h1>

            <!-- Success Messages -->
            <c:if test="${param.success == 'registered'}">
                <div class="alert alert-success">
                    <i class="bi bi-check-circle"></i> Đăng ký thành công! Vui lòng đăng nhập.
                </div>
            </c:if>

            <c:if test="${param.success == 'password_reset'}">
                <div class="alert alert-success">
                    <i class="bi bi-check-circle"></i> Đặt lại mật khẩu thành công!
                </div>
            </c:if>

            <!-- Error Messages -->
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    <i class="bi bi-exclamation-circle"></i> ${error}
                </div>
            </c:if>

            <div class="input-group">
                <label for="email"><i class="bi bi-envelope-fill"></i>Email</label>
                <input id="email" name="email" type="email" placeholder="Nhập email"
                       value="${param.email}" required>
            </div>

            <div class="input-group">
                <label for="password"><i class="bi bi-key-fill"></i>Mật Khẩu</label>
                <input id="password" name="password" type="password"
                       placeholder="Nhập mật khẩu" required>
            </div>

            <!-- Hidden redirect field -->
            <input type="hidden" name="redirect" value="${param.redirect}">

            <a href="forgot-password.jsp" class="forgot-password">Quên mật khẩu?</a>

            <button type="submit">Đăng Nhập</button>
        </form>
    </div>

    <!-- Overlay phải -->
    <div class="overlay-container">
        <div class="overlay">
            <div class="overlay-panel overlay-right">
                <img src="https://i.postimg.cc/26JnYsPT/Logo-Photoroom.png" alt="Logo">
                <h1 class="title">Xin Chào!</h1>
                <p>Nếu bạn chưa có tài khoản, hãy đăng ký tại đây</p>
                <a href="register.jsp">
                    <button class="ghost" id="register">
                        Đăng Ký <i class="bi bi-arrow-right"></i>
                    </button>
                </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
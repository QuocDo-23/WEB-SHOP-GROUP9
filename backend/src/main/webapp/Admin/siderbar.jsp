<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="sidebar">
    <div class="logo">
        <img src="https://i.postimg.cc/26JnYsPT/Logo-Photoroom.png" alt="logo">
        LightAdmin
    </div>

    <a href="${pageContext.request.contextPath}/admin/dashboard"
       class="menu-item ${currentPage == 'dashboard' ? 'active' : ''}">
        📊 Tổng Quan
    </a>

    <a href="${pageContext.request.contextPath}/admin/products"
       class="menu-item ${currentPage == 'products' ? 'active' : ''}">
        📦 Sản Phẩm
    </a>

    <a href="${pageContext.request.contextPath}/admin/orders"
       class="menu-item ${currentPage == 'orders' ? 'active' : ''}">
        🛒 Đơn Hàng
    </a>

    <a href="${pageContext.request.contextPath}/admin/customers"
       class="menu-item ${currentPage == 'customers' ? 'active' : ''}">
        👥 Khách Hàng
    </a>

    <a href="${pageContext.request.contextPath}/admin/news"
       class="menu-item ${currentPage == 'news' ? 'active' : ''}">
        📰 Tin Tức
    </a>

    <a href="${pageContext.request.contextPath}/admin/reviews"
       class="menu-item ${currentPage == 'reviews' ? 'active' : ''}">
        ⭐ Đánh Giá
    </a>

    <a href="${pageContext.request.contextPath}/admin/analytics"
       class="menu-item ${currentPage == 'analytics' ? 'active' : ''}">
        📈 Thống Kê
    </a>

    <button type="button" class="logout-btn"
            onclick="window.location.href='${pageContext.request.contextPath}/logout'">
        Đăng xuất
    </button>
</div>
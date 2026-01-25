<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý đánh giá - Admin Dashboard</title>

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/CSS/reviews-list.css">
</head>

<body>
<div class="container">

    <!-- ================= SIDEBAR ================= -->
    <div class="sidebar">
        <div class="logo">
            <img src="https://i.postimg.cc/26JnYsPT/Logo-Photoroom.png" alt="logo">
            LightAdmin
        </div>

        <a href="${pageContext.request.contextPath}/admin/dashboard" class="menu-item">📊 Tổng Quan</a>
        <a href="${pageContext.request.contextPath}/admin/products" class="menu-item">📦 Sản Phẩm</a>
        <a href="${pageContext.request.contextPath}/admin/orders" class="menu-item">🛒 Đơn Hàng</a>
        <a href="${pageContext.request.contextPath}/admin/customers" class="menu-item">👥 Khách Hàng</a>
        <a href="${pageContext.request.contextPath}/admin/news" class="menu-item">📰 Tin Tức</a>
        <a href="${pageContext.request.contextPath}/admin/reviews" class="menu-item active">⭐ Đánh Giá</a>
        <a href="${pageContext.request.contextPath}/admin/analytics" class="menu-item">📈 Thống Kê</a>

        <button class="logout-btn"
                onclick="window.location.href='${pageContext.request.contextPath}/logout'">
            Đăng xuất
        </button>
    </div>

    <!-- ================= MAIN ================= -->
    <div class="main-content">

        <!-- Header -->
        <div class="header">
            <h1>Quản lý đánh giá</h1>
            <div class="user-info">
                <div class="avatar">A</div>
                <div>
                    <div style="font-weight:600;">Admin</div>
                    <div style="font-size:12px;color:#718096;">Quản trị viên</div>
                </div>
            </div>
        </div>

        <!-- ================= STATISTICS ================= -->
        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-label">Tổng đánh giá</div>
                <div class="stat-value">${statistics.totalReviews}</div>
            </div>
            <div class="stat-card">
                <div class="stat-label">Điểm trung bình</div>
                <div class="stat-value" style="color:#f39c12;">
                    ${statistics.averageRating} ⭐
                </div>
            </div>
        </div>

        <!-- ================= REVIEW LIST ================= -->
        <div class="reviews-list-section">

            <c:forEach items="${reviews}" var="r">
                <div class="review-item">

                    <div class="review-header">
                        <div class="review-customer">
                            <div class="customer-avatar">
                                <i class="fas fa-user"></i>
                            </div>
                            <div class="customer-info">
                                <h4>${r.userName}</h4>
                                <p>User ID: ${r.userId}</p>
                            </div>
                        </div>

                        <div class="review-meta">
                            <div class="review-rating">
                                <c:forEach begin="1" end="5" var="i">
                                    <i class="${i <= r.rating ? 'fas fa-star' : 'far fa-star'}"></i>
                                </c:forEach>
                                <span class="rating-number">${r.rating}.0</span>
                            </div>
                            <span class="review-date">${r.date}</span>
                        </div>
                    </div>

                    <div class="review-product">
                        <div class="product-image">
                            <img src="${r.img}" alt="Product">
                        </div>
                        <div class="product-info">
                            <h5>Sản phẩm ID: ${r.productId}</h5>
                        </div>
                    </div>

                    <div class="review-content">
                        <p>${r.text}</p>
                    </div>

                    <div class="review-actions">

                        <!-- Duyệt / Ẩn -->
                        <form method="post"
                              action="${pageContext.request.contextPath}/admin/reviews"
                              style="display:inline;">
                            <input type="hidden" name="action" value="updateStatus"/>
                            <input type="hidden" name="reviewId" value="${r.id}"/>
                            <input type="hidden" name="status"
                                   value="${r.status == 1 ? 0 : 1}"/>

                            <button class="btn-action">
                                <c:choose>
                                    <c:when test="${r.status == 1}">
                                        Ẩn
                                    </c:when>
                                    <c:otherwise>
                                        Duyệt
                                    </c:otherwise>
                                </c:choose>
                            </button>
                        </form>

                        <!-- Xóa -->
                        <form method="post"
                              action="${pageContext.request.contextPath}/admin/reviews"
                              onsubmit="return confirm('Xóa đánh giá này?')"
                              style="display:inline;">
                            <input type="hidden" name="action" value="delete"/>
                            <input type="hidden" name="reviewId" value="${r.id}"/>
                            <button class="btn-action">Xóa</button>
                        </form>

                    </div>
                </div>
            </c:forEach>

        </div>
    </div>
</div>
</body>
</html>

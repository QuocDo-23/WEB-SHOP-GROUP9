<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sản Phẩm - LightUp</title>

    <!-- CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <link rel="icon" type="image/png" sizes="32x32" href="https://i.postimg.cc/26JnYsPT/Logo-Photoroom.png">
    <link rel="stylesheet" href="./CSS/cart.css">
    <link rel="stylesheet" href="./CSS/products.css">
    <link rel="stylesheet" href="./CSS/style.css">
    <link rel="stylesheet" href="./CSS/sub_login.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

</head>
<body>
<main>
    <!-- Navigation -->
    <jsp:include page="header.jsp"/>

    <!-- Breadcrumb -->
    <div class="link-page">
        <div class="containt_link">
            <a href="./index.jsp"><i class="bi bi-house"></i> Trang chủ </a>
            <span> /</span>
            <a href="">Sản Phẩm</a>
        </div>
    </div>

    <!-- Filter and Sort Bar -->
    <div class="filter-sort-bar">
        <!-- Bộ lọc bên trái -->
        <div class="filter-container">
            <div class="filter-dropdown">
                <div class="filter-toggle">
                    <i class="bi bi-funnel-fill"></i>
                    <span>Bộ lọc</span>
                </div>
            </div>

            <div class="filter-dropdown">
                <div class="filter-toggle">
                    <span>Giá</span>
                    <i class="bi bi-chevron-down"></i>
                </div>
                <ul class="filter-list">
                    <li><label><input type="checkbox"> Dưới 1.000.000₫</label></li>
                    <li><label><input type="checkbox"> 1.000.000₫ – 5.000.000₫</label></li>
                    <li><label><input type="checkbox"> 5.000.000₫ – 7.500.000₫</label></li>
                    <li><label><input type="checkbox"> 7.500.000₫ – 10.000.000₫</label></li>
                    <li><label><input type="checkbox"> Trên 10.000.000₫</label></li>
                </ul>
            </div>

            <div class="filter-dropdown">
                <div class="filter-toggle">
                    <span>Loại đèn</span>
                    <i class="bi bi-chevron-down"></i>
                </div>
                <ul class="filter-list">
                    <c:forEach var="category" items="${categories}">
                        <li><label><input type="checkbox"> ${category.name}</label></li>
                    </c:forEach>
                </ul>
            </div>
        </div>

        <!-- Sắp xếp bên phải -->
        <div class="sort-box">
            <div class="sort-toggle">
                <span>Sắp xếp</span>
                <i class="bi bi-chevron-down"></i>
            </div>
            <ul class="sort-list">
                <li>Bán chạy</li>
                <li>Giá thấp → cao</li>
                <li>Giá cao → thấp</li>
            </ul>
        </div>
    </div>

    <div class="search-overlay" id="searchOverlay" onclick="closeSearchPanel()"></div>

    <!-- Products Section -->
    <div class="container product-section">
        <c:forEach var="category" items="${categories}">
            <c:set var="products" value="${productsByCategory[category.id]}"/>
            <c:if test="${not empty products}">
                <div id="section-${category.id}" class="product-section">
                    <h3 class="sub-title">${category.name}</h3>

                    <div class="product-grid">
                        <c:forEach var="product" items="${products}">
                            <div class="product-card">
                                <div class="product-image">
                                    <c:if test="${product.hasDiscount()}">
                                        <div class="product-sale">-<fmt:formatNumber
                                                value="${product.discountRate}" maxFractionDigits="0"/>%
                                        </div>
                                    </c:if>
                                    <a href="product-detail?id=${product.id}">
                                        <img src="${not empty product.mainImage ? product.mainImage : 'default.jpg'}"
                                             alt="${product.description}" class="img-main">
                                        <c:if test="${not empty product.hoverImage}">
                                            <img src="${product.hoverImage}"
                                                 alt="${product.description}" class="img-hover">
                                        </c:if>
                                    </a>
                                </div>

                                <div class="product-info">
                                    <h3 class="product-name">
                                        <a href="product-detail?id=${product.id}">
                                                ${product.name}
                                        </a>
                                    </h3>
                                    <c:set var="rating" value="${product.review}"/>
                                    <div class="rating-box">
                                        <div class="star-rating">
                                            <span style="width:${rating * 20}%;"></span>
                                        </div>
                                    </div>
                                    <div class="product-action">
                                        <div class="product-prices">
                                            <span class="current-price">
                                                <fmt:formatNumber value="${product.discountedPrice}"
                                                                  pattern="#,###"/>₫
                                            </span>
                                            <c:if test="${product.hasDiscount()}">
                                                <span class="old-price">
                                                    <del><fmt:formatNumber value="${product.price}" pattern="#,###"/>₫</del>
                                                </span>
                                            </c:if>
                                        </div>
                                        <div class="cart-icon">
                                            <a href="add-cart?pID=${product.id}&quantity=1">
                                                <i class="bi bi-cart-check"></i>
                                            </a>
                                        </div>
                                    </div>

                                    <div class="product-meta">
                                        <span class="sold">Còn lại: ${product.inventoryQuantity}</span>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
        </c:forEach>

        <!-- Pagination -->
        <div class="pagination" id="pagination"></div>
    </div>

    <!-- Footer -->
    <jsp:include page="footer.jsp"/>

    <!-- Scroll to top button -->
    <a href="#">
        <button id="scrollToTopBtn">
            <i class="bi bi-chevron-up"></i>
        </button>
    </a>

    <!-- Shopping Cart Sidebar -->
    <%--    <jsp:include page="cart-sidebar.jsp" />--%>
</main>

<!-- JavaScript -->
<script src="./JS/products.js"></script>
</body>
</html>
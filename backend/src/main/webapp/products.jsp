<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<<<<<<< HEAD
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

=======
<%@ page import="java.util.*, java.text.NumberFormat, java.util.Locale" %>
<%@ page import="code.web.webgroup9.dao.ProductDAO, code.web.webgroup9.dao.CategoryDAO" %>
<%@ page import="code.web.webgroup9.model.ProductWithDetails, code.web.webgroup9.model.Category" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    // Khởi tạo DAO
    ProductDAO productDAO = new ProductDAO();
    CategoryDAO categoryDAO = new CategoryDAO();

    // Lấy dữ liệu
    List<ProductWithDetails> allProducts = productDAO.getAllProductsWithDetails();
    List<Category> categories = categoryDAO.getSubCategories();

    // Nhóm sản phẩm theo category
    Map<Integer, List<ProductWithDetails>> productsByCategory = new HashMap<>();
    for (ProductWithDetails product : allProducts) {
        int catId = product.getCategoryId();
        if (!productsByCategory.containsKey(catId)) {
            productsByCategory.put(catId, new ArrayList<>());
        }
        productsByCategory.get(catId).add(product);
    }
    Map<Integer, Integer> totalProductsByCategory = new HashMap<>();
    for (Category category : categories) {
        int total = productDAO.getProductsByCategory(category.getId()).size();
        totalProductsByCategory.put(category.getId(), total);
    }

    request.setAttribute("totalProductsByCategory", totalProductsByCategory);


    // Format tiền Việt Nam
    NumberFormat vndFormat = NumberFormat.getInstance(Locale.of("vi", "VN"));

    // Set vào request scope
    request.setAttribute("categories", categories);
    request.setAttribute("productsByCategory", productsByCategory);
    request.setAttribute("vndFormat", vndFormat);
%>

>>>>>>> 07c836a785bf7a8f4329f5442006a2aa41cce9d1
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        <c:choose>
            <c:when test="${not empty category}">
                ${category.name} - LightUp
            </c:when>
            <c:otherwise>
                Sản Phẩm - LightUp
            </c:otherwise>
        </c:choose>
    </title>

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
            <a href="products">Sản Phẩm</a>
            <c:if test="${not empty category}">
                <span> /</span>
                <a href="">${category.name}</a>
            </c:if>
        </div>
    </div>

    <!-- Category Header (chỉ hiển thị khi xem theo danh mục) -->
    <c:if test="${not empty category}">
        <div class="container">
            <div style="text-align: center; padding: 30px 0;">
                <h1 style="font-size: 2rem; font-weight: 600; color: #333; margin-bottom: 10px;">
                        ${category.name}
                </h1>
            </div>
        </div>
    </c:if>

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
                    <li><label><input type="checkbox" class="price-filter" data-min="0" data-max="1000000"> Dưới 1.000.000₫</label></li>
                    <li><label><input type="checkbox" class="price-filter" data-min="1000000" data-max="5000000"> 1.000.000₫ – 5.000.000₫</label></li>
                    <li><label><input type="checkbox" class="price-filter" data-min="5000000" data-max="7500000"> 5.000.000₫ – 7.500.000₫</label></li>
                    <li><label><input type="checkbox" class="price-filter" data-min="7500000" data-max="10000000"> 7.500.000₫ – 10.000.000₫</label></li>
                    <li><label><input type="checkbox" class="price-filter" data-min="10000000" data-max="999999999"> Trên 10.000.000₫</label></li>
                </ul>
            </div>

            <!-- Hiển thị bộ lọc loại đèn chỉ khi xem tất cả sản phẩm -->
            <c:if test="${empty category}">
                <div class="filter-dropdown">
                    <div class="filter-toggle">
                        <span>Loại đèn</span>
                        <i class="bi bi-chevron-down"></i>
                    </div>
                    <ul class="filter-list">
                        <c:forEach var="cat" items="${categories}">
                            <li><label><input type="checkbox" class="category-filter" value="${cat.id}"> ${cat.name}</label></li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>
        </div>

        <!-- Sắp xếp bên phải -->
        <div class="sort-box">
            <div class="sort-toggle">
                <span>Sắp xếp</span>
                <i class="bi bi-chevron-down"></i>
            </div>
            <ul class="sort-list">
                <li data-sort="popular">Bán chạy</li>
                <li data-sort="price-asc">Giá thấp → cao</li>
                <li data-sort="price-desc">Giá cao → thấp</li>
            </ul>
        </div>
    </div>

    <div class="search-overlay" id="searchOverlay" onclick="closeSearchPanel()"></div>

    <!-- Products Section -->
    <div class="container product-section">
<<<<<<< HEAD
        <c:choose>
            <c:when test="${not empty category}">
                <div class="product-grid" id="productGrid">
                    <c:forEach var="product" items="${products}">
                        <div class="product-card"
                             data-price="${product.discountedPrice}"
                             data-rating="${product.review}"
                             data-category="${product.categoryId}">
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
=======
        <c:forEach var="category" items="${categories}">
            <c:set var="products" value="${productsByCategory[category.id]}"/>
            <c:if test="${not empty products}">
                <div id="section-${category.id}" class="product-section">
                    <h3 class="sub-title">${category.name}</h3>

                    <div class="product-grid">
                        <c:forEach var="product" items="${products}" begin="0" end="7">

                        <div class="product-card">
                                <div class="product-image">
                                    <c:if test="${product.hasDiscount()}">
                                        <div class="product-sale">-<fmt:formatNumber
                                                value="${product.getDiscountRate()}" maxFractionDigits="0"/>%
                                        </div>
>>>>>>> 07c836a785bf7a8f4329f5442006a2aa41cce9d1
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

<<<<<<< HEAD
                                <div class="product-meta">
                                    <span class="sold">Còn lại: ${product.inventoryQuantity}</span>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
=======
                                <div class="product-info">
                                    <h3 class="product-name">
                                        <a href="product-detail.jsp?id=${product.getId()}">
                                                ${product.getName()}
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
                                                <fmt:formatNumber value="${product.getDiscountedPrice()}"
                                                                  type="currency" currencySymbol=""/>₫
                                            </span>
                                            <c:if test="${product.hasDiscount()}">
                                                <span class="old-price">
                                                    <del><fmt:formatNumber value="${product.getPrice()}" type="currency"
                                                                           currencySymbol=""/>₫</del>
                                                </span>
                                            </c:if>
                                        </div>
                                        <div class="cart-icon">
                                            <a href="cart?action=add&productId=${product.getId()}" class="open-cart">
                                                <i class="bi bi-cart-check"></i>
                                            </a>
                                        </div>
                                    </div>

                                    <div class="product-meta">
                                        <span class="sold">Còn lại: ${product.getInventoryQuantity()}</span>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                        <c:if test="${products.size() > 8}">
                            <div class="view-more-wrapper">
                                <a class="view-more-btn"
                                   href="products?action=category&id=${category.id}">
                                    Xem thêm
                                </a>
                            </div>
                        </c:if>

                    </div>
>>>>>>> 07c836a785bf7a8f4329f5442006a2aa41cce9d1
                </div>

                <!-- Empty State -->
                <c:if test="${empty products}">
                    <div style="text-align: center; padding: 60px 20px;">
                        <i class="bi bi-box" style="font-size: 4rem; color: #ccc;"></i>
                        <h3 style="margin-top: 20px; color: #666;">Không tìm thấy sản phẩm nào</h3>
                        <a href="products" style="display: inline-block; margin-top: 20px; padding: 10px 30px;
                           background: #333; color: white; text-decoration: none; border-radius: 5px;">
                            Quay lại trang sản phẩm
                        </a>
                    </div>
                </c:if>
            </c:when>

            <%-- Hiển thị tất cả sản phẩm theo danh mục (khi không có category) --%>
            <c:otherwise>
                <c:forEach var="cat" items="${categories}">
                    <c:set var="products" value="${productsByCategory[cat.id]}"/>
                    <c:if test="${not empty products}">
                        <div id="section-${cat.id}" class="product-section" data-category="${cat.id}">
                            <h3 class="sub-title">${cat.name}</h3>
                            <a class="view-more-text"
                               href="cate_products?action=category&id=${cat.id}">
                                Xem thêm
                            </a>

                            <div class="product-grid">
                                <c:forEach var="product" items="${products}">
                                    <div class="product-card"
                                         data-price="${product.discountedPrice}"
                                         data-rating="${product.review}"
                                         data-category="${product.categoryId}">
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
            </c:otherwise>
        </c:choose>

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
<<<<<<< HEAD
=======

    <!-- Shopping Cart Sidebar -->
    <%--    <jsp:include page="cart-sidebar.jsp" />--%>
>>>>>>> 07c836a785bf7a8f4329f5442006a2aa41cce9d1
</main>

<!-- JavaScript -->
<script src="./JS/products.js"></script>
</body>
</html>
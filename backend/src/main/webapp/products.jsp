<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

    // Format tiền Việt Nam
    NumberFormat vndFormat = NumberFormat.getInstance(Locale.of("vi", "VN"));

    // Set vào request scope
    request.setAttribute("categories", categories);
    request.setAttribute("productsByCategory", productsByCategory);
    request.setAttribute("vndFormat", vndFormat);
%>

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
    <nav>
        <div class="nav_head">
            <a href="./index.html">
                <div class="logo">
                    <img src="https://i.postimg.cc/26JnYsPT/Logo-Photoroom.png" alt="Logo">
                </div>
            </a>

            <div class="nav-container">
                <ul class="nav-links">
                    <li><a href="./index.html">TRANG CHỦ</a></li>
                    <li><a href="./about.html">GIỚI THIỆU</a></li>
                    <li><a href="./products.html" class="active">SẢN PHẨM <i class="bi bi-caret-down-fill"></i></a>

                        <div class="sub-item">
                            <div class="contant-sub">
                                <h2>Danh mục sản phẩm</h2>
                                <div class="cont-item">
                                    <div class="product_item">
                                        <div class="product_cont">
                                            <img src="https://cdn-icons-png.freepik.com/512/4452/4452959.png?uid=R166547988&ga=GA1.1.166031633.1762096545"
                                                 alt="">
                                            <a class="item" href="#denban-section">Đèn Bàn</a>
                                        </div>
                                        <span class="arrow">›</span>
                                    </div>
                                    <div class="product_item">
                                        <div class="product_cont">
                                            <img src="https://cdn-icons-png.freepik.com/512/1237/1237786.png?uid=R166547988&ga=GA1.1.166031633.1762096545"
                                                 alt="">
                                            <a class="item" href="#denchum-section">Đèn Chùm</a>
                                        </div>
                                        <span class="arrow">›</span>
                                    </div>
                                    <div class="product_item">
                                        <div class="product_cont">
                                            <img src="https://cdn-icons-png.freepik.com/512/18200/18200846.png?uid=R166547988&ga=GA1.1.166031633.1762096545"
                                                 alt="">
                                            <a class="item" href="#denoptran-section">Đèn Ốp Trần</a>
                                        </div>
                                        <span class="arrow">›</span>
                                    </div>
                                    <div class="product_item">
                                        <div class="product_cont">
                                            <img src="https://cdn-icons-png.freepik.com/512/6202/6202698.png?uid=R166547988&ga=GA1.1.166031633.1762096545"
                                                 alt="">
                                            <a class="item" href="#denvach-section">Đèn Vách</a>
                                        </div>
                                        <span class="arrow">›</span>
                                    </div>
                                    <div class="product_item">
                                        <div class="product_cont">
                                            <img src="https://cdn-icons-png.freepik.com/512/3719/3719943.png?uid=R166547988&ga=GA1.1.166031633.1762096545"
                                                 alt="">
                                            <a class="item" href="#dentha-section">Đèn Thả</a>
                                        </div>
                                        <span class="arrow">›</span>
                                    </div>
                                    <div class="product_item">
                                        <div class="product_cont">
                                            <img src="https://cdn-icons-png.freepik.com/512/896/896351.png?uid=R166547988&ga=GA1.1.166031633.1762096545"
                                                 alt="">
                                            <a class="item" href="#dencay-section">Đèn Cây</a>
                                        </div>
                                        <span class="arrow">›</span>
                                    </div>
                                    <div class="product_item">
                                        <div class="product_cont">
                                            <img src="https://cdn-icons-png.freepik.com/512/3724/3724416.png?uid=R166547988&ga=GA1.1.166031633.1762096545"
                                                 alt="">
                                            <a class="item" href="#denquat-section">Đèn Quạt</a>
                                        </div>
                                        <span class="arrow">›</span>
                                    </div>
                                    <div class="product_item">
                                        <div class="product_cont">
                                            <img src="https://cdn-icons-png.freepik.com/512/7402/7402062.png?uid=R166547988&ga=GA1.1.166031633.1762096545"
                                                 alt="">
                                            <a class="item" href="#bongden-section">Bóng Đèn</a>
                                        </div>
                                        <span class="arrow">›</span>
                                    </div>

                                </div>

                            </div>

                        </div>

                    </li>
                    <li><a href="./news.html">TIN TỨC</a></li>
                    <li><a href="./contact.html">LIÊN HỆ</a></li>
                </ul>
                <div class="nav_r" id="nav_r">
                    <!-- Ô SEARCH TRÊN NAV -->
                    <div class="search-icon" onclick="openSearchBar()">
                        <input type="text" placeholder="Tìm kiếm ">
                        <i class="bi bi-search"></i>
                    </div>

                    <!-- NÚT QUAY LẠI ẨN LÚC BÌNH THƯỜNG -->
                    <i class="bi bi-x back-button-nav" onclick="closeSearchBar()"></i>


                    <!-- khung tìm kiếm -->
                    <div class="search-panel" id="searchPanel">
                        <div class="search-content">
                            <div class="section-header_search">
                                <h2 class="section-title-search">Gợi ý tìm kiếm</h2>
                            </div>

                            <a href="./product-detail.html" class="search-item">
                                <span class="arrow-icon"><i class="bi bi-search"></i></span>
                                <div class="item-content">
                                    <div class="item-name">Đèn để bàn phòng khách phong cách tối giản KLS0049</div>
                                </div>
                            </a>

                            <a href="./product-detail.html" class="search-item">
                                <span class="arrow-icon"><i class="bi bi-search"></i></span>
                                <div class="item-content">
                                    <div class="item-name">Đèn bàn đọc sách làm việc hiện đại tối giản FLM5145</div>
                                </div>
                            </a>

                            <a href="./product-detail.html" class="search-item">
                                <span class="arrow-icon"><i class="bi bi-search"></i></span>
                                <div class="item-content">
                                    <div class="item-name">Đèn bàn bằng gốm sứ nhiều màu HYJ0029</div>
                                </div>
                            </a>

                        </div>
                    </div>

                    <!-- ICON SHOP -->
                    <div class="icon-group">
                        <div class="shop" id="shop">
                            <a href="cart_detail.html"><i class="bi bi-cart-check" title="Giỏ hàng"></i></a>
                            <div class="item-quantity">2</div>
                        </div>

                        <!-- ICON LOGIN -->
                        <div class="login" id="login">
                            <a href="./login.html"><i class="bi bi-person-circle" title="Tài Khoản"></i></a>
                            <div class="sub_login">
                                <div class="container-sub-login">
                                    <a href="./profile.html"><i class="bi bi-person"></i> Tài khoản</a>
                                    <a href="./order.html"><i class="bi bi-clipboard-check"></i>Đơn Hàng</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </nav>

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
            <c:set var="products" value="${productsByCategory[category.id]}" />
            <c:if test="${not empty products}">
                <div id="section-${category.id}" class="product-section">
                    <h3 class="sub-title">${category.name}</h3>

                    <div class="product-grid">
                        <c:forEach var="product" items="${products}">
                            <div class="product-card">
                                <div class="product-image">
                                    <c:if test="${product.hasDiscount()}">
                                        <div class="product-sale">-<fmt:formatNumber value="${product.getDiscountRate()}" maxFractionDigits="0" />%</div>
                                    </c:if>
                                    <a href="product-detail.jsp?id=${product.getId()}">
                                        <img src="${not empty product.getMainImage() ? product.getMainImage() : 'default.jpg'}"
                                             alt="${product.getDescription()}" class="img-main">
                                        <c:if test="${not empty product.getHoverImage()}">
                                            <img src="${product.getHoverImage()}"
                                                 alt="${product.getDescription()}" class="img-hover">
                                        </c:if>
                                    </a>
                                </div>

                                <div class="product-info">
                                    <h3 class="product-name">
                                        <a href="product-detail.jsp?id=${product.getId()}">
                                            ${product.getName()}
                                        </a>
                                    </h3>


                                    <c:set var="rating" value="${product.review}" />

                                    <div class="rating-box">
                                        <div class="star-rating">
                                            <span style="width:${rating * 20}%;"></span>
                                        </div>
                                    </div>


                                    <div class="product-action">
                                        <div class="product-prices">
                                            <span class="current-price">
                                                <fmt:formatNumber value="${product.getDiscountedPrice()}" type="currency" currencySymbol="" />₫
                                            </span>
                                            <c:if test="${product.hasDiscount()}">
                                                <span class="old-price">
                                                    <del><fmt:formatNumber value="${product.getPrice()}" type="currency" currencySymbol="" />₫</del>
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
                    </div>
                </div>
            </c:if>
        </c:forEach>

        <!-- Pagination -->
        <div class="pagination" id="pagination"></div>
    </div>

    <!-- Footer -->
<%--    <jsp:include page="footer.jsp" />--%>

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
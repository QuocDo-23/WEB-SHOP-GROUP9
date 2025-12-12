<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.*, code.web.webgroup9.dao.*, code.web.webgroup9.model.*" %>
<%@ page import="code.web.webgroup9.model.ReviewStatistics" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    // Lấy product ID từ parameter
    String idParam = request.getParameter("id");
    if (idParam == null) {
        response.sendRedirect("products.jsp");
        return;
    }

    int productId = Integer.parseInt(idParam);

    // Khởi tạo DAOs
    ProductDAO productDAO = new ProductDAO();
    ReviewDAO reviewDAO = new ReviewDAO();
    ImageDAO imageDAO = new ImageDAO();

    // Lấy thông tin sản phẩm
    Optional<ProductWithDetails> productOpt = productDAO.getProductById(productId);
    if (!productOpt.isPresent()) {
        response.sendRedirect("products.jsp");
        return;
    }

    ProductWithDetails product = productOpt.get();

    // Lấy hình ảnh
    List<Image> images = imageDAO.getImagesByProductId(productId);

    // Lấy reviews
    List<Review> reviews = reviewDAO.getReviewsByProductId(productId);
    ReviewStatistics stats = reviewDAO.getReviewStatistics(productId);

    // Lấy sản phẩm liên quan (cùng category)
    List<ProductWithDetails> relatedProducts = productDAO.getProductsByCategory(product.getCategoryId());
    relatedProducts.removeIf(p -> p.getId() == productId); // Remove current product
    if (relatedProducts.size() > 4) {
        relatedProducts = relatedProducts.subList(0, 4);
    }

    // Format tiền
    NumberFormat vndFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

    // Set attributes
    request.setAttribute("product", product);
    request.setAttribute("images", images);
    request.setAttribute("reviews", reviews);
    request.setAttribute("stats", stats);
    request.setAttribute("relatedProducts", relatedProducts);
    request.setAttribute("vndFormat", vndFormat);
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${product.name} - LightUp</title>

    <link rel="icon" type="image/png" sizes="32x32" href="https://i.postimg.cc/26JnYsPT/Logo-Photoroom.png">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="./CSS/product-detail.css">
    <link rel="stylesheet" href="./CSS/style.css">
    <link rel="stylesheet" href="./CSS/sub_login.css">
    <link rel="stylesheet" href="CSS/products.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
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
<div class="link-page" id="up">
    <div class="containt_link">
        <a href="./index.jsp"><i class="bi bi-house"></i> Trang chủ </a>
        <span> /</span>
        <a href="./products.jsp"> Sản Phẩm </a>
        <span> /</span>
        <a href="">Chi tiết sản phẩm</a>
    </div>
</div>

<!-- Product Detail Section -->
<div class="row">
    <!-- Left: Product Images -->
    <div class="content_img_l">
        <div class="cont">
            <div class="img_main">
                <img src="${images[0].img}" alt="${product.description}" id="mainImage">
            </div>
            <div class="img_down">
                <c:forEach var="img" items="${images}" varStatus="status">
                    <img src="${img.img}"
                         alt="${product.description}"
                         onclick="changeMainImage('${img.img}')"
                         class="${status.index == 0 ? 'active' : ''}">
                </c:forEach>
            </div>
        </div>
    </div>

    <!-- Right: Product Info -->
    <div class="product-image-summary">
        <div class="content_products">
            <div class="summary-inner">
                <h1 class="product_title">${product.description}</h1>

                <div class="product-rating">
                    <c:set var="rating" value="${product.review}" />
                        <div class="star-rating">
                            <span style="width:${rating * 20}%;"></span>
                        </div>

                    <div class="product-prices">
                            <span class="current-price">
                                <%= vndFormat.format(product.getDiscountedPrice()) %>₫
                            </span>
                        <% if (product.hasDiscount()) { %>
                        <span class="old-price">
                                    <del><%= vndFormat.format(product.getPrice()) %>₫</del>
                                </span>
                        <% } %>
                    </div>

                    <!-- Product Attributes Table -->
                    <table class="product-attributes">
                        <tbody>
                        <c:if test="${not empty product.warranty}">
                            <tr class="product-attributes-item">
                                <th class="product-item-label">
                                    <span class="wd-attr-name">Bảo hành</span>
                                </th>
                                <td class="product-item-value">
                                    <p>${product.warranty}</p>
                                </td>
                            </tr>
                        </c:if>

                        <c:if test="${not empty product.material}">
                            <tr class="product-attributes-item">
                                <th class="product-item-label">
                                    <span class="wd-attr-name">Vật liệu</span>
                                </th>
                                <td class="product-item-value">
                                    <p>${product.material}</p>
                                </td>
                            </tr>
                        </c:if>

                        <c:if test="${not empty product.voltage}">
                            <tr class="product-attributes-item">
                                <th class="product-item-label">
                                    <span class="wd-attr-name">Hiệu điện thế</span>
                                </th>
                                <td class="product-item-value">
                                    <p>${product.voltage}</p>
                                </td>
                            </tr>
                        </c:if>

                        <c:if test="${not empty product.dimensions}">
                            <tr class="product-attributes-item">
                                <th class="product-item-label">
                                    <span class="wd-attr-name">Kích thước</span>
                                </th>
                                <td class="product-item-value">
                                    <p>${product.dimensions}</p>
                                </td>
                            </tr>
                        </c:if>

                        <c:if test="${not empty product.type}">
                            <tr class="product-attributes-item">
                                <th class="product-item-label">
                                    <span class="wd-attr-name">Loại bóng</span>
                                </th>
                                <td class="product-item-value">
                                    <p>${product.type}</p>
                                </td>
                            </tr>
                        </c:if>

                        <c:if test="${not empty product.color}">
                            <tr class="product-item-label">
                                <th class="product-item-label">
                                    <span class="wd-attr-name">Màu sắc</span>
                                </th>
                                <td class="product-item-value">
                                    <p>${product.color}</p>
                                </td>
                            </tr>
                        </c:if>

                        <c:if test="${not empty product.style}">
                            <tr class="product-attributes-item">
                                <th class="product-item-label">
                                    <span class="wd-attr-name">Phong cách</span>
                                </th>
                                <td class="product-item-value">
                                    <p>${product.style}</p>
                                </td>
                            </tr>
                        </c:if>
                        </tbody>
                    </table>

                    <!-- Quantity Section -->
                    <form action="cart" method="post" id="addToCartForm">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="productId" value="${product.id}">

                        <div class="quantity-section">
                            <label class="quantity-label">Số lượng</label>
                            <div class="quantity-control">
                                <button type="button" class="quantity-btn" onclick="decreaseQty()">-</button>
                                <input type="number" class="quantity-input" name="quantity"
                                       id="quantity" value="1" min="1" max="${product.inventoryQuantity}">
                                <button type="button" class="quantity-btn" onclick="increaseQty()">+</button>
                            </div>
                            <p class="stock-info">Còn lại: ${product.inventoryQuantity} sản phẩm</p>
                        </div>

                        <!-- Action Buttons -->
                        <div class="action-buttons">
                            <button type="submit" class="btn btn-add-cart">
                                THÊM VÀO GIỎ HÀNG
                            </button>
                            <button type="button" class="btn btn-buy-now" onclick="buyNow()">
                                THANH TOÁN
                            </button>
                        </div>
                    </form>

                    <a href="./contact.jsp">
                        <button class="btn btn-contact" style="width: 100%;">
                            LIÊN HỆ
                        </button>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Product Tabs: Description & Reviews -->
<div class="product-tabs-wrapper">
    <div class="container">
        <div class="row">
            <div class="col-12 poduct-tabs-inner">
                <div class="wd-accordion">
                    <!-- Description Tab -->
                    <div class="wd-accordion-item">
                        <div class="wd-accordion-title" data-target="description">
                            <div class="wd-accordion-title-text">
                                <span>Mô tả</span>
                            </div>
                            <span class="wd-accordion-opener"></span>
                        </div>

                        <div class="wd-accordion-content" id="tab-description">
                            <div class="wc-tab-inner">
                                <h4>Chi tiết về ${product.description}</h4>

                                <c:if test="${not empty images && fn:length(images) > 0}">
                                    <div class="image-grid">
                                        <c:forEach var="img" items="${images}" begin="0" end="3">
                                            <img src="${img.img}" alt="${product.description}">
                                        </c:forEach>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>

                    <!-- Reviews Tab -->
                    <div class="wd-single-reviews">
                        <div id="reviews" class="woocommerce-Reviews">
                            <div id="comments">
                                <div class="reviews-title">
                                    ${stats.totalReviews} đánh giá cho <span>${product.description}</span>
                                </div>

                                <div class="star_box">
                                    <div class="star-average">
                                        <div class="product-rating">
                                                <span class="star_average">
                                                    <fmt:formatNumber value="${stats.averageRating}" maxFractionDigits="1"/>
                                                    <i class="devvn-star">★</i>
                                                </span>
                                            <div class="star-rating">
                                                <span style="width:${stats.averageRating * 20}%"></span>
                                            </div>
                                            <strong>Đánh giá trung bình</strong>
                                        </div>
                                    </div>

                                    <div class="star_box_left">
                                        <div class="reviews_bar">
                                            <c:forEach var="star" begin="1" end="5">
                                                <c:set var="starReversed" value="${6 - star}"/>
                                                <div class="devvn_review_row">
                                                    <span class="devvn_stars_value">${starReversed}<i class="devvn-star">★</i></span>
                                                    <span class="devvn_rating_bar">
                                                            <span style="background-color: #eee" class="devvn_scala_rating">
                                                                <span class="devvn_perc_rating"
                                                                      style="width: ${stats.getPercentage(starReversed)}%; background-color: #f5a623"></span>
                                                            </span>
                                                        </span>
                                                    <span class="devvn_num_reviews">
                                                            <b><fmt:formatNumber value="${stats.getPercentage(starReversed)}" maxFractionDigits="0"/>%</b>
                                                        </span>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>

                                    <div class="star_box_right">
                                        <a href="#" title="Đánh giá ngay" class="btn-reviews-now">Đánh giá ngay</a>
                                    </div>
                                </div>

                                <!-- Review List -->
                                <ol class="commentlist">
                                    <c:forEach var="review" items="${reviews}">
                                        <li class="review" id="review-${review.id}">
                                            <div class="comment_container devvn_review_box">
                                                <div class="comment-text">
                                                    <div class="devvn_review_top">
                                                        <p class="meta">
                                                            <strong class="woocommerce-review__author">${review.userName}</strong>
                                                            <em class="woocommerce-review__verified verified">Đã mua tại LightUp</em>
                                                        </p>
                                                    </div>

                                                    <div class="devvn_review_mid">
                                                        <div class="star-rating">
                                                            <span style="width:${review.rating * 20}%"></span>
                                                        </div>
                                                        <div class="description">
                                                            <p>${review.text}</p>
                                                        </div>
                                                    </div>

                                                    <div class="devvn_review_bottom">
                                                        <time class="woocommerce-review__published-date"
                                                              datetime="${review.date}">
                                                            <fmt:formatDate value="${review.date}" pattern="dd/MM/yyyy"/>
                                                        </time>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ol>
                            </div>
                        </div>
                    </div>

                    <!-- Related Products -->
                    <div class="related-products">
                        <h3 class="title slider-title">Sản phẩm tương tự</h3>

                        <div class="product-grid">
                            <c:forEach var="relatedProduct" items="${relatedProducts}">
                                <div class="product-card">
                                    <div class="product-image">
                                        <c:if test="${relatedProduct.hasDiscount()}">
                                            <div class="product-sale">
                                                -<fmt:formatNumber value="${relatedProduct.discountRate}" maxFractionDigits="0"/>%
                                            </div>
                                        </c:if>
                                        <a href="product-detail.jsp?id=${relatedProduct.id}">
                                            <img src="${relatedProduct.mainImage}"
                                                 alt="${relatedProduct.description}" class="img-main">
                                            <c:if test="${not empty relatedProduct.hoverImage}">
                                                <img src="${relatedProduct.hoverImage}"
                                                     alt="${relatedProduct.description}" class="img-hover">
                                            </c:if>
                                        </a>
                                    </div>

                                    <div class="product-info">
                                        <h3 class="product-name">
                                            <a href="product-detail.jsp?id=${relatedProduct.id}">
                                                    ${relatedProduct.description}
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
                                            <span class="sold">Còn lại: ${relatedProduct.inventoryQuantity}</span>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Footer -->
<%--<jsp:include page="footer.jsp" />--%>

<!-- Scroll to Top Button -->
<a href="#up">
    <button id="scrollToTopBtn">
        <i class="bi bi-chevron-up"></i>
    </button>
</a>

<!-- Review Box Overlay -->
<div class="overlay"></div>
<div class="review-box">
    <button class="close-btn">✖</button>
    <div class="review-title">Đánh giá sản phẩm</div>

    <form action="review" method="post" id="reviewForm">
        <input type="hidden" name="productId" value="${product.id}">

        <div class="review-stars">
            <label>Bạn cảm thấy như thế nào về sản phẩm?</label>
            <div class="stars">
                <div class="star-item">
                    <input type="radio" name="rating" value="1" id="star1" required>
                    <label for="star1">★ <small>Rất tệ</small></label>
                </div>
                <div class="star-item">
                    <input type="radio" name="rating" value="2" id="star2">
                    <label for="star2">★ <small>Không tệ</small></label>
                </div>
                <div class="star-item">
                    <input type="radio" name="rating" value="3" id="star3">
                    <label for="star3">★ <small>Trung bình</small></label>
                </div>
                <div class="star-item">
                    <input type="radio" name="rating" value="4" id="star4">
                    <label for="star4">★ <small>Tốt</small></label>
                </div>
                <div class="star-item">
                    <input type="radio" name="rating" value="5" id="star5">
                    <label for="star5">★ <small>Tuyệt vời</small></label>
                </div>
            </div>
        </div>

        <textarea name="comment" placeholder="Mời bạn chia sẻ cảm nhận về sản phẩm..." required></textarea>
        <button type="submit" class="submit-btn">Gửi đánh giá</button>
    </form>
</div>

<!-- JavaScript -->
<script>
    const maxQuantity = ${product.inventoryQuantity};

    function changeMainImage(src) {
        document.getElementById('mainImage').src = src;

        // Update active state
        document.querySelectorAll('.img_down img').forEach(img => {
            img.classList.remove('active');
        });
        event.target.classList.add('active');
    }

    function decreaseQty() {
        const input = document.getElementById('quantity');
        if (input.value > 1) {
            input.value = parseInt(input.value) - 1;
        }
    }

    function increaseQty() {
        const input = document.getElementById('quantity');
        if (input.value < maxQuantity) {
            input.value = parseInt(input.value) + 1;
        }
    }

    function buyNow() {
        document.getElementById('addToCartForm').action = 'checkout.jsp';
        document.getElementById('addToCartForm').submit();
    }

    // Review box
    document.querySelector('.btn-reviews-now').addEventListener('click', function(e) {
        e.preventDefault();
        document.querySelector('.overlay').classList.add('active');
        document.querySelector('.review-box').classList.add('active');
    });

    document.querySelector('.close-btn').addEventListener('click', function() {
        document.querySelector('.overlay').classList.remove('active');
        document.querySelector('.review-box').classList.remove('active');
    });
</script>
<script src="./JS/product_detail.js"></script>
<script src="./JS/index.js"></script>
</body>
</html>
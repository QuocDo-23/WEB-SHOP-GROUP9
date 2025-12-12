<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.*, code.web.webgroup9.dao.*,code.web.webgroup9.model.*" %>
<%@ page import="code.web.webgroup9.model.ProductWithDetails" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    // Khởi tạo DAOs
    ProductDAO productDAO = new ProductDAO();
    CategoryDAO categoryDAO = new CategoryDAO();

    // Lấy sản phẩm nổi bật (có discount hoặc review cao)
    List<ProductWithDetails> featuredProducts = productDAO.getAllProductsWithDetails();

    // Sắp xếp theo review và lấy 8 sản phẩm đầu
    featuredProducts.sort((p1, p2) -> {
        if (p2.getReview() == null) return -1;
        if (p1.getReview() == null) return 1;
        return p2.getReview().compareTo(p1.getReview());
    });

    if (featuredProducts.size() > 8) {
        featuredProducts = featuredProducts.subList(0, 8);
    }

    // Lấy categories
    List<Category> categories = categoryDAO.getSubCategories();


    // Format tiền
    NumberFormat vndFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

    // Set attributes
    request.setAttribute("featuredProducts", featuredProducts);
    request.setAttribute("categories", categories);
    request.setAttribute("vndFormat", vndFormat);
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>LightUP - Đèn Và Thiết Bị Chiếu Sáng</title>

    <link rel="icon" type="image/png" sizes="32x32" href="https://i.postimg.cc/26JnYsPT/Logo-Photoroom.png">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="./CSS/style.css">
    <link rel="stylesheet" href="./CSS/sub_login.css">
    <link rel="stylesheet" href="./CSS/products.css">
    <link rel="stylesheet" href="CSS/cart.css">
    <link href='https://fonts.googleapis.com/css?family=Monsieur La Doulaise' rel='stylesheet'>
    <link href='https://fonts.googleapis.com/css?family=Literata' rel='stylesheet'>
    <link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet'>
</head>

<body>
<main>
    <!-- Navigation -->
    <nav>
        <div class="nav_head">
            <a href="./index.jsp">
                <div class="logo">
                    <img src="https://i.postimg.cc/26JnYsPT/Logo-Photoroom.png" alt="Logo">
                </div>
            </a>

            <div class="nav-container">
                <ul class="nav-links">
                    <li><a href="./index.jsp" class="active">TRANG CHỦ</a></li>
                    <li><a href="./about.jsp">GIỚI THIỆU</a></li>
                    <li><a href="./products.jsp">SẢN PHẨM <i class="bi bi-caret-down-fill"></i></a>
                        <div class="sub-item">
                            <div class="contant-sub">
                                <h2>Danh mục sản phẩm</h2>
                                <div class="cont-item">
                                    <c:forEach var="category" items="${categories}">
                                        <div class="product_item">
                                            <div class="product_cont">
                                                <img src="${category.imgCate}" alt="${category.name}" />
                                                <a class="item" href="./products.jsp#section-${category.id}">${category.name}</a>
                                            </div>
                                            <span class="arrow">›</span>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li><a href="./news.jsp">TIN TỨC</a></li>
                    <li><a href="./contact.jsp">LIÊN HỆ</a></li>
                </ul>

                <div class="nav_r" id="nav_r">
                    <!-- Search Box -->
                    <div class="search-icon" onclick="openSearchBar()">
                        <input type="text" placeholder="Tìm kiếm " id="searchInput">
                        <i class="bi bi-search"></i>
                    </div>
                    <i class="bi bi-x back-button-nav" onclick="closeSearchBar()"></i>

                    <!-- Search Panel -->
                    <div class="search-panel" id="searchPanel">
                        <div class="search-content" id="searchResults">
                            <div class="section-header_search">
                                <h2 class="section-title-search">Gợi ý tìm kiếm</h2>
                            </div>
                            <!-- Search results will be loaded here via AJAX -->
                        </div>
                    </div>

                    <!-- Icons -->
                    <div class="icon-group">
                        <div class="shop" id="shop">
                            <a href="cart-detail.jsp">
                                <i class="bi bi-cart-check" title="Giỏ hàng"></i>
                                <div class="item-quantity" id="cartCount">0</div>
                            </a>
                        </div>
                        <div class="login" id="login">
                            <a href="./login.jsp"><i class="bi bi-person-circle" title="Tài Khoản"></i></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </nav>

    <div class="search-overlay" id="searchOverlay" onclick="closeSearchPanel()"></div>

    <!-- Carousel Section -->
    <div class="carousel-container" id="carouselContainer">
        <!-- Slide 1 -->
        <div class="carousel-slide active">
            <div class="section_1" style="background-image: url('https://anhsanghoanglam.vn/wp-content/uploads/2022/05/z2483765420178_66b758a5966fe34dd8e7e861b837681d-scaled-1.jpg');">
                <div class="cont">
                    <div class="section_1-content">
                        <h1>Lightup</h1>
                        <span class="highlight">Đèn Và Thiết Bị Chiếu Sáng</span>
                        <p class="line1">Chúng tôi mang đến ánh sáng chuẩn mực từ những bộ sưu tập chất lượng.</p>
                        <p class="line2">Tạo nên không gian sang trọng, tinh tế, an tâm và đầy cảm hứng.</p>
                        <a href="./products.jsp" class="cta-button">Mua Ngay</a>
                    </div>
                </div>
            </div>
        </div>

        <!-- Slide 2 -->
        <div class="carousel-slide">
            <div class="section_2" style="background-image: url('https://denchum.mauthemewp.com/wp-content/uploads/2022/05/artglass-particles06.igallery.image0000007.jpg');">
                <div class="cont1">
                    <div class="section_2-content p2">
                        <div class="p2-r">
                            <div class="r-up">
                                <h1 class="slide2">Lightup</h1>
                                <span class="highlight1">Đèn Và Thiết Bị Chiếu Sáng</span>
                            </div>
                            <div class="r-down">
                                <img src="https://www.mpe.com.vn/media/zzbjgmwb/ca-n-ho.jpg" alt="">
                                <div class="down_c">
                                    <a href="./contact.jsp">Liên Hệ Ngay</a>
                                    <div class="contact-box">
                                        <div class="contact-phone">
                                            <div class="phone-icon">☎</div>
                                            <span>0906.94.94.88</span>
                                        </div>
                                        <div class="contact-phone">
                                            <div class="phone-icon">☎</div>
                                            <span>0937.94.94.88</span>
                                        </div>
                                        <div class="addresses">
                                            <div class="address-item"><strong>CN1:</strong> Khu Phố 33, Phường Linh Xuân, TP.HCM</div>
                                            <div class="address-item"><strong>CN2:</strong> Đường Trần Nhật Duật, Phường Diên Hồng, Tỉnh Gia Lai</div>
                                            <div class="address-item"><strong>CN3:</strong> Số 8 Yên Ninh, Phường Ninh Chữ, Tỉnh Ninh Thuận</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="p2-l">
                            <img src="https://i.pinimg.com/736x/09/38/c2/0938c245ac71087fa38d47f0276378e2.jpg" alt="">
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <button class="carousel-arrow prev" onclick="prevSlide()">❮</button>
        <button class="carousel-arrow next" onclick="nextSlide()">❯</button>

        <div class="carousel-dots" id="dots">
            <span class="dot active" onclick="goToSlide(0)"></span>
            <span class="dot" onclick="goToSlide(1)"></span>
        </div>
    </div>

    <!-- Product Categories Section -->
    <div class="section-2">
        <div class="section-header">
            <h2>DÒNG SẢN PHẨM CHÍNH</h2>
            <div class="divider"></div>
        </div>

        <div class="products-grid-2">
            <c:forEach var="category" items="${categories}">
                <a href="./products.jsp?action=category&id=${category.id}">
                    <div class="product-card-2">
                        <div class="product-image-2">
                            <img src="${category.imgCate}" alt="${category.name}" />
                        </div>
                        <div class="product-name-2">${category.name}</div>
                    </div>
                </a>
            </c:forEach>
        </div>
    </div>

    <!-- Featured Products Section -->
    <div class="section-3">
        <div class="section-header">
            <h2>SẢN PHẨM NỔI BẬT</h2>
            <div class="divider"></div>
        </div>

        <div class="products-slider">
            <button class="slider-btn prev" id="prevBtn" onclick="slideProducts(-1)">‹</button>

            <div class="product-grid" id="productGrid">
                <c:forEach var="product" items="${featuredProducts}">
                    <div class="product-card">
                        <div class="product-image">
                            <c:if test="${product.hasDiscount()}">
                                <div class="product-sale">
                                    -<fmt:formatNumber value="${product.discountRate}" maxFractionDigits="0"/>%
                                </div>
                            </c:if>
                            <a href="product-detail.jsp?id=${product.id}">
                                <img src="${product.mainImage}"
                                     alt="${product.description}" class="img-main">
                                <c:if test="${not empty product.hoverImage}">
                                    <img src="${product.hoverImage}"
                                         alt="${product.description}" class="img-hover">
                                </c:if>
                            </a>
                        </div>

                        <div class="product-info">
                            <h3 class="product-name">
                                <a href="product-detail.jsp?id=${product.id}">${product.description}</a>
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
                                    <a href="#" class="open-cart" onclick="addToCart(${product.id}); return false;">
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

            <button class="slider-btn next" id="nextBtn" onclick="slideProducts(1)">›</button>
        </div>
    </div>

    <!-- About Section -->
    <section class="section_4">
        <div class="cont">
            <div class="section_cont">
                <div class="section-header">
                    <h2>VỀ CHÚNG TÔI</h2>
                    <div class="divider"></div>
                </div>
                <div class="select_colume">
                    <img src="./IMG/tải xuống.webp" alt="">
                    <div class="cont-r">
                        <h2>LightUP được thành lập với niềm đam mê mang ánh sáng và sự ấm áp đến mọi không gian sống.
                            Chúng tôi hiểu rằng ánh sáng không đơn thuần là một nhu cầu thiết yếu, mà còn là yếu tố quan trọng
                            tạo nên không khí, phong cách và cảm xúc cho ngôi nhà của bạn.</h2>
                        <p><b>SỨ MỆNH</b>: Mang đến những giải pháp chiếu sáng thông minh, tiết kiệm năng lượng và thẩm mỹ cao,
                            giúp khách hàng tạo nên không gian sống hoàn hảo với chi phí hợp lý nhất.</p>
                        <p><b>TẦM NHÌN</b>: Trở thành thương hiệu thiết bị chiếu sáng được tin dùng nhất tại Việt Nam,
                            nơi khách hàng tìm thấy sự kết hợp hoàn hảo giữa chất lượng, thiết kế và công nghệ.</p>
                        <div class="cont-in">
                            <img src="https://www.mpe.com.vn/media/ivhkb2hx/mpe-smart-control-recovered-02.png" alt="">
                            <img src="https://www.mpe.com.vn/media/jwahhja1/canhquan1.png" alt="">
                            <img src="https://www.mpe.com.vn/media/ekqb0p4h/nha-xu-o-ng.png" alt="">
                            <img src="https://www.mpe.com.vn/media/b0elw3rk/sanvandong.png" alt="">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Statistics Section -->
    <section class="stats-section">
        <div class="container">
            <div class="stats-grid">
                <div class="stat-item">
                    <div class="icon-wrapper">
                        <div class="icon"><i class="bi bi-person-gear"></i></div>
                    </div>
                    <div class="stat-number">500+</div>
                    <div class="stat-label">Nhà Thầu</div>
                </div>

                <div class="stat-item">
                    <div class="icon-wrapper">
                        <div class="icon"><i class="bi bi-award"></i></div>
                    </div>
                    <div class="stat-number">30+</div>
                    <div class="stat-label">Năm Thành Lập</div>
                </div>

                <div class="stat-item">
                    <div class="icon-wrapper">
                        <div class="icon"><i class="bi bi-building"></i></div>
                    </div>
                    <div class="stat-number">4000+</div>
                    <div class="stat-label">Đại Lý và Cửa Hàng</div>
                </div>

                <div class="stat-item">
                    <div class="icon-wrapper">
                        <div class="icon"><i class="bi bi-lightbulb"></i></div>
                    </div>
                    <div class="stat-number">5000+</div>
                    <div class="stat-label">Mã Sản Phẩm</div>
                </div>

                <div class="stat-item">
                    <div class="icon-wrapper">
                        <div class="icon"><i class="bi bi-box-seam"></i></div>
                    </div>
                    <div class="stat-number">60M+</div>
                    <div class="stat-label">Sản Phẩm/Năm</div>
                </div>
            </div>
        </div>
    </section>

    <!-- News Section -->
    <section class="section-5">
        <div class="container">
            <div class="section-header">
                <h2>TIN TỨC</h2>
                <div class="divider"></div>
            </div>

            <div class="news-grid">
                <div class="featured-post">
                    <img src="https://duhal.com.vn/image-news/small/ung-dung-bong-led-bulb-duhal-trong-chieu-sang-gia-dinh-van-phong-va-cua-hang-51306.jpg"
                         alt="Tin tức" class="featured-image">
                    <div class="featured-content">
                        <h3><a href="news-detail.jsp">Ứng dụng bóng LED bulb Duhal trong chiếu sáng gia đình, văn phòng và cửa hàng</a></h3>
                        <p>Bóng LED bulb Duhal ngày càng trở thành lựa chọn quen thuộc trong nhiều không gian nhờ ánh sáng dễ chịu,
                            tiết kiệm điện năng và độ bền cao...</p>
                    </div>
                </div>

                <div class="side-posts">
                    <div class="view-more">
                        <a href="./news.jsp" class="view-more-btn">
                            XEM THÊM <i class="bi bi-arrow-right"></i>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Footer -->
<%--    <jsp:include page="footer.jsp" />--%>

    <!-- Scroll to Top -->
    <a href="#">
        <button id="scrollToTopBtn">
            <i class="bi bi-chevron-up"></i>
        </button>
    </a>

    <!-- Cart Sidebar -->
<%--    <jsp:include page="cart-sidebar.jsp" />--%>

    <div id="cart-overlay"></div>
</main>

<!-- JavaScript -->
<script>
    // Search functionality
    document.getElementById('searchInput').addEventListener('input', function() {
        const keyword = this.value.trim();
        if (keyword.length >= 2) {
            searchProducts(keyword);
        }
    });

    function searchProducts(keyword) {
        fetch('products?action=search&keyword=' + encodeURIComponent(keyword))
            .then(response => response.json())
            .then(data => {
                displaySearchResults(data);
            })
            .catch(error => console.error('Error:', error));
    }

    function displaySearchResults(products) {
        const resultsContainer = document.getElementById('searchResults');
        let html = '<div class="section-header_search"><h2 class="section-title-search">Kết quả tìm kiếm</h2></div>';

        products.forEach(product => {
            html += `
                    <a href="product-detail.jsp?id=\${product.id}" class="search-item">
                        <span class="arrow-icon"><i class="bi bi-search"></i></span>
                        <div class="item-content">
                            <div class="item-name">\${product.description}</div>
                        </div>
                    </a>
                `;
        });

        resultsContainer.innerHTML = html;
    }

    // Add to cart
    function addToCart(productId) {
        fetch('cart?action=add&productId=' + productId, {
            method: 'POST'
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    updateCartCount(data.cartCount);
                    showNotification('Đã thêm vào giỏ hàng');
                }
            })
            .catch(error => console.error('Error:', error));
    }

    function updateCartCount(count) {
        document.getElementById('cartCount').textContent = count;
    }

    function showNotification(message) {
        // Implement notification display
        alert(message);
    }
</script>
<script src="./JS/products.js"></script>
<script src="./JS/index.js"></script>
<script src="./JS/actionButton.js"></script>
</body>
</html>
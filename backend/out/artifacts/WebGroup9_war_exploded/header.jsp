<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav>
    <div class="nav_head">
        <a href="./index.jsp">
            <div class="logo">
                <img src="https://i.postimg.cc/26JnYsPT/Logo-Photoroom.png" alt="Logo">
            </div>
        </a>

        <div class="nav-container">
            <ul class="nav-links">
                <li><a href="./index.jsp" class="${pageContext.request.servletPath == '/index.jsp' ? 'active' : ''}">TRANG CHỦ</a></li>
                <li><a href="./about.jsp" class="${pageContext.request.servletPath == '/about.jsp' ? 'active' : ''}">GIỚI THIỆU</a></li>
                <li>
                    <a href="./products.jsp" class="${pageContext.request.servletPath == '/products.jsp' ? 'active' : ''}">
                        SẢN PHẨM <i class="bi bi-caret-down-fill"></i>
                    </a>
                    <div class="sub-item">
                        <div class="contant-sub">
                            <h2>Danh mục sản phẩm</h2>
                            <div class="cont-item" >
                                <c:forEach var="category" items="${categories}">
                                    <a href="./products.jsp#section-${category.id}" class="product_item">
                                        <div class="product_cont">
                                            <img src="${category.imgCate}" alt="${category.name}" />
                                            <div class="item" >${category.name}</div>
                                        </div>
                                        <span class="arrow">›</span>
                                    </a>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </li>
                <li><a href="./news.jsp" class="${pageContext.request.servletPath == '/news.jsp' ? 'active' : ''}">TIN TỨC</a></li>
                <li><a href="./contact.jsp" class="${pageContext.request.servletPath == '/contact.jsp' ? 'active' : ''}">LIÊN HỆ</a></li>
            </ul>

            <div class="nav_r" id="nav_r">
                <div class="search-icon" onclick="openSearchBar()">
                    <input type="text" placeholder="Tìm kiếm" id="searchInput">
                    <i class="bi bi-search"></i>
                </div>
                <i class="bi bi-x back-button-nav" onclick="closeSearchBar()"></i>

                <div class="search-panel" id="searchPanel">
                    <div class="search-content" id="searchResults">
                        <div class="section-header_search">
                            <h2 class="section-title-search">Gợi ý tìm kiếm</h2>
                        </div>
                    </div>
                </div>

                <div class="icon-group">
                    <div class="shop" id="shop">
                        <a href="cart-detail.jsp">
                            <i class="bi bi-cart-check" title="Giỏ hàng"></i>
                            <div class="item-quantity" id="cartCount">
                                ${not empty sessionScope.cart ? sessionScope.cart.getTotalItems() : 0}
                            </div>
                        </a>
                    </div>

                    <div class="login" id="login">
                        <c:choose>
                            <c:when test="${not empty sessionScope.user}">
                                <a href="#"><i class="bi bi-person-circle" title="${sessionScope.user.name}"></i></a>
                                <div class="sub_login">
                                    <div class="container-sub-login">
                                        <a href="./profile.jsp"><i class="bi bi-person"></i> Tài khoản</a>
                                        <a href="./order.jsp"><i class="bi bi-clipboard-check"></i> Đơn Hàng</a>
                                        <a href="logout"><i class="bi bi-box-arrow-right"></i> Đăng xuất</a>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <a href="./login.jsp"><i class="bi bi-person-circle" title="Đăng nhập"></i></a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
</nav>
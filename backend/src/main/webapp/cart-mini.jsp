<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- ===== MINI CART (KHUNGGIOHANG) ===== -->
<div id="khunggiohang" class="cart-widget-side wd-right">

    <!-- HEADER -->
    <div class="wd-heading">
        <span class="title">Giỏ hàng</span>
        <div class="close-side-widget">
            <a href="#" class="close-cart">× Đóng</a>
        </div>
    </div>

    <!-- BODY -->
    <div class="woocommerce-cart-widget">
        <div class="widget_shopping_cart_content">

            <div class="shopping-cart-widget-body wd-scroll">
                <ul class="cart_list woocommerce-mini-cart">

                    <!-- GIỎ HÀNG TRỐNG -->
                    <c:if test="${empty sessionScope.cart || empty sessionScope.cart.listItem}">
                        <li class="mini-cart-empty">
                            <p>Giỏ hàng của bạn đang trống</p>
                        </li>
                    </c:if>

                    <!-- DANH SÁCH SẢN PHẨM -->
                    <c:forEach var="item" items="${sessionScope.cart.listItem}">
                        <li class="woocommerce-mini-cart-item">

                            <!-- ẢNH -->
                            <div class="cart-item-image">
                                <img src="${item.product.mainImage}" alt="${item.product.name}">
                            </div>

                            <!-- THÔNG TIN -->
                            <div class="cart-info">

                                <div class="cart-info-top">
                                    <span class="wd-entities-title">
                                            ${item.product.name}
                                    </span>

                                    <!-- XÓA SẢN PHẨM -->
                                    <a href="remove-cart?id=${item.product.id}" class="remove">×</a>
                                </div>

                                <!-- SỐ LƯỢNG + GIÁ -->
                                <form action="update" method="post" class="quantity">

                                    <input type="hidden" name="productId" value="${item.product.id}"/>

                                    <button type="submit" name="qty" value="${item.quantity - 1}">-</button>

                                    <span class="qty">${item.quantity}</span>

                                    <button type="submit" name="qty" value="${item.quantity + 1}">+</button>

                                    <span class="woocommerce-Price-amount amount">
                                        <fmt:formatNumber
                                                value="${item.product.discountedPrice}"
                                                pattern="#,###"/>₫
                                    </span>

                                </form>

                            </div>
                        </li>
                    </c:forEach>

                </ul>
            </div>

            <!-- FOOTER -->
            <div class="shopping-cart-widget-footer">

                <!-- TỔNG TIỀN (CÙNG 1 HÀNG) -->
                <div class="woocommerce-mini-cart__total">
                    <span>Tổng tiền:</span>
                    <span class="woocommerce-Price-amount">
                        <fmt:formatNumber
                                value="${sessionScope.cart.totalPrice}"
                                pattern="#,###"/>₫
                    </span>
                </div>

                <!-- NÚT -->
                <div class="woocommerce-mini-cart__buttons">

                    <a href="${pageContext.request.contextPath}/cart"
                       class="btn-cart">
                        Xem giỏ hàng
                    </a>

                    <a href="${pageContext.request.contextPath}/checkout"
                       class="checkout">
                        Thanh toán
                    </a>

                </div>
            </div>

        </div>
    </div>
</div>

<!-- OVERLAY -->
<div id="cart-overlay"></div>
<!-- ===== END MINI CART ===== -->

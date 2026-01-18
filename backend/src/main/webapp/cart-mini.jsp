<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- ===== MINI CART (KHUNGGIOHANG) ===== -->
<div id="khunggiohang" class="cart-widget-side wd-right">

    <div class="wd-heading">
        <span class="title">Giỏ hàng</span>
        <div class="close-side-widget">
            <a href="#" class="close-cart">× Đóng</a>
        </div>
    </div>

    <div class="woocommerce-cart-widget">
        <div class="widget_shopping_cart_content">

            <div class="shopping-cart-widget-body wd-scroll">
                <ul class="cart_list woocommerce-mini-cart">

                    <c:if test="${empty sessionScope.cart || empty sessionScope.cart.listItem}">
                        <li class="mini-cart-empty">
                            <p>Giỏ hàng của bạn đang trống</p>
                        </li>
                    </c:if>

                    <c:forEach var="item" items="${sessionScope.cart.listItem}">
                        <li class="woocommerce-mini-cart-item">
                            <div class="cart-item-image">
                                <img src="${item.product.mainImage}">
                            </div>

                            <div class="cart-info">
                                <span>${item.product.name}</span>

                                <span class="quantity">
                                    ${item.quantity} ×
                                    <fmt:formatNumber
                                            value="${item.product.discountedPrice}"
                                            pattern="#,###"/>₫
                                </span>

                                <a href="remove-cart?id=${item.product.id}" class="remove">×</a>
                            </div>
                        </li>
                    </c:forEach>

                </ul>
            </div>

            <div class="shopping-cart-widget-footer">

                <div class="woocommerce-mini-cart__total">
                    <span>Tổng tiền:</span>
                    <span class="woocommerce-Price-amount">
            <fmt:formatNumber value="${sessionScope.cart.totalPrice}" pattern="#,###"/>₫
        </span>
                </div>


                <div class="buttons">
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
</div>

<div id="cart-overlay"></div>
<!-- ===== END MINI CART ===== -->

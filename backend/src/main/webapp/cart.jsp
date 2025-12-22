<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Giỏ hàng của bạn</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <link rel="icon" type="image/png" sizes="32x32" href="https://i.postimg.cc/26JnYsPT/Logo-Photoroom.png">
    <link rel="stylesheet" href="./CSS/sub_login.css">
    <link rel="stylesheet" href="./CSS/cart_detail.css">
    <link rel="stylesheet" href="./CSS/style.css">
    <link rel="stylesheet" href="./CSS/about.css">

</head>

<body>
<!--header trang-->
<jsp:include page="header.jsp"/>


<!--danh sách sản phẩm-->
<main class="cart-page">
    <div class="cart-left">
        <h2>Giỏ hàng của bạn</h2>
        <hr class="line">

        <c:if test="${sessionScope.cart == null || empty sessionScope.cart.listItem}">
            <p>Giỏ hàng của bạn đang trống</p>
        </c:if>

        <c:forEach var="item" items="${sessionScope.cart.listItem}">
            <div class="cart-item">
                    <%--                <img src="${item.product.getMainImage}" alt="${item.product.name}">--%>

                <div class="item-info">
                    <h4>${item.product.name}</h4>

                    <p class="price">
                        <fmt:formatNumber value="${item.price}" type="number"/> VND
                    </p>

                    <div class="quantity">

                        <!-- Giảm -->
                        <form action="${pageContext.request.contextPath}/update"
                              method="post" style="display:inline;">
                            <input type="hidden" name="productId" value="${item.product.id}">
                            <input type="hidden" name="qty" value="${item.quantity - 1}">
                            <button type="submit" class="qty-btn">-</button>
                        </form>

                        <span class="qty">${item.quantity}</span>

                        <!-- Tăng -->
                        <form action="${pageContext.request.contextPath}/update"
                              method="post" style="display:inline;">
                            <input type="hidden" name="productId" value="${item.product.id}">
                            <input type="hidden" name="qty" value="${item.quantity + 1}">
                            <button type="submit" class="qty-btn">+</button>
                        </form>

                    </div>


                </div>

                <div class="item-total">
                    <fmt:formatNumber value="${item.price * item.quantity}" type="number"/> VND
                </div>

                <a href="remove?productId=${item.product.id}"
                   class="delete-btn">
                    Xóa
                </a>
            </div>
        </c:forEach>
    </div>

    <!--tổng tiền sản phẩm-->
    <div class="cart-right">
        <div class="order-box">
            <h3>Thông tin đơn hàng</h3>
            <ul>
                <c:forEach var="item" items="${sessionScope.cart.listItem}">
                    <li>
                            ${item.product.name} - SL: ${item.quantity}
                    </li>
                </c:forEach>
            </ul>

            <div class="total">
                <span>Tổng tiền:</span>
                <strong id="total">
                    <fmt:formatNumber value="${sessionScope.cart.totalPrice}" type="number"/> VND
                </strong>
            </div>
            <a href="./checkout.html">
                <button class="checkout-btn">THANH TOÁN</button>
            </a>
        </div>

        <div class="order-note">
            <a href="./products"><i class="bi bi-arrow-return-left"></i> Tiếp tục mua hàng</a>
            <ul>
                <li>Không rủi ro.</li>
                <li>Đặt hàng trước, thanh toán sau tại nhà. Miễn phí giao hàng & lắp đặt tại TP.HCM, Hà Nội,...
                </li>
                <li>Đơn hàng sẽ được giao trong vòng 3 ngày, vui lòng chờ nhân viên tư vấn xác nhận.</li>
                <li>Miễn phí 1 đổi 1 - Bảo hành 2 tháng - Bảo trì nhanh chóng.</li>
            </ul>
        </div>
    </div>
</main>

<!-- Footer -->
<jsp:include page="footer.jsp"/>

<script src="./JS/index.js"></script>
</body>

</html>

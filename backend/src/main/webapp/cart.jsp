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
    <link rel="stylesheet" href="CSS/cart_detail.css">
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

        <!--sản phẩm demo-->
        <div class="cart-item">
            <img src="https://flexhouse.vn/wp-content/uploads/2022/12/Den-de-ban-phong-khach-phong-cach-toi-gian-KLS0049-4-430x430.jpg"
                 alt="Đèn tường LED">
            <div class="item-info">
                <h4>Đèn để bàn phòng khách phong cách tối giản KLS0049</h4>
                <p class="price">3.900.000 VND</p>
                <div class="quantity">
                    <button type="submit">-</button>
                    <span class="qty">1</span>
                    <button type="submit">+</button>
                </div>
            </div>
            <div class="item-total">3.900.000 VND</div>
            <button class="delete-btn" type="submit">Xóa</button>
        </div>
    </div>

    <!--tổng tiền sản phẩm-->
    <div class="cart-right">
        <div class="order-box">
            <h3>Thông tin đơn hàng</h3>
            <ul>
                <li>Đèn để bàn phòng khách phong cách tối giản KLS0049 - SL: 1</li>
                <li>Đèn vách tường trang trí cảnh quan ngoài trời JJ7118 - SL: 1</li>
            </ul>
            <div class="total">
                <span>Tổng tiền:</span>
                <strong id="total">7.230.000 VND</strong>
            </div>
            <a href="./checkout.html"> <button class="checkout-btn">THANH TOÁN</button></a>
        </div>

        <div class="order-note">
            <a href="products.html"><i class="bi bi-arrow-return-left"></i> Tiếp tục mua hàng</a>
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

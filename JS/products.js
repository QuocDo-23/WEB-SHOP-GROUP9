
    document.addEventListener("DOMContentLoaded", function () {
    const cart = document.getElementById("khunggiohang");
    const overlay = document.getElementById("cart-overlay");

    // Nút mở
    document.querySelectorAll(".open-cart").forEach(btn => {
    btn.addEventListener("click", function (e) {
    e.preventDefault();
    cart.classList.add("active");
    overlay.classList.add("active");
});
});

    // Nút đóng
    document.querySelectorAll(".close-cart").forEach(btn => {
    btn.addEventListener("click", function (e) {
    e.preventDefault();
    cart.classList.remove("active");
    overlay.classList.remove("active");
});
});

    // Bấm ra ngoài để đóng
    overlay.addEventListener("click", function () {
    cart.classList.remove("active");
    overlay.classList.remove("active");
});
});
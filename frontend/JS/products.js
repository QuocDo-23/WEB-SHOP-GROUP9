
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
    // Toggle dropdown bộ lọc
    document.querySelectorAll('.filter-toggle').forEach(toggle => {
        toggle.addEventListener('click', () => {
            const list = toggle.nextElementSibling;
            list.classList.toggle('show');
            toggle.classList.toggle('open');
        });
    });

    // Toggle dropdown sắp xếp
    document.querySelector('.sort-toggle')?.addEventListener('click', () => {
        const list = document.querySelector('.sort-list');
        list.classList.toggle('show');
        document.querySelector('.sort-toggle').classList.toggle('open');
    });

document.addEventListener("DOMContentLoaded", function () {

    /* =======================
       MINI CART (KHUNGGIOHANG)
       ======================= */
    const cart = document.getElementById("khunggiohang");
    const overlay = document.getElementById("cart-overlay");

    // MỞ mini cart (chỉ icon trên thẻ sản phẩm có class .open-cart)
    document.querySelectorAll(".open-cart").forEach(btn => {
        btn.addEventListener("click", function (e) {
            e.preventDefault(); // ⭐ BẮT BUỘC

            const productId = this.dataset.productId;
            if (!productId) return;

            fetch(`add-cart?pID=${productId}&quantity=1`)
                .then(() => {
                    const cart = document.getElementById("khunggiohang");
                    const overlay = document.getElementById("cart-overlay");

                    cart?.classList.add("active");
                    overlay?.classList.add("active");
                })
                .catch(err => console.error(err));
        });
    });



    // ĐÓNG mini cart (nút X)
    document.querySelectorAll(".close-cart").forEach(btn => {
        btn.addEventListener("click", function (e) {
            e.preventDefault();
            cart?.classList.remove("active");
            overlay?.classList.remove("active");
        });
    });

    // BẤM RA NGOÀI overlay → đóng
    if (overlay) {
        overlay.addEventListener("click", function () {
            cart?.classList.remove("active");
            overlay.classList.remove("active");
        });
    }

    /* =======================
       FILTER DROPDOWN
       ======================= */
    document.querySelectorAll('.filter-toggle').forEach(toggle => {
        toggle.addEventListener('click', () => {
            const list = toggle.nextElementSibling;
            list?.classList.toggle('show');
            toggle.classList.toggle('open');
        });
    });

    /* =======================
       SORT DROPDOWN
       ======================= */
    const sortToggle = document.querySelector('.sort-toggle');
    if (sortToggle) {
        sortToggle.addEventListener('click', () => {
            const list = document.querySelector('.sort-list');
            list?.classList.toggle('show');
            sortToggle.classList.toggle('open');
        });
    }

    /* =======================
       VIEW MORE (XEM THÊM)
       ======================= */
    document.querySelectorAll(".view-more-text").forEach(link => {
        link.addEventListener("click", function () {
            const categoryId = this.getAttribute("data-category");
            const section = document.getElementById("section-" + categoryId);

            if (section) {
                section.classList.add("show-all");
                this.style.display = "none";
            }
        });
    });

});

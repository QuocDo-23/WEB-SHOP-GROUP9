document.addEventListener("DOMContentLoaded", function () {

    const miniCart = document.getElementById("khunggiohang");
    const overlay = document.getElementById("cart-overlay");

    /* =======================
       ADD TO CART + OPEN MINI CART
       ======================= */
    document.querySelectorAll(".open-cart").forEach(btn => {
        btn.addEventListener("click", function (e) {
            e.preventDefault();

            const productId = this.dataset.productId;
            if (!productId) return;

            fetch(`add-cart?pID=${productId}&quantity=1`)
                .then(res => {
                    if (!res.ok) throw new Error("Add cart failed");

                    // CÁCH SINH VIÊN – reload để JSP cập nhật session
                    location.reload();
                })
                .then(() => {
                    miniCart.classList.add("active");
                    overlay.classList.add("active");
                })
                .catch(err => console.error(err));
        });
    });

    /* =======================
       CLOSE MINI CART
       ======================= */
    document.querySelectorAll(".close-cart").forEach(btn => {
        btn.addEventListener("click", function (e) {
            e.preventDefault();
            miniCart.classList.remove("active");
            overlay.classList.remove("active");
        });
    });

    if (overlay) {
        overlay.addEventListener("click", function () {
            miniCart.classList.remove("active");
            overlay.classList.remove("active");
        });
    }

    /* =======================
       REMOVE ITEM IN MINI CART
       ======================= */
    document.addEventListener("click", function (e) {
        const btn = e.target.closest(".remove-mini-cart");
        if (!btn) return;

        e.preventDefault();

        const productId = btn.dataset.id;
        if (!productId) return;

        fetch(`remove?productId=${productId}`)
            .then(res => {
                if (!res.ok) throw new Error("Remove failed");
                location.reload(); // reload để mini cart cập nhật
            })
            .catch(err => console.error(err));
    });

});

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
    document.querySelectorAll('.sort-list li').forEach(item => {
        item.addEventListener('click', () => {

            const sortType = item.dataset.sort;
            const products = Array.from(document.querySelectorAll('.product-card'));
            const grid = document.querySelector('.product-grid');

            let sorted;

            if (sortType === 'price-asc') {
                sorted = products.sort((a, b) =>
                    a.dataset.price - b.dataset.price
                );
            }

            if (sortType === 'price-desc') {
                sorted = products.sort((a, b) =>
                    b.dataset.price - a.dataset.price
                );
            }

            if (sortType === 'popular') {
                sorted = products.sort((a, b) =>
                    b.dataset.rating - a.dataset.rating
                );
            }

            grid.innerHTML = '';
            sorted.forEach(p => grid.appendChild(p));
        });
    });
    /* ================= DROPDOWN FILTER & SORT ================= */

// toggle dropdown (Giá, Loại đèn, Sắp xếp)
    // FILTER (Giá, Loại đèn)
    document.querySelectorAll('.filter-dropdown .filter-toggle')
        .forEach(toggle => {
            toggle.addEventListener('click', function (e) {
                e.stopPropagation();

                const list = this.parentElement.querySelector('.filter-list');
                if (!list) return;

                document.querySelectorAll('.filter-list')
                    .forEach(l => { if (l !== list) l.style.display = 'none'; });

                list.style.display = list.style.display === 'block' ? 'none' : 'block';
            });
        });

// SORT
    document.querySelector('.sort-toggle')?.addEventListener('click', function (e) {
        e.stopPropagation();

        const list = document.querySelector('.sort-list');
        if (!list) return;

        document.querySelectorAll('.filter-list')
            .forEach(l => l.style.display = 'none');

        list.style.display = list.style.display === 'block' ? 'none' : 'block';
    });


// click ra ngoài thì đóng hết
    document.addEventListener('click', () => {
        document.querySelectorAll('.filter-list, .sort-list')
            .forEach(list => list.style.display = 'none');
    });
    /* ================= APPLY PRICE FILTER ================= */

    /* ================= APPLY PRICE FILTER (NÚT BỘ LỌC) ================= */

// lấy đúng nút "BỘ LỌC" (icon phễu – cái đầu tiên)
    const applyFilterBtn = document.querySelector(
        '.filter-container > .filter-dropdown:first-child .filter-toggle'
    );

    if (applyFilterBtn) {
        applyFilterBtn.addEventListener('click', function (e) {
            e.stopPropagation();

            const checked = document.querySelectorAll('.price-filter:checked');

            if (checked.length === 0) {
                alert("Vui lòng chọn ít nhất một khoảng giá");
                return;
            }

            const params = [];

            checked.forEach(cb => {
                const min = cb.dataset.min;
                const max = cb.dataset.max;
                params.push(`price=${min}-${max}`);
            });

            // chuyển sang servlet /products
            window.location.href = `products?${params.join('&')}`;
        });
    }


});

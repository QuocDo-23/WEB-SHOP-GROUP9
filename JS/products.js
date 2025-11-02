class ProductsPagination {
    constructor(containerSelector, itemsPerPage = 8) {
        this.container = document.querySelector(containerSelector);
        this.items = Array.from(this.container.querySelectorAll('.product-card'));
        this.itemsPerPage = itemsPerPage;
        this.currentPage = 1;
        this.totalPages = Math.ceil(this.items.length / this.itemsPerPage);
        this.init();
    }

    init() {
        this.renderPageButtons();
        this.showPage(this.currentPage); // ✅ Quan trọng: ẩn bớt sản phẩm ban đầu
    }

    renderPageButtons() {
        const pagination = document.createElement('div');
        pagination.className = 'pagination';

        for (let i = 1; i <= this.totalPages; i++) {
            const btn = document.createElement('button');
            btn.className = 'page-btn';
            btn.textContent = i;
            btn.addEventListener('click', () => this.showPage(i));
            pagination.appendChild(btn);
        }

        this.container.parentElement.appendChild(pagination);
        this.updateActiveButton();
    }

    showPage(pageNumber) {
        this.currentPage = pageNumber;

        const start = (pageNumber - 1) * this.itemsPerPage;
        const end = start + this.itemsPerPage;

        // ✅ Ẩn tất cả sản phẩm trước
        this.items.forEach((item, index) => {
            item.style.display = (index >= start && index < end) ? 'block' : 'none';
        });

        this.updateActiveButton();
    }

    updateActiveButton() {
        const buttons = document.querySelectorAll('.page-btn');
        buttons.forEach((btn, index) => {
            btn.classList.toggle('active', index + 1 === this.currentPage);
        });
    }
}

// ✅ Khởi động phân trang sau khi DOM tải xong
document.addEventListener('DOMContentLoaded', () => {
    new ProductsPagination('.product-grid', 8);
});

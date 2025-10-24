const navLinks = document.querySelectorAll(".nav-links a");
navLinks.forEach((link) => {
  link.addEventListener("click", function () {
    navLinks.forEach((l) => l.classList.remove("active"));
    this.classList.add("active");
  });
});

let currentPage = 0;
const productsPerPage = 4;
const allProducts = document.querySelectorAll(".product-card");
const totalPages = Math.ceil(allProducts.length / productsPerPage);
const prevBtn = document.getElementById("prevBtn");
const nextBtn = document.getElementById("nextBtn");

function updateButtons() {
  // Disable nút prev ở trang đầu
  if (currentPage === 0) {
    prevBtn.classList.add("disabled");
    prevBtn.disabled = true;
  } else {
    prevBtn.classList.remove("disabled");
    prevBtn.disabled = false;
  }

  // Disable nút next ở trang cuối
  if (currentPage === totalPages - 1) {
    nextBtn.classList.add("disabled");
    nextBtn.disabled = true;
  } else {
    nextBtn.classList.remove("disabled");
    nextBtn.disabled = false;
  }
}

function showPage(page) {
  // Ẩn tất cả sản phẩm
  allProducts.forEach((product) => {
    product.classList.add("hidden");
  });

  // Hiển thị 4 sản phẩm của trang hiện tại
  const start = page * productsPerPage;
  const end = start + productsPerPage;

  for (let i = start; i < end && i < allProducts.length; i++) {
    allProducts[i].classList.remove("hidden");
  }

  updateButtons();
}

function slideProducts(direction) {
  const newPage = currentPage + direction;

  // Kiểm tra giới hạn
  if (newPage >= 0 && newPage < totalPages) {
    currentPage = newPage;
    showPage(currentPage);
  }
}

// Hiển thị trang đầu tiên khi load
showPage(0);

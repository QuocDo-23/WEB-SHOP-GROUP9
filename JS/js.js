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
  if (currentPage === 0) {
    prevBtn.classList.add("disabled");
    prevBtn.disabled = true;
  } else {
    prevBtn.classList.remove("disabled");
    prevBtn.disabled = false;
  }

  if (currentPage === totalPages - 1) {
    nextBtn.classList.add("disabled");
    nextBtn.disabled = true;
  } else {
    nextBtn.classList.remove("disabled");
    nextBtn.disabled = false;
  }
}

function showPage(page) {
  allProducts.forEach((product) => {
    product.classList.add("hidden");
  });

  const start = page * productsPerPage;
  const end = start + productsPerPage;

  for (let i = start; i < end && i < allProducts.length; i++) {
    allProducts[i].classList.remove("hidden");
  }

  updateButtons();
}

function slideProducts(direction) {
  const newPage = currentPage + direction;

  if (newPage >= 0 && newPage < totalPages) {
    currentPage = newPage;
    showPage(currentPage);
  }
}

showPage(0);

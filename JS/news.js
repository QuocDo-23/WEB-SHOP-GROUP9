const articles = document.querySelectorAll(".article-card");
const pageButtons = document.querySelectorAll(".page-btn");
const prevBtn = document.querySelector(".prev-btn");
const nextBtn = document.querySelector(".next-btn");
let currentPage = 1;
const totalPages = 3;

function showPage(page) {
  currentPage = page;

  articles.forEach((article) => {
    if (parseInt(article.dataset.page) === page) {
      article.classList.remove("hidden");
    } else {
      article.classList.add("hidden");
    }
  });

  pageButtons.forEach((btn, index) => {
    if (index + 1 === page) {
      btn.classList.add("active");
    } else {
      btn.classList.remove("active");
    }
  });

  if (page === 1) {
    prevBtn.classList.add("disabled");
  } else {
    prevBtn.classList.remove("disabled");
  }

  if (page === totalPages) {
    nextBtn.classList.add("disabled");
  } else {
    nextBtn.classList.remove("disabled");
  }
}

pageButtons.forEach((btn, index) => {
  btn.addEventListener("click", (e) => {
    e.preventDefault();
    showPage(index + 1);
  });
});

prevBtn.addEventListener("click", (e) => {
  e.preventDefault();
  if (currentPage > 1) {
    showPage(currentPage - 1);
  }
});

nextBtn.addEventListener("click", (e) => {
  e.preventDefault();
  if (currentPage < totalPages) {
    showPage(currentPage + 1);
  }
});

showPage(1);

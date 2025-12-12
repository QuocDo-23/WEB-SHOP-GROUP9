function openSearchBar() {
  const navR = document.getElementById("nav_r");
  const searchPanel = document.getElementById("searchPanel");
  const searchOverlay = document.getElementById("searchOverlay");

  navR.classList.add("active");

  searchPanel.classList.add("active");
  searchOverlay.classList.add("active");
  document.body.style.overflowY = "hidden";
}

function closeSearchBar() {
  const navR = document.getElementById("nav_r");
  const searchPanel = document.getElementById("searchPanel");
  const searchOverlay = document.getElementById("searchOverlay");

  searchPanel.classList.remove("active");

  navR.classList.remove("active");
  searchOverlay.classList.remove("active");
  document.body.style.overflowY = "auto";
}

let currentSlide = 0;
const totalSlides = 2;

function showSlide(n) {
  const slides = document.querySelectorAll(".carousel-slide");
  const dots = document.querySelectorAll(".dot");

  slides.forEach((slide) => slide.classList.remove("active"));
  dots.forEach((dot) => dot.classList.remove("active"));

  slides[n].classList.add("active");
  dots[n].classList.add("active");
}

function nextSlide() {
  currentSlide = (currentSlide + 1) % totalSlides;
  showSlide(currentSlide);
}

function prevSlide() {
  currentSlide = (currentSlide - 1 + totalSlides) % totalSlides;
  showSlide(currentSlide);
}

function goToSlide(n) {
  currentSlide = n;
  showSlide(currentSlide);
}

// Auto-advance every 5 seconds
setInterval(nextSlide, 5000);

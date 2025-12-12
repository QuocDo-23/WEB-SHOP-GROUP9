function switchTab(tabName) {
  var sections = document.querySelectorAll(".content-section");
  for (var i = 0; i < sections.length; i++) {
    sections[i].classList.remove("active");
  }

  var menuItems = document.querySelectorAll(".menu-item");
  for (var i = 0; i < menuItems.length; i++) {
    menuItems[i].classList.remove("active");
  }

  document.getElementById(tabName).classList.add("active");
  event.target.classList.add("active");
}

function openModal() {
  document.getElementById("productModal").classList.add("active");
}

function closeModal() {
  document.getElementById("productModal").classList.remove("active");
  document.getElementById("productForm").reset();
}

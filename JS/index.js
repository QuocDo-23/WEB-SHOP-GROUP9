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

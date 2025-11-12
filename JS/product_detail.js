// JavaScript cho accordion
document.addEventListener("DOMContentLoaded", function () {
  const accordionTitles = document.querySelectorAll(".wd-accordion-title");

  accordionTitles.forEach(function (title) {
    title.addEventListener("click", function () {
      const targetId = this.getAttribute("data-target");
      const content = document.getElementById("tab-" + targetId);

      // Toggle active state
      this.classList.toggle("wd-active");
      content.classList.toggle("wd-active");
    });
  });
});

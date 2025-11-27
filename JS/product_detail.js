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

 // JavaScript để làm cho hệ thống sao hoạt động
     const stars = document.querySelectorAll('.stars a');
     let selectedRating = 5; // Mặc định là 5 sao vì có class active
     stars.forEach(star => {
         star.addEventListener('click', function(e) {
             e.preventDefault();
             selectedRating = parseInt(this.getAttribute('data-rating'));
             // Xóa class active khỏi tất cả sao
             stars.forEach(s => s.classList.remove('active'));
             // Thêm class active cho sao được chọn và các sao trước đó
             for (let i = 0; i < selectedRating; i++) {
                 stars[i].classList.add('active');
             }
         });
     });
     // Xử lý nút gửi (demo: chỉ log ra console)
     document.querySelector('.submit-btn').addEventListener('click', function() {
         const comment = document.querySelector('textarea').value;
         console.log(`Đánh giá: ${selectedRating} sao`);
         console.log(`Bình luận: ${comment}`);
         alert(`Cảm ơn bạn đã đánh giá ${selectedRating} sao!`);
         // Ở đây bạn có thể gửi dữ liệu đến server thay vì chỉ log
     });
     // Xử lý nút "Đánh giá ngay" để hiển thị overlay và hộp đánh giá
     document.querySelector('.btn-reviews-now').addEventListener('click', function(e) {
         e.preventDefault();
         document.querySelector('.overlay').style.display = 'block'; // Hiển thị overlay
         document.querySelector('.review-box').style.display = 'block'; // Hiển thị hộp
     });
     // Xử lý nút đóng để ẩn overlay và hộp đánh giá
     document.querySelector('.close-btn').addEventListener('click', function() {
         document.querySelector('.overlay').style.display = 'none'; // Ẩn overlay
         document.querySelector('.review-box').style.display = 'none'; // Ẩn hộp
     });
     // Tùy chọn: Nhấn vào overlay để đóng hộp đánh giá
     document.querySelector('.overlay').addEventListener('click', function() {
         document.querySelector('.overlay').style.display = 'none'; // Ẩn overlay
         document.querySelector('.review-box').style.display = 'none'; // Ẩn hộp
     });
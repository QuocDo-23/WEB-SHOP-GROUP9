
package code.web.webgroup9.controller;

import code.web.webgroup9.dao.ReviewDAO;
import code.web.webgroup9.model.Review;
import code.web.webgroup9.model.ReviewStatistics;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/review")
public class ReviewServlet extends HttpServlet {

    private ReviewDAO reviewDAO;

    @Override
    public void init() throws ServletException {
        reviewDAO = new ReviewDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        // Kiểm tra đăng nhập
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login.jsp?redirect=product-detail.jsp?id=" +
                    request.getParameter("productId"));
            return;
        }

        try {
            // Lấy dữ liệu từ form
            int productId = Integer.parseInt(request.getParameter("productId"));
            int rating = Integer.parseInt(request.getParameter("rating"));
            String comment = request.getParameter("comment");

            // Validate
            if (rating < 1 || rating > 5) {
                request.setAttribute("error", "Rating phải từ 1-5 sao");
                request.getRequestDispatcher("product-detail.jsp?id=" + productId)
                        .forward(request, response);
                return;
            }

            // Tạo review object
            Review review = new Review();
            review.setProductId(productId);
            review.setUserId(userId);
            review.setRating(rating);
            review.setText(comment);

            // Lưu vào database
            reviewDAO.addReview(review);

            // Update product rating (optional - có thể tính lại rating trung bình)
            updateProductRating(productId);

            // Redirect về trang chi tiết với thông báo thành công
            response.sendRedirect("product-detail.jsp?id=" + productId + "&reviewSuccess=true");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Dữ liệu không hợp lệ");
            request.getRequestDispatcher("product-detail.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi khi gửi đánh giá");
            request.getRequestDispatcher("product-detail.jsp").forward(request, response);
        }
    }

    /**
     * Update lại rating trung bình của sản phẩm
     */
    private void updateProductRating(int productId) {
        try {
          ReviewStatistics stats = reviewDAO.getReviewStatistics(productId);

            // Update vào bảng Product
            // Bạn có thể thêm method updateRating trong ProductDAO
            // productDAO.updateRating(productId, stats.getAverageRating());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package code.web.webgroup9.service;

import code.web.webgroup9.dao.ReviewDAO;
import code.web.webgroup9.model.Review;
import code.web.webgroup9.model.ReviewStatistics;

import java.util.List;

public class ReviewService {

    private final ReviewDAO reviewDAO;

    public ReviewService() {
        this.reviewDAO = new ReviewDAO();
    }

    public List<Review> getReviewsByProductId(int productId) {
        return reviewDAO.getReviewsByProductId(productId);
    }

    public ReviewStatistics getReviewStatistics(int productId) {
        return reviewDAO.getReviewStatistics(productId);
    }

    public boolean addReview(Review review) {
        int rows = reviewDAO.addReview(review);
        return rows > 0;
    }
    // ===== ADMIN =====

    // Lấy toàn bộ đánh giá
    public List<Review> getAllReviews() {
        return reviewDAO.getAllReviews();
    }

    // Lấy đánh giá theo trạng thái
    public List<Review> getReviewsByStatus(int status) {
        return reviewDAO.getReviewsByStatus(status);
    }

    // Duyệt / Ẩn đánh giá
    public boolean updateReviewStatus(int reviewId, int status) {
        return reviewDAO.updateReviewStatus(reviewId, status) > 0;
    }

    // Xóa đánh giá
    public boolean deleteReview(int reviewId) {
        return reviewDAO.deleteReview(reviewId) > 0;
    }

    // Thống kê đánh giá cho admin (toàn hệ thống)
    public ReviewStatistics getAdminReviewStatistics() {
        return reviewDAO.getAdminReviewStatistics();
    }

}
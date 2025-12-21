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
}
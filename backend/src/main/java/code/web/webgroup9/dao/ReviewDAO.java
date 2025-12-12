package code.web.webgroup9.dao;

import code.web.webgroup9.model.Review;
import code.web.webgroup9.model.ReviewStatistics;
import org.jdbi.v3.core.Jdbi;
import java.util.List;

public class ReviewDAO {
    private final Jdbi jdbi;

    public ReviewDAO() {
        this.jdbi = BaseDao.get();
    }

    /**
     * Lấy tất cả reviews của một sản phẩm
     */
    public List<Review> getReviewsByProductId(int productId) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT r.*, u.name as user_name " +
                                        "FROM Review_Product r " +
                                        "LEFT JOIN User u ON r.user_id = u.id " +
                                        "WHERE r.product_id = :productId " +
                                        "ORDER BY r.date DESC"
                        )
                        .bind("productId", productId)
                        .mapToBean(Review.class)
                        .list()
        );
    }

    /**
     * Tính rating trung bình
     */
    public ReviewStatistics getReviewStatistics(int productId) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT " +
                                        "COUNT(*) as total_reviews, " +
                                        "COALESCE(AVG(rating), 0) as average_rating, " +
                                        "SUM(CASE WHEN rating = 5 THEN 1 ELSE 0 END) as five_stars, " +
                                        "SUM(CASE WHEN rating = 4 THEN 1 ELSE 0 END) as four_stars, " +
                                        "SUM(CASE WHEN rating = 3 THEN 1 ELSE 0 END) as three_stars, " +
                                        "SUM(CASE WHEN rating = 2 THEN 1 ELSE 0 END) as two_stars, " +
                                        "SUM(CASE WHEN rating = 1 THEN 1 ELSE 0 END) as one_star " +
                                        "FROM Review_Product " +
                                        "WHERE product_id = :productId"
                        )
                        .bind("productId", productId)
                        .mapToBean(ReviewStatistics.class)
                        .one()
        );
    }

    /**
     * Thêm review mới
     */
    public int addReview(Review review) {
        return jdbi.withHandle(handle ->
                handle.createUpdate(
                                "INSERT INTO Review_Product (product_id, user_id, text, img, rating, date) " +
                                        "VALUES (:productId, :userId, :text, :img, :rating, NOW())"
                        )
                        .bind("productId", review.getProductId())
                        .bind("userId", review.getUserId())
                        .bind("text", review.getText())
                        .bind("img", review.getImg())
                        .bind("rating", review.getRating())
                        .execute()
        );
    }


}

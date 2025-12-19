package code.web.webgroup9.dao;

import code.web.webgroup9.model.Articles;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Optional;

public class ArticleDAO {
    private final Jdbi jdbi;

    public ArticleDAO() {
        this.jdbi = BaseDao.get();
    }

    /**
     * Lấy tất cả bài viết với hình ảnh
     */
    public List<Articles> getAllArticles() {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT a.*, c.name as category_name, " +
                                        "(SELECT img FROM Image WHERE type = 'articles' AND ref_id = a.id LIMIT 1) as main_img " +
                                        "FROM Articles a " +
                                        "LEFT JOIN Categories c ON a.category_id = c.id " +
                                        "ORDER BY a.date_of_posting DESC"
                        )
                        .mapToBean(Articles.class)
                        .list()
        );
    }

    /**
     * Lấy bài viết theo ID với hình ảnhs
     */
    public Optional<Articles> getArticleById(int id) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT a.*, c.name as category_name, " +
                                        "(SELECT img FROM Image WHERE type = 'articles' AND ref_id = a.id LIMIT 1) as main_img " +
                                        "FROM Articles a " +
                                        "LEFT JOIN Categories c ON a.category_id = c.id " +
                                        "WHERE a.id = :id"
                        )
                        .bind("id", id)
                        .mapToBean(Articles.class)
                        .findFirst()
        );
    }

    /**
     * Lấy bài viết nổi bật với hình ảnh
     */
    public List<Articles> getFeaturedArticles(int limit) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT a.*, c.name as category_name, " +
                                        "(SELECT img FROM Image WHERE type = 'articles' AND ref_id = a.id LIMIT 1) as main_img " +
                                        "FROM Articles a " +
                                        "LEFT JOIN Categories c ON a.category_id = c.id " +
                                        "WHERE a.feature = TRUE " +
                                        "ORDER BY a.date_of_posting DESC " +
                                        "LIMIT :limit"
                        )
                        .bind("limit", limit)
                        .mapToBean(Articles.class)
                        .list()
        );
    }

    /**
     * Lấy bài viết mới nhất với hình ảnh
     */
    public List<Articles> getLatestArticles(int limit) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT a.*, c.name as category_name, " +
                                        "(SELECT img FROM Image WHERE type = 'articles' AND ref_id = a.id LIMIT 1) as main_img " +
                                        "FROM Articles a " +
                                        "LEFT JOIN Categories c ON a.category_id = c.id " +
                                        "ORDER BY a.date_of_posting DESC " +
                                        "LIMIT :limit"
                        )
                        .bind("limit", limit)
                        .mapToBean(Articles.class)
                        .list()
        );
    }

    /**
     * Lấy bài viết với phân trang và hình ảnh
     */
    public List<Articles> getArticlesWithPagination(int page, int pageSize, String sortBy) {
        int offset = (page - 1) * pageSize;

        String orderClause; // Default: newest first
        if ("oldest".equals(sortBy)) {
            orderClause = "a.date_of_posting ASC";
        } else {
            orderClause = "a.date_of_posting DESC";
        }

        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT a.*, c.name as category_name, " +
                                        "(SELECT img FROM Image WHERE type = 'articles' AND ref_id = a.id LIMIT 1) as main_img " +
                                        "FROM Articles a " +
                                        "LEFT JOIN Categories c ON a.category_id = c.id " +
                                        "WHERE (a.feature = FALSE OR a.feature IS NULL) " +
                                        "ORDER BY " + orderClause + " " +
                                        "LIMIT :limit OFFSET :offset"
                        )
                        .bind("limit", pageSize)
                        .bind("offset", offset)
                        .mapToBean(Articles.class)
                        .list()
        );
    }

    /**
     * Đếm tổng số bài viết
     */
    public int getTotalArticles() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT COUNT(*) FROM Articles a " +
                                "WHERE (a.feature = FALSE OR a.feature IS NULL) "
                        )
                        .mapTo(Integer.class)
                        .one()
        );
    }

    /**
     * Lấy bài viết theo category
     */
    public List<Articles> getArticlesByCategory(int categoryId) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT a.*, c.name as category_name " +
                                        "FROM Articles a " +
                                        "LEFT JOIN Categories c ON a.category_id = c.id " +
                                        "WHERE a.category_id = :categoryId " +
                                        "ORDER BY a.date_of_posting DESC"
                        )
                        .bind("categoryId", categoryId)
                        .mapToBean(Articles.class)
                        .list()
        );
    }
}
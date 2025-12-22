package code.web.webgroup9.dao;

import code.web.webgroup9.model.Articles.Article;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class ArticleDAO {

    private final Jdbi jdbi;

    public ArticleDAO() {
        this.jdbi = BaseDao.get();
    }

    /**
     * Lấy 4 bài viết nổi bật
     */
    public List<Article> getFeaturedArticles(int featuredLimit) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT a.*, c.name AS categoryName, " +
                                        "(SELECT img FROM Image WHERE type = 'articles' AND ref_id = a.id ORDER BY id LIMIT 1) as mainImg " +
                                        "FROM Articles a " +
                                        "LEFT JOIN Categories c ON a.category_id = c.id " +
                                        "WHERE a.feature = TRUE " +
                                        "ORDER BY a.date_of_posting DESC " +
                                        "LIMIT :featuredLimit"
                        )
                        .bind("featuredLimit", featuredLimit)
                        .mapToBean(Article.class)
                        .list()
        );
    }
    /**
     * Lấy 4 bài viết cho trang chủ
     */
    public List<Article> getArticles(int limit) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT a.*, c.name AS categoryName, " +
                                        "(SELECT img FROM Image WHERE type = 'articles' AND ref_id = a.id ORDER BY id LIMIT 1) as mainImg " +
                                        "FROM Articles a " +
                                        "LEFT JOIN Categories c ON a.category_id = c.id " +
                                        "ORDER BY a.date_of_posting DESC " +
                                        "LIMIT :limit"
                        )
                        .bind("limit", limit)
                        .mapToBean(Article.class)
                        .list()
        );
    }

    /**
     * Lấy bài viết với phân trang và hình ảnh
     */
    public List<Article> getArticlesWithPagination(int page, int pageSize, String sortBy) {
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
                        .mapToBean(Article.class)
                        .list()
        );
    }



    /**
     * Đếm tổng số bài viết
     */
    public int getTotalArticles() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT COUNT(*) FROM Articles")
                        .mapTo(Integer.class)
                        .one()
        );
    }

    /**
     * Lấy bài viết theo ID
     */
    public Article getArticleById(int id) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT a.*, c.name AS categoryName, " +
                                        "(SELECT img FROM Image WHERE type = 'articles' AND ref_id = a.id ORDER BY id LIMIT 1) as mainImg " +
                                        "FROM Articles a " +
                                        "LEFT JOIN Categories c ON a.category_id = c.id " +
                                        "WHERE a.id = :id"
                        )
                        .bind("id", id)
                        .mapToBean(Article.class)
                        .findFirst()
                        .orElse(null)
        );
    }

    /**
     * Lấy bài viết theo slug
     */
    public Article getArticleBySlug(String slug) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT a.*, c.name AS categoryName, " +
                                        "(SELECT img FROM Image WHERE type = 'articles' AND ref_id = a.id ORDER BY id LIMIT 1) as mainImg " +
                                        "FROM Articles a " +
                                        "LEFT JOIN Categories c ON a.category_id = c.id " +
                                        "WHERE a.slug = :slug"
                        )
                        .bind("slug", slug)
                        .mapToBean(Article.class)
                        .findFirst()
                        .orElse(null)
        );
    }


}

package code.web.webgroup9.dao;

import code.web.webgroup9.model.Articles.Article;
import code.web.webgroup9.model.Articles.ArticlesContent;
import code.web.webgroup9.model.Articles.ContentType;
import org.jdbi.v3.core.Jdbi;
import java.util.List;

public class ArticleContentDAO {
    private final Jdbi jdbi;

    public ArticleContentDAO() {
        this.jdbi = BaseDao.get();
    }

    /**
     * Lấy bài viết theo ID
     */
    public List<ArticlesContent> getContentByArticleId(int articleId) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT * FROM articles_content " +
                                        "WHERE article_id = :articleId " +
                                        "ORDER BY display_order ASC"
                        )
                        .bind("articleId", articleId)
                        .mapToBean(ArticlesContent.class)
                        .list()
        );
    }

    /**
     * Lấy tất cả bài viết
     */
    public List<Article> getAllArticles() {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT a.*, c.name AS categoryName " +
                                        "FROM articles a " +
                                        "LEFT JOIN categories c ON a.category_id = c.id " +
                                        "ORDER BY a.date_of_posting DESC"
                        )
                        .mapToBean(Article.class)
                        .list()
        );
    }

    /**
     * Lấy bài viết liên quan (cùng danh mục, khác ID)
     */
    public List<Article> getRelatedArticles(int currentArticleId, Integer categoryId, int limit) {
        if (categoryId == null) {
            // Nếu không có category, lấy bài viết mới nhất
            return jdbi.withHandle(handle ->
                    handle.createQuery(
                                    "SELECT a.*, c.name AS categoryName, " +
                                            "(SELECT img FROM Image WHERE type = 'Articles' AND ref_id = a.id ORDER BY id LIMIT 1) as mainImg " +
                                            "FROM articles a " +
                                            "LEFT JOIN categories c ON a.category_id = c.id " +
                                            "WHERE a.id != :currentId " +
                                            "ORDER BY a.date_of_posting DESC " +
                                            "LIMIT :limit"
                            )
                            .bind("currentId", currentArticleId)
                            .bind("limit", limit)
                            .mapToBean(Article.class)
                            .list()
            );
        }

        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT a.*, c.name AS categoryName, " +
                                        "(SELECT img FROM Image WHERE type = 'Articles' AND ref_id = a.id ORDER BY id LIMIT 1) as mainImg " +
                                        "FROM articles a " +
                                        "LEFT JOIN categories c ON a.category_id = c.id " +
                                        "WHERE a.category_id = :categoryId " +
                                        "AND a.id != :currentId " +
                                        "ORDER BY a.date_of_posting DESC " +
                                        "LIMIT :limit"
                        )
                        .bind("categoryId", categoryId)
                        .bind("currentId", currentArticleId)
                        .bind("limit", limit)
                        .mapToBean(Article.class)
                        .list()
        );
    }

}
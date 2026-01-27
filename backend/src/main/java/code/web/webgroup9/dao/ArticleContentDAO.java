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
     * Lấy nd bài viết theo ID
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

    /**
     * Thêm nội dung mới vào bài viết
     */
    public boolean insertContent(ArticlesContent content) {
        return jdbi.withHandle(handle ->
                handle.createUpdate(
                                "INSERT INTO articles_content (article_id, content, content_type, display_order) " +
                                        "VALUES (:articleId, :content, :contentType, :displayOrder)"
                        )
                        .bind("articleId", content.getArticleId())
                        .bind("content", content.getContent())
                        .bind("contentType", content.getContentType())
                        .bind("displayOrder", content.getDisplayOrder())
                        .execute() > 0
        );
    }

    /**
     * update nội dung bài viết
     */
    public boolean updateContent(ArticlesContent content) {
        return jdbi.withHandle(handle ->
                handle.createUpdate(
                                "UPDATE articles_content SET " +
                                        "content = :content, " +
                                        "content_type = :contentType, " +
                                        "display_order = :displayOrder " +
                                        "WHERE id = :id"
                        )
                        .bind("content", content.getContent())
                        .bind("contentType", content.getContentType())
                        .bind("displayOrder", content.getDisplayOrder())
                        .bind("id", content.getId())
                        .execute() > 0
        );
    }

    /**
     * Xóa nội dung bài viết
     */
    public boolean deleteContent(int id) {
        return jdbi.withHandle(handle ->
                handle.createUpdate("DELETE FROM articles_content WHERE id = :id")
                        .bind("id", id)
                        .execute() > 0
        );
    }

    /**
     * Lấy nội dung theo ID
     */
    public ArticlesContent getContentById(int id) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM articles_content WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(ArticlesContent.class)
                        .findFirst()
                        .orElse(null)
        );
    }
    
    /**
     * Lấy thứ tự hiển thị tiếp theo cho bài viết
     */
    public int getNextDisplayOrder(int articleId) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT COALESCE(MAX(display_order), 0) + 1 FROM articles_content WHERE article_id = :articleId")
                        .bind("articleId", articleId)
                        .mapTo(Integer.class)
                        .one()
        );
    }

}
package code.web.webgroup9.dao;

import code.web.webgroup9.model.Articles.ArticlesContent;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class ArticleContentDAO {
    private final Jdbi jdbi;

    public ArticleContentDAO() {
        this.jdbi = BaseDao.get();
    }

    /**
     * Lấy nội dung của bài viết khi người dùng bấm vào bài viết
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
     * Thêm mới một khối nội dung vào bài viết.
     *
     * @param content Đối tượng ArticlesContent chứa thông tin cần thêm.
     * @return Số dòng đã được thêm vào (thường là 1 nếu thành công).
     */
    public int addContent(ArticlesContent content) {
        return jdbi.withHandle(handle ->
                handle.createUpdate(
                                "INSERT INTO articles_content (article_id, content, content_type, display_order) " +
                                        "VALUES (:articleId, :content, :contentType, :displayOrder)"
                        )
                        .bindBean(content)
                        .execute()
        );
    }

//    public int updateContent
}
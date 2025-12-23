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

    /**
     * Cập nhật một khối nội dung đã có.
     *
     * @param content Đối tượng ArticlesContent chứa thông tin cần cập nhật. ID của đối tượng này sẽ được dùng để xác định dòng cần cập nhật.
     * @return Số dòng đã được cập nhật (thường là 1 nếu thành công).
     */
    public int updateContent(ArticlesContent content) {
        return jdbi.withHandle(handle ->
                handle.createUpdate(
                                "UPDATE articles_content " +
                                        "SET content = :content, content_type = :contentType, display_order = :displayOrder " +
                                        "WHERE id = :id"
                        )
                        .bindBean(content)
                        .execute()
        );
    }

    /**
     * Xóa một khối nội dung.
     *
     * @param id ID của khối nội dung cần xóa.
     * @return Số dòng đã được xóa (thường là 1 nếu thành công).
     */
    public int deleteContent(int id) {
        return jdbi.withHandle(handle ->
                handle.createUpdate("DELETE FROM articles_content WHERE id = :id")
                        .bind("id", id)
                        .execute()
        );
    }

    /**
     * Xóa tất cả nội dung của một bài viết.
     * Thường được dùng khi xóa bài viết chính hoặc khi muốn ghi đè lại toàn bộ nội dung.
     *
     * @param articleId ID của bài viết.
     * @return Số dòng đã được xóa.
     */
    public int deleteAllContentByArticleId(int articleId) {
        return jdbi.withHandle(handle ->
                handle.createUpdate("DELETE FROM articles_content WHERE article_id = :articleId")
                        .bind("articleId", articleId)
                        .execute()
        );
    }
}

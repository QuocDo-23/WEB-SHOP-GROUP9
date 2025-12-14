package code.web.webgroup9.dao;

import code.web.webgroup9.model.Articles;
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
    public List<Articles> getFeaturedArticles() {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT a.*, c.name AS categoryName, " +
                                        "(SELECT img FROM Image WHERE type = 'articles' AND ref_id = a.id ORDER BY id LIMIT 1) as mainImg "+
                                        "FROM Articles a " +
                                        "LEFT JOIN Categories c ON a.category_id = c.id " +
                                        "WHERE a.feature = TRUE " +
                                        "ORDER BY a.date_of_posting DESC " +
                                        "LIMIT 4"
                        )
                        .mapToBean(Articles.class)
                        .list()
        );
    }

    /**
     * Lấy danh sách bài viết có phân trang + sắp xếp
     */
    public List<Articles> getArticlesByPage(int page, int pageSize, String sortBy) {
        int offset = (page - 1) * pageSize;

        String orderBy = "ORDER BY a.date_of_posting DESC";
        if ("oldest".equals(sortBy)) {
            orderBy = "ORDER BY a.date_of_posting ASC";
        } else if ("popular".equals(sortBy)) {
            orderBy = "ORDER BY a.feature DESC, a.date_of_posting DESC";
        }

        String sql =
                "SELECT a.*, c.name AS categoryName, " +
                        "(SELECT img FROM Image WHERE type = 'articles' AND ref_id = a.id ORDER BY id LIMIT 1) as mainImg "+
                        "FROM Articles a " +
                        "LEFT JOIN Categories c ON a.category_id = c.id " +
                        orderBy +
                        " LIMIT :limit OFFSET :offset";

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
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
                handle.createQuery("SELECT COUNT(*) FROM Articles")
                        .mapTo(Integer.class)
                        .one()
        );
    }

    /**
     * Lấy bài viết theo ID
     */
    public Articles getArticleById(int id) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT a.*, c.name AS categoryName, " +
                                        "(SELECT img FROM Image WHERE type = 'articles' AND ref_id = a.id ORDER BY id LIMIT 1) as mainImg "+
                                        "FROM Articles a " +
                                        "LEFT JOIN Categories c ON a.category_id = c.id " +
                                        "WHERE a.id = :id"
                        )
                        .bind("id", id)
                        .mapToBean(Articles.class)
                        .findFirst()
                        .orElse(null)
        );
    }

    /**
     * Lấy bài viết theo slug
     */
    public Articles getArticleBySlug(String slug) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT a.*, c.name AS categoryName, " +
                                        "(SELECT img FROM Image WHERE type = 'articles' AND ref_id = a.id ORDER BY id LIMIT 1) as mainImg "+
                                        "FROM Articles a " +
                                        "LEFT JOIN Categories c ON a.category_id = c.id " +
                                        "WHERE a.slug = :slug"
                        )
                        .bind("slug", slug)
                        .mapToBean(Articles.class)
                        .findFirst()
                        .orElse(null)
        );
    }
}

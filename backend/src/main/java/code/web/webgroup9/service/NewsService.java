package code.web.webgroup9.service;

import code.web.webgroup9.dao.ArticleDAO;
import code.web.webgroup9.model.Articles.Article;

import java.util.List;

public class NewsService {
    private final ArticleDAO articleDAO;

    public NewsService() {
        this.articleDAO = new ArticleDAO();
    }

    public List<Article> getFeaturedArticles(int limit) {
        return articleDAO.getFeaturedArticle(limit);
    }

    public List<Article> getArticlesWithPagination(int page, int pageSize, String sortBy) {
        return articleDAO.getArticleWithPagination(page, pageSize, sortBy);
    }

    public int getTotalArticles() {
        return articleDAO.getTotalArticle();
    }

    public Article getArticlesById(int id) {
        return articleDAO.getArticleById(id);
    }
    public List<Article> getArticle(int limit) {
        return articleDAO.getArticle(limit);
    }

    public Article getArticlesBySlug(String slug) {
        return articleDAO.getArticleBySlug(slug);
    }

}

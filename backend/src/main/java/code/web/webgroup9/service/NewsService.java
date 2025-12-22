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
        return articleDAO.getFeaturedArticles(limit);
    }

    public List<Article> getArticlesWithPagination(int page, int pageSize, String sortBy) {
        return articleDAO.getArticlesWithPagination(page, pageSize, sortBy);
    }

    public int getTotalArticles() {
        return articleDAO.getTotalArticles();
    }

    public Article getArticleById(int id) {
        return articleDAO.getArticleById(id);
    }
    public List<Article> getArticles(int limit) {
        return articleDAO.getArticles(limit);
    }

    public Article getArticleBySlug(String slug) {
        return articleDAO.getArticleBySlug(slug);
    }

}

package code.web.webgroup9.service;

import code.web.webgroup9.dao.ArticleDAO;
import code.web.webgroup9.model.Articles;

import java.util.List;

public class NewsService {
    private final ArticleDAO articleDAO;

    public NewsService() {
        this.articleDAO = new ArticleDAO();
    }

    public List<Articles> getFeaturedArticles(int limit) {
        return articleDAO.getFeaturedArticles(limit);
    }

    public List<Articles> getArticlesWithPagination(int page, int pageSize, String sortBy) {
        return articleDAO.getArticlesWithPagination(page, pageSize, sortBy);
    }

    public int getTotalArticles() {
        return articleDAO.getTotalArticles();
    }

    public Articles getArticleById(int id) {
        return articleDAO.getArticleById(id);
    }
    public List<Articles> getArticles(int limit) {
        return articleDAO.getArticles(limit);
    }

    public Articles getArticleBySlug(String slug) {
        return articleDAO.getArticleBySlug(slug);
    }

}

package code.web.webgroup9.controller;

import code.web.webgroup9.dao.ArticleDAO;
import code.web.webgroup9.model.Articles;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/news")
public class NewsServlet extends HttpServlet {
    private ArticleDAO articleDAO;

    @Override
    public void init() {
        articleDAO = new ArticleDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy tham số phân trang và sắp xếp
        String pageParam = request.getParameter("page");
        String sortParam = request.getParameter("sort");

        int currentPage = 1;
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                currentPage = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        String sortBy = sortParam != null ? sortParam : "newest";
        int pageSize = 4;

        // Lấy bài viết nổi bật
        List<Articles> featuredArticles = articleDAO.getFeaturedArticles();

        // Lấy bài viết theo trang
        List<Articles> articles = articleDAO.getArticlesWithPagination(currentPage, pageSize, sortBy);

        // Tính tổng số trang
        int totalArticles = articleDAO.getTotalArticles();
        int totalPages = (int) Math.ceil((double) totalArticles / pageSize);

        // Set attributes
        request.setAttribute("featuredArticles", featuredArticles);
        request.setAttribute("articles", articles);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("sortBy", sortBy);

        // Forward to JSP
        request.getRequestDispatcher("/news.jsp").forward(request, response);
    }
}
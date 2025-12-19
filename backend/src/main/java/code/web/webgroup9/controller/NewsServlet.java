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

@WebServlet(name = "NewsServlet", urlPatterns = {"/news"})
public class NewsServlet extends HttpServlet {

    private ArticleDAO articleDAO;
    private static final int PAGE_SIZE = 4; // Số bài viết mỗi trang
    private static final int FEATURED_LIMIT = 4; // Số bài viết nổi bật

    @Override
    public void init() throws ServletException {
        articleDAO = new ArticleDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        try {

            // Lấy tham số
            String pageParam = request.getParameter("page");
            int currentPage = 1;
            if (pageParam != null && !pageParam.isEmpty()) {
                try {
                    currentPage = Integer.parseInt(pageParam);
                    if (currentPage < 1) currentPage = 1;
                } catch (NumberFormatException e) {
                    currentPage = 1;
                }
            }

            String sortBy = request.getParameter("sort");
            if (sortBy == null || sortBy.isEmpty()) {
                sortBy = "newest";
            }


            // Lấy bài viết nổi bật
            List<Articles> featuredArticles = articleDAO.getFeaturedArticles(FEATURED_LIMIT);


            // Lấy danh sách bài viết
            List<Articles> articles = articleDAO.getArticlesWithPagination(currentPage, PAGE_SIZE, sortBy);

            // Tính tổng số trang
            int totalArticles = articleDAO.getTotalArticles();
            int totalPages = (int) Math.ceil((double) totalArticles / PAGE_SIZE);

            // Set attributes
            request.setAttribute("featuredArticles", featuredArticles);
            request.setAttribute("articles", articles);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("sortBy", sortBy);


            // Forward
            request.getRequestDispatcher("/news.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("Error in NewsServlet: " + e.getMessage());
            e.printStackTrace();

            request.setAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
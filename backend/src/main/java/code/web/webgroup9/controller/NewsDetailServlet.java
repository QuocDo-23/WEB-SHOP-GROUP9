package code.web.webgroup9.controller;


import code.web.webgroup9.model.Articles.Article;
import code.web.webgroup9.model.Articles.ArticlesContent;
import code.web.webgroup9.service.NewsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "NewsDetailServlet", value = "/news-detail")
public class NewsDetailServlet extends HttpServlet {

    private final NewsService newsService = new NewsService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String slugParam = request.getParameter("slug");

        Article article = null;

        try {
            // Tìm bài viết theo ID hoặc Slug
            if (idParam != null && !idParam.isEmpty()) {
                int articleId = Integer.parseInt(idParam);
                article = newsService.getArticleById(articleId);
            } else if (slugParam != null && !slugParam.isEmpty()) {
                article = newsService.getArticlesBySlug(slugParam);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Thiếu ID hoặc Slug của bài viết.");
                return;
            }

            // Kiểm tra nếu không tìm thấy bài viết
            if (article == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "Không tìm thấy bài viết.");
                return;
            }

            // Lấy nội dung chi tiết của bài viết
            List<ArticlesContent> contents = newsService.getContentByArticleId(article.getId());

            // Lấy bài viết liên quan (cùng danh mục, loại trừ bài hiện tại)
            List<Article> relatedArticles = newsService.getRelatedArticles(article.getId(), article.getCategoryId(), 3);

            // Đặt thuộc tính vào request
            request.setAttribute("article", article);
            request.setAttribute("contents", contents);
            request.setAttribute("relatedArticles", relatedArticles);

            // Chuyển tiếp đến trang JSP
            request.getRequestDispatcher("/new-detail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Định dạng ID không hợp lệ.");
        }
    }
}
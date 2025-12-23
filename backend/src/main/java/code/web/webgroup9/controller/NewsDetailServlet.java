package code.web.webgroup9.controller;

import code.web.webgroup9.dao.ArticleContentDAO;
import code.web.webgroup9.dao.ArticleDAO;
import code.web.webgroup9.model.Articles.Article;
import code.web.webgroup9.model.Articles.ArticlesContent;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "NewsDetailServlet", value = "/news-detail")
public class NewsDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String slugParam = request.getParameter("slug");

        ArticleDAO articleDAO = new ArticleDAO();
        ArticleContentDAO contentDAO = new ArticleContentDAO();
        Article article = null;

        try {
            if (idParam != null && !idParam.isEmpty()) {
                // Tìm theo ID
                int articleId = Integer.parseInt(idParam);
                article = articleDAO.getArticleById(articleId);
            } else if (slugParam != null && !slugParam.isEmpty()) {
                // Tìm theo Slug
                article = articleDAO.getArticleBySlug(slugParam);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Article ID or Slug is missing.");
                return;
            }

            // Kiểm tra nếu không tìm thấy bài viết
            if (article == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Article not found.");
                return;
            }

            // Lấy nội dung chi tiết
            List<ArticlesContent> contents = contentDAO.getContentByArticleId(article.getId());

            // Đặt thuộc tính vào request
            request.setAttribute("article", article);
            request.setAttribute("contents", contents);

            // Chuyển tiếp đến trang JSP
            request.getRequestDispatcher("/new-detail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Article ID format.");
        }
    }
}

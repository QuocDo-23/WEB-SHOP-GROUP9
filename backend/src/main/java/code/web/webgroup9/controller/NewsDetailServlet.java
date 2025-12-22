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
        // 1. Lấy ID bài viết từ URL
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Article ID is missing.");
            return;
        }

        try {
            int articleId = Integer.parseInt(idParam);

            // 2. Khởi tạo DAO
            ArticleDAO articleDAO = new ArticleDAO();
            ArticleContentDAO contentDAO = new ArticleContentDAO();

            // 3. Lấy dữ liệu từ DAO
            Article article = articleDAO.getArticleById(articleId);
            List<ArticlesContent> contents = contentDAO.getContentByArticleId(articleId);

            // 4. Kiểm tra nếu không tìm thấy bài viết
            if (article == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Article not found.");
                return;
            }

            // 5. Đặt thuộc tính vào request để JSP có thể truy cập
            request.setAttribute("article", article);
            request.setAttribute("contents", contents);

            // 6. Chuyển tiếp đến trang JSP
            request.getRequestDispatcher("/new-detail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Article ID format.");
        }
    }
}

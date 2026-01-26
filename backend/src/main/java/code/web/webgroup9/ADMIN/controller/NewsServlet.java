package code.web.webgroup9.ADMIN.controller;

import code.web.webgroup9.dao.ArticleDAO;
import code.web.webgroup9.model.Articles.Article;
import code.web.webgroup9.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "NewsServlet", value = "/admin/news")
public class NewsServlet extends HttpServlet {

    private ArticleDAO articleDAO;

    @Override
    public void init() throws ServletException {
        articleDAO = new ArticleDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Kiểm tra quyền admin
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        User admin = (User) session.getAttribute("user");
        if (admin.getRoleId() != 1) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String searchKeyword = request.getParameter("search");
        List<Article> newsList;

        if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
            // lấy trang đầu
            newsList = articleDAO.searchArticles(searchKeyword, 50, 0); 
        } else {
            // lấy 50 bài mới nhất
            newsList = articleDAO.getArticle(50); 
        }

        request.setAttribute("newsList", newsList);
        request.getRequestDispatcher("/Admin/news.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        // Kiểm tra quyền admin
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        User admin = (User) session.getAttribute("user");
        if (admin.getRoleId() != 1) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String action = request.getParameter("action");
        String message = "";

        String idStr = request.getParameter("id");
        int articleId = (idStr != null && !idStr.isEmpty()) ? Integer.parseInt(idStr) : 0;

        String title = request.getParameter("newsTitle");
        String imageLink = request.getParameter("imageLink");
        String newsCategoryStr = request.getParameter("newsCategory");
        String content = request.getParameter("newsContent");

        // bài viết thường
        int categoryId = (newsCategoryStr != null && !newsCategoryStr.isEmpty()) ? Integer.parseInt(newsCategoryStr) : 2;

        Article article = new Article();
        article.setId(articleId);
        article.setTitle(title);
        article.setMainImg(imageLink);
        article.setCategoryId(categoryId);
        article.setDescription(content);
        article.setDateOfPosting(Timestamp.valueOf(LocalDateTime.now()));
        article.setSlug(title.toLowerCase().replaceAll("\\s+", "-"));
        //categoryId = 1 là bài viết nổi bậc
        article.setFeature(categoryId == 1);

        boolean success = false;

        /**
         * Thêm tin mới
         */
        if ("add".equals(action)) {
            int newId = articleDAO.insertArticle(article);
            success = newId > 0;
            message = success ? "Thêm tin tức mới thành công!" : "Thêm tin tức mới thất bại!";
        }

        /**
         * cập nhât tin tức
         */
        else if ("update".equals(action)) {
            int updatedRows = articleDAO.updateArticle(article);
            success = updatedRows > 0;
            message = success ? "Cập nhật tin tức thành công!" : "Cập nhật tin tức thất bại!";
        }

        /**
         * xóa tin tức
         */
        else if ("delete".equals(action)) {
            int deletedRows = articleDAO.deleteArticle(articleId);
            success = deletedRows > 0;
            message = success ? "Xóa tin tức thành công!" : "Xóa tin tức thất bại!";
        }

        response.sendRedirect(request.getContextPath() + "/Admin/news?message=" + message);
    }
}
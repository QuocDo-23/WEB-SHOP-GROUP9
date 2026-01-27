package code.web.webgroup9.ADMIN.controller;

import code.web.webgroup9.dao.ArticleContentDAO;
import code.web.webgroup9.dao.ArticleDAO;
import code.web.webgroup9.model.Articles.Article;
import code.web.webgroup9.model.Articles.ArticlesContent;
import code.web.webgroup9.model.Articles.ContentType;
import code.web.webgroup9.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/news-content")
public class NewsDetailServlet extends HttpServlet {

    private ArticleDAO articleDAO;
    private ArticleContentDAO articleContentDAO;

    @Override
    public void init() throws ServletException {
        articleDAO = new ArticleDAO();
        articleContentDAO = new ArticleContentDAO();
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

        String articleIdStr = request.getParameter("articleId");
        if (articleIdStr == null || articleIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/news");
            return;
        }

        try {
            int articleId = Integer.parseInt(articleIdStr);
            Article article = articleDAO.getArticleById(articleId);
            
            if (article == null) {
                response.sendRedirect(request.getContextPath() + "/admin/news");
                return;
            }

            List<ArticlesContent> contents = articleContentDAO.getContentByArticleId(articleId);

            request.setAttribute("article", article);
            request.setAttribute("contents", contents);
            request.getRequestDispatcher("/Admin/news_content.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/news");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
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
        String articleIdStr = request.getParameter("articleId");
        
        if (articleIdStr == null || articleIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/news");
            return;
        }

        int articleId = Integer.parseInt(articleIdStr);
        String message = "";

        try {
            if ("add".equals(action)) {
                String contentText = request.getParameter("content");
                String typeStr = request.getParameter("contentType");
                String orderStr = request.getParameter("displayOrder");

                ArticlesContent content = new ArticlesContent();
                content.setArticleId(articleId);
                content.setContent(contentText);
                content.setContentType(ContentType.valueOf(typeStr));
                
                int order = (orderStr != null && !orderStr.isEmpty()) 
                            ? Integer.parseInt(orderStr) 
                            : articleContentDAO.getNextDisplayOrder(articleId);
                content.setDisplayOrder(order);

                boolean success = articleContentDAO.insertContent(content);
                message = success ? "Thêm nội dung thành công!" : "Thêm nội dung thất bại!";

            } else if ("update".equals(action)) {
                int contentId = Integer.parseInt(request.getParameter("contentId"));
                String contentText = request.getParameter("content");
                String typeStr = request.getParameter("contentType");
                int order = Integer.parseInt(request.getParameter("displayOrder"));

                ArticlesContent content = new ArticlesContent();
                content.setId(contentId);
                content.setArticleId(articleId);
                content.setContent(contentText);
                content.setContentType(ContentType.valueOf(typeStr));
                content.setDisplayOrder(order);

                boolean success = articleContentDAO.updateContent(content);
                message = success ? "Cập nhật nội dung thành công!" : "Cập nhật nội dung thất bại!";

            } else if ("delete".equals(action)) {
                int contentId = Integer.parseInt(request.getParameter("contentId"));
                boolean success = articleContentDAO.deleteContent(contentId);
                message = success ? "Xóa nội dung thành công!" : "Xóa nội dung thất bại!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "Đã xảy ra lỗi: " + e.getMessage();
        }

        response.sendRedirect(request.getContextPath() + "/admin/news-content?articleId=" + articleId + "&message=" + java.net.URLEncoder.encode(message, "UTF-8"));
    }
}
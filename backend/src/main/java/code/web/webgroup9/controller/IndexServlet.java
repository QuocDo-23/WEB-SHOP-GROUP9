package code.web.webgroup9.controller;

import code.web.webgroup9.service.CategoryService;
import code.web.webgroup9.service.NewsService;
import code.web.webgroup9.service.ProductService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("")
public class IndexServlet extends HttpServlet {
    private ProductService productService;
    private CategoryService categoryService;
    private NewsService newsService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
        categoryService = new CategoryService();
        newsService = new NewsService();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("categories", categoryService.getSubCategories());
        request.setAttribute("listProducts", productService.getFeaturedProducts());
        request.setAttribute("listArticle", newsService.getArticles(4));

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
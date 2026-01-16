package code.web.webgroup9.controller;

import code.web.webgroup9.dao.ProductDAO;
import code.web.webgroup9.model.ProductWithDetails;
import code.web.webgroup9.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchServlet", urlPatterns = {"/search-suggestions"})
public class searchServlet extends HttpServlet {

    private ProductService productService;
    @Override
    public void init() throws ServletException {
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String query = request.getParameter("q");

        if (query != null && query.trim().length() >= 2) {
            List<ProductWithDetails> searchResults = productService.searchProducts(query.trim());
            request.setAttribute("searchResults", searchResults);
        }

        request.getRequestDispatcher("/search.jsp").forward(request, response);
    }
}
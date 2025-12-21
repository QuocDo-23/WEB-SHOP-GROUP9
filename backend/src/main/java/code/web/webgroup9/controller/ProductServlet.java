package code.web.webgroup9.controller;

import code.web.webgroup9.service.ProductService;
import code.web.webgroup9.service.CategoryService;  // Thay DAO bằng Service
import code.web.webgroup9.model.ProductWithDetails;
import code.web.webgroup9.model.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    private ProductService productService;
    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
        categoryService = new CategoryService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "list":
                    showProductList(request, response);
                    break;
                case "category":
                    showProductsByCategory(request, response);
                    break;
                default:
                    showProductList(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void showProductList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<ProductWithDetails> allProducts = productService.getAllProductsWithDetails();
        List<Category> categories = categoryService.getSubCategories();

        Map<Integer, List<ProductWithDetails>> productsByCategory = new HashMap<>();
        for (ProductWithDetails product : allProducts) {
            int catId = product.getCategoryId();
            productsByCategory.computeIfAbsent(catId, k -> new ArrayList<>()).add(product);
        }

        request.setAttribute("categories", categories);
        request.setAttribute("productsByCategory", productsByCategory);
        request.setAttribute("totalProducts", allProducts.size());

        request.getRequestDispatcher("products.jsp").forward(request, response);
    }

    private void showProductsByCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int categoryId = Integer.parseInt(request.getParameter("id"));

        List<ProductWithDetails> products = productService.getProductsByCategory(categoryId);
        Category category = categoryService.getCategoryById(categoryId);

        request.setAttribute("products", products);
        request.setAttribute("category", category);

        request.getRequestDispatcher("category-products.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
package code.web.webgroup9.controller;

import code.web.webgroup9.service.ProductService;
import code.web.webgroup9.service.CategoryService;
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
            // Lấy tất cả sản phẩm và danh mục
            List<ProductWithDetails> allProducts = productService.getAllProductsWithDetails();
            List<Category> categories = categoryService.getSubCategories();

            // Nhóm sản phẩm theo danh mục
            Map<Integer, List<ProductWithDetails>> productsByCategory = new HashMap<>();
            for (ProductWithDetails product : allProducts) {
                int catId = product.getCategoryId();
                List<ProductWithDetails> categoryProducts = productsByCategory.computeIfAbsent(catId, k -> new ArrayList<>());

                // Chỉ thêm nếu chưa đủ 8 sản phẩm
                if (categoryProducts.size() < 8) {
                    categoryProducts.add(product);
                }
            }

            // Set attributes
            request.setAttribute("categories", categories);
            request.setAttribute("productsByCategory", productsByCategory);
            request.setAttribute("totalProducts", allProducts.size());

            // Forward đến products.jsp
            request.getRequestDispatcher("products.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }}

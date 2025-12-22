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

<<<<<<< HEAD
=======
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";

        }

>>>>>>> 07c836a785bf7a8f4329f5442006a2aa41cce9d1
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

<<<<<<< HEAD

=======
    /**
     * Hiển thị danh sách  sản phẩm
     */
    private void showProductList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy tất cả categories
        List<Category> categories = categoryDAO.getSubCategories();

        // Map: categoryId -> 8 sản phẩm
        Map<Integer, List<ProductWithDetails>> productsByCategory = new HashMap<>();

        // ===== THÊM MỚI =====
        // Map: categoryId -> tổng số sản phẩm (để biết có hiện "Xem thêm" hay không)
        Map<Integer, Integer> totalProductsByCategory = new HashMap<>();
        // ====================

        for (Category category : categories) {

            // 8 sản phẩm hiển thị mặc định
            List<ProductWithDetails> products =
                    productDAO.getTop8ProductsByCategory(category.getId());
            productsByCategory.put(category.getId(), products);

            // ===== THÊM MỚI =====
            // Tổng số sản phẩm của category đó
            int totalProducts = productDAO.getProductsByCategory(category.getId()).size();
            totalProductsByCategory.put(category.getId(), totalProducts);
            // ====================
        }

        request.setAttribute("categories", categories);
        request.setAttribute("productsByCategory", productsByCategory);

        // ===== THÊM MỚI =====
        request.setAttribute("totalProductsByCategory", totalProductsByCategory);
        // ====================

        request.getRequestDispatcher("products.jsp").forward(request, response);
    }


    /**
     * Hiển thị chi tiết sản phẩm
     */
    private void showProductDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int productId = Integer.parseInt(request.getParameter("id"));

        Optional<ProductWithDetails> product = productDAO.getProductById(productId);

        if (product.isPresent()) {
            request.setAttribute("product", product.get());
            request.getRequestDispatcher("product-detail.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy sản phẩm");
        }
    }

    /**
     * Tìm kiếm sản phẩm
     */
    private void searchProducts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");

        if (keyword != null && !keyword.trim().isEmpty()) {
            List<ProductWithDetails> results = productDAO.searchProducts(keyword);
            request.setAttribute("searchResults", results);
            request.setAttribute("keyword", keyword);
        }

        request.getRequestDispatcher("search-results.jsp").forward(request, response);
    }

    /**
     * Hiển thị sản phẩm theo category
     */
    private void showProductsByCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int categoryId = Integer.parseInt(request.getParameter("id"));

        List<ProductWithDetails> products = productDAO.getProductsByCategory(categoryId);
        Category category = categoryDAO.getCategoryById(categoryId);

        request.setAttribute("products", products);
        request.setAttribute("category", category);

        request.getRequestDispatcher("category-products.jsp").forward(request, response);
    }
>>>>>>> 07c836a785bf7a8f4329f5442006a2aa41cce9d1

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
<<<<<<< HEAD
}
=======

}
>>>>>>> 07c836a785bf7a8f4329f5442006a2aa41cce9d1

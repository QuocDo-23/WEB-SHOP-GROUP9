package code.web.webgroup9.controller;

import code.web.webgroup9.dao.ProductDAO;
import code.web.webgroup9.dao.CategoryDAO;
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

    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set encoding
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
                case "detail":
                    showProductDetail(request, response);
                    break;
                case "search":
                    searchProducts(request, response);
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

    /**
     * Hiển thị danh sách tất cả sản phẩm
     */
    private void showProductList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy tất cả sản phẩm
        List<ProductWithDetails> allProducts = productDAO.getAllProductsWithDetails();

        // Lấy tất cả categories
        List<Category> categories = categoryDAO.getSubCategories();

        // Nhóm sản phẩm theo category
        Map<Integer, List<ProductWithDetails>> productsByCategory = new HashMap<>();
        for (ProductWithDetails product : allProducts) {
            int catId = product.getCategoryId();
            productsByCategory.computeIfAbsent(catId, k -> new ArrayList<>()).add(product);
        }

        // Set attributes
        request.setAttribute("categories", categories);
        request.setAttribute("productsByCategory", productsByCategory);
        request.setAttribute("totalProducts", allProducts.size());

        // Forward to JSP
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

// ============ web.xml Configuration ============
/*
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
         http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <display-name>LightUp Shop</display-name>

    <welcome-file-list>
        <welcome-file>index.jsp</welcome-file>
    </welcome-file-list>

    <!-- Session timeout: 30 minutes -->
    <session-config>
        <session-timeout>30</session-timeout>
    </session-config>

    <!-- Error pages -->
    <error-page>
        <error-code>404</error-code>
        <location>/error-404.jsp</location>
    </error-page>

    <error-page>
        <error-code>500</error-code>
        <location>/error-500.jsp</location>
    </error-page>
</web-app>
*/
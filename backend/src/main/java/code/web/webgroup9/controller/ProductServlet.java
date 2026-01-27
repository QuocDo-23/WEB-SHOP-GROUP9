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

        try {
            /* ===================== 1. NHẬN FILTER GIÁ ===================== */
            // checkbox giá (vd: 0-1000000, 1000000-5000000 ...)
            String[] priceRanges = request.getParameterValues("price");

            /* ===================== 2. LẤY DANH MỤC (GIỮ NGUYÊN) ===================== */
            List<Category> categories = categoryService.getSubCategories();

            /* ===================== 3. NẾU CÓ LỌC GIÁ → CHẠY LỌC ===================== */
            if (priceRanges != null && priceRanges.length > 0) {
                List<ProductWithDetails> filteredProducts =
                        productService.filterProductsByPrice(priceRanges);

                // Debug: In ra console để kiểm tra (tùy chọn, xóa khi production)
                System.out.println("Filtering by price ranges: " + Arrays.toString(priceRanges) + ", Filtered products count: " + filteredProducts.size());

                request.setAttribute("products", filteredProducts);  // Dòng quan trọng: Set products cho JSP
                request.setAttribute("categories", categories);

                // Dùng lại products.jsp – KHÔNG đổi giao diện
                request.getRequestDispatcher("products.jsp").forward(request, response);
                return;
            }

            /* ===================== 4. KHÔNG LỌC → LOGIC CŨ ===================== */
            List<ProductWithDetails> allProducts =
                    productService.getAllProductsWithDetails();

            Map<Integer, List<ProductWithDetails>> productsByCategory = new HashMap<>();

            for (ProductWithDetails product : allProducts) {
                int catId = product.getCategoryId();
                List<ProductWithDetails> list =
                        productsByCategory.computeIfAbsent(catId, k -> new ArrayList<>());

                // Chỉ lấy 8 sp / danh mục (GIỮ NGUYÊN)
                if (list.size() < 8) {
                    list.add(product);
                }
            }

            request.setAttribute("categories", categories);
            request.setAttribute("productsByCategory", productsByCategory);
            request.setAttribute("totalProducts", allProducts.size());

            request.getRequestDispatcher("products.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
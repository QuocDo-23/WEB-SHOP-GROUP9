package code.web.webgroup9.ADMIN.controller;

import code.web.webgroup9.dao.CategoryDAO;
import code.web.webgroup9.dao.ProductDAO;
import code.web.webgroup9.model.Category;
import code.web.webgroup9.model.ProductWithDetails;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@WebServlet("/admin/products")
public class AdminProductServlet extends HttpServlet {
    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy tham số filter từ request
        String search = req.getParameter("search");
        String categoryIdStr = req.getParameter("categoryId");
        String status = req.getParameter("status");

        Integer categoryId = null;
        if (categoryIdStr != null && !categoryIdStr.trim().isEmpty()) {
            try {
                categoryId = Integer.parseInt(categoryIdStr);
            } catch (NumberFormatException e) {
                // Ignore
            }
        }

        // Lấy tham số phân trang
        int page = 1;
        try {
            String pageParam = req.getParameter("page");
            if (pageParam != null && !pageParam.trim().isEmpty()) {
                page = Integer.parseInt(pageParam);
            }
        } catch (NumberFormatException e) {
            page = 1;
        }

        int itemsPerPage = 10;
        int offset = (page - 1) * itemsPerPage;

        // Lấy danh sách sản phẩm (có filter)
        List<ProductWithDetails> products = productDAO.getProductsWithPagination(
                search, categoryId, status, offset, itemsPerPage
        );

        // Đếm tổng số sản phẩm (có filter)
        int totalFiltered = productDAO.countProductsWithFilter(search, categoryId, status);
        int totalPages = (int) Math.ceil((double) totalFiltered / itemsPerPage);

        // ĐẢM BẢO totalPages >= 1
        if (totalPages < 1) {
            totalPages = 1;
        }

        // Đảm bảo page hợp lệ
        if (page < 1) page = 1;
        if (page > totalPages) page = totalPages;

        // ===== TÍNH TOÁN STATS (Cache nếu cần) =====
        int totalCount = productDAO.getTotalProductCount();
        int activeCount = productDAO.getProductCountByStatus("active");
        int lowStockCount = productDAO.getLowStockProductCount(20);
        int outOfStockCount = productDAO.getOutOfStockProductCount();

        // thêm sản phẩm
        String success = req.getParameter("success");
        if ("add".equals(success)) {
            req.setAttribute("successMessage", "Thêm sản phẩm thành công!");
        }

        List<Category> categories = categoryDAO.getProductCategories();

        // Set attributes cho JSP
        req.setAttribute("products", products);
        req.setAttribute("currentPages", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalProducts", totalCount);
        req.setAttribute("activeProducts", activeCount);
        req.setAttribute("lowStockProducts", lowStockCount);
        req.setAttribute("outOfStockProducts", outOfStockCount);
        req.setAttribute("categories", categories);
        req.setAttribute("currentPage", "products");



        // Set lại filter parameters để giữ trạng thái
        req.setAttribute("searchParam", search != null ? search : "");
        req.setAttribute("categoryParam", categoryIdStr != null ? categoryIdStr : "");
        req.setAttribute("statusParam", status != null ? status : "");

        // Forward đến JSP
        req.getRequestDispatcher("/Admin/products.jsp").forward(req, resp);
    }
}
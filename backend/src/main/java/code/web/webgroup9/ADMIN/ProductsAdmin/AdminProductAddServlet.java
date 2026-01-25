package code.web.webgroup9.controller.admin;

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
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/admin/products/add")
public class AdminProductAddServlet extends HttpServlet {
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

        // Lấy danh sách categories để hiển thị trong form
        List<Category> categories = categoryDAO.getProductCategories();
        req.setAttribute("categories", categories);
        req.setAttribute("currentPage", "products");

        // Forward đến trang thêm sản phẩm
        req.getRequestDispatcher("/Admin/product_add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        try {
            // Lấy dữ liệu từ form
            ProductWithDetails product = new ProductWithDetails();

            // Thông tin cơ bản
            product.setName(req.getParameter("productName"));
            product.setCategoryId(Integer.parseInt(req.getParameter("categoryId")));
            product.setPrice(Double.parseDouble(req.getParameter("price")));
            product.setInventoryQuantity(Integer.parseInt(req.getParameter("stock")));

            // Review (có thể null)
            String reviewParam = req.getParameter("review");
            if (reviewParam != null && !reviewParam.trim().isEmpty()) {
                product.setReview(new BigDecimal(reviewParam));
            } else {
                product.setReview(BigDecimal.ZERO);
            }

            // Discount ID (có thể null)
            String discountParam = req.getParameter("discountId");
            if (discountParam != null && !discountParam.trim().isEmpty()) {
                product.setDiscountId(Integer.parseInt(discountParam));
            }

            // Status - mặc định là active
            String status = req.getParameter("status");
            product.setStatus(status != null ? status : "active");

            // Chi tiết sản phẩm
            product.setDescription(req.getParameter("description"));
            product.setMaterial(req.getParameter("material"));
            product.setVoltage(req.getParameter("voltage"));
            product.setDimensions(req.getParameter("dimension"));
            product.setType(req.getParameter("type"));
            product.setColor(req.getParameter("color"));
            product.setStyle(req.getParameter("style"));

            // Warranty
            String warrantyParam = req.getParameter("warranty");
            if (warrantyParam != null && !warrantyParam.trim().isEmpty()) {
                product.setWarranty(warrantyParam);
            }

            // Image
            product.setMainImage(req.getParameter("imageLink"));

            // Thêm sản phẩm vào database
            boolean success = productDAO.insertProduct(product);

            if (success) {
                // Thành công - redirect về trang danh sách với thông báo
                resp.sendRedirect(req.getContextPath() + "/admin/products?success=add");
            } else {
                // Thất bại - quay lại form với thông báo lỗi
                req.setAttribute("error", "Có lỗi xảy ra khi thêm sản phẩm");
                List<Category> categories = categoryDAO.getProductCategories();
                req.setAttribute("categories", categories);
                req.setAttribute("product", product); // Giữ lại dữ liệu đã nhập
                req.getRequestDispatcher("/Admin/product_add.jsp").forward(req, resp);
            }

        } catch (NumberFormatException e) {
            req.setAttribute("error", "Dữ liệu nhập không hợp lệ: " + e.getMessage());
            List<Category> categories = categoryDAO.getProductCategories();
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/Admin/product_add.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            List<Category> categories = categoryDAO.getProductCategories();
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/Admin/product_add.jsp").forward(req, resp);
        }
    }
}
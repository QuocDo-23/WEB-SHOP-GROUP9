package code.web.webgroup9.ADMIN.ProductsAdmin;

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
import java.util.Optional;

@WebServlet("/admin/products/edit")
public class AdminProductEditServlet extends HttpServlet {
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

        String idParam = req.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/admin/products");
            return;
        }

        try {
            int productId = Integer.parseInt(idParam);

            // Lấy thông tin sản phẩm
            Optional<ProductWithDetails> productOpt = productDAO.getProductById(productId);

            if (productOpt.isEmpty()) {
                req.setAttribute("error", "Không tìm thấy sản phẩm");
                resp.sendRedirect(req.getContextPath() + "/admin/products");
                return;
            }

            ProductWithDetails product = productOpt.get();

            // Lấy danh sách categories
            List<Category> categories = categoryDAO.getProductCategories();

            // Set attributes
            req.setAttribute("product", product);
            req.setAttribute("categories", categories);
            req.setAttribute("currentPage", "products");

            // Forward đến trang edit
            req.getRequestDispatcher("/Admin/product_edit.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/admin/products");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String idParam = req.getParameter("productId");

        if (idParam == null || idParam.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/admin/products");
            return;
        }

        try {
            int productId = Integer.parseInt(idParam);

            // Tạo object product với dữ liệu mới
            ProductWithDetails product = new ProductWithDetails();
            product.setId(productId);

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

            // Status
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
            String[] imageLinks = req.getParameterValues("imageLinks");
            if (imageLinks != null) {
                product.setImages(
                        java.util.Arrays.stream(imageLinks)
                                .filter(s -> s != null && !s.trim().isEmpty())
                                .toList()
                );
            }
            // Cập nhật sản phẩm trong database
            boolean success = productDAO.updateProduct(product);

            if (success) {
                // Thành công - redirect về trang danh sách với thông báo
                resp.sendRedirect(req.getContextPath() + "/admin/products?success=edit");
            } else {
                // Thất bại - quay lại form với thông báo lỗi
                req.setAttribute("error", "Có lỗi xảy ra khi cập nhật sản phẩm");
                List<Category> categories = categoryDAO.getProductCategories();
                req.setAttribute("categories", categories);
                req.setAttribute("product", product);
                req.getRequestDispatcher("/Admin/product_edit.jsp").forward(req, resp);
            }

        } catch (NumberFormatException e) {
            req.setAttribute("error", "Dữ liệu nhập không hợp lệ: " + e.getMessage());

            // Lấy lại thông tin để hiển thị form
            try {
                int productId = Integer.parseInt(idParam);
                Optional<ProductWithDetails> productOpt = productDAO.getProductById(productId);
                if (productOpt.isPresent()) {
                    req.setAttribute("product", productOpt.get());
                }
            } catch (Exception ex) {
                // Ignore
            }

            List<Category> categories = categoryDAO.getProductCategories();
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/Admin/product_edit.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());

            // Lấy lại thông tin để hiển thị form
            try {
                int productId = Integer.parseInt(idParam);
                Optional<ProductWithDetails> productOpt = productDAO.getProductById(productId);
                if (productOpt.isPresent()) {
                    req.setAttribute("product", productOpt.get());
                }
            } catch (Exception ex) {
                // Ignore
            }

            List<Category> categories = categoryDAO.getProductCategories();
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/Admin/product_edit.jsp").forward(req, resp);
        }
    }
}
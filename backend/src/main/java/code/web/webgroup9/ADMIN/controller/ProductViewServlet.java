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
import java.util.Optional;

@WebServlet("/admin/products/view")
public class ProductViewServlet extends HttpServlet {
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
            Optional<ProductWithDetails> productOpt = productDAO.getProductById(productId);

            if (productOpt.isPresent()) {
                req.setAttribute("product", productOpt.get());
                req.setAttribute("currentPage", "products");

                req.getRequestDispatcher("/Admin/product_detail.jsp").forward(req, resp);
            } else {
                req.setAttribute("errorMessage", "Không tìm thấy sản phẩm với ID: " + productId);
                resp.sendRedirect(req.getContextPath() + "/Admin/products");
            }

        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/Admin/products");
        }
    }
}
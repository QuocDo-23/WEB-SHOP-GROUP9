package code.web.webgroup9.controller;

import code.web.webgroup9.service.ImageService;
import code.web.webgroup9.service.ProductService;
import code.web.webgroup9.dao.ReviewDAO;
import code.web.webgroup9.dao.ImageDAO;
import code.web.webgroup9.model.*;

import code.web.webgroup9.service.ReviewService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;
import java.util.Locale;

@WebServlet("/product-detail")
public class ProductDetailServlet extends HttpServlet {

    private ProductService productService;
    private ReviewService reviewService;
    private ImageService imageService;


    @Override
    public void init() {
        productService = new ProductService();
        reviewService = new ReviewService();
        imageService = new ImageService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null) {
            response.sendRedirect("products");
            return;
        }

        int productId;
        try {
            productId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("products");
            return;
        }

        Optional<ProductWithDetails> productOpt = productService.getProductById(productId);

        if (productOpt.isEmpty()) {
            response.sendRedirect("products");
            return;
        }

        ProductWithDetails product = productOpt.get();

        // Images
        List<Image> images = imageService.getImagesByProductId(productId);

        // Reviews
        List<Review> reviews = reviewService.getReviewsByProductId(productId);
        ReviewStatistics stats = reviewService.getReviewStatistics(productId);

        // Related products
        List<ProductWithDetails> relatedProducts =
                productService.getProductsByCategory(product.getCategoryId());

        relatedProducts.removeIf(p -> p.getId() == productId);

        if (relatedProducts.size() > 4) {
            relatedProducts = relatedProducts.subList(0, 4);
        }

        // Format VND
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        // Set attributes
        request.setAttribute("product", product);
        request.setAttribute("images", images);
        request.setAttribute("reviews", reviews);
        request.setAttribute("stats", stats);
        request.setAttribute("relatedProducts", relatedProducts);
        request.setAttribute("vndFormat", vndFormat);

        request.getRequestDispatcher("product-detail.jsp").forward(request, response);
    }
}
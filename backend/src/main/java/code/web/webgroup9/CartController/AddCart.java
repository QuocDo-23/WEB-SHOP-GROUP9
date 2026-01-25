package code.web.webgroup9.CartController;

import code.web.webgroup9.model.Cart;
import code.web.webgroup9.model.ProductWithDetails;
import code.web.webgroup9.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "AddCart", value = "/add-cart")
public class AddCart extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pID = Integer.parseInt(request.getParameter("pID"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        HttpSession session = request.getSession();

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }

        ProductService productService = new ProductService();
        Optional<ProductWithDetails> product = productService.getProductById(pID);

        if (product.isPresent()) {
            cart.addItem(product.get(), quantity);
            session.setAttribute("cart", cart);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            request.setAttribute("msg", "Product not found");
            request.getRequestDispatcher("products.jsp").forward(request, response);
        }
    }
}

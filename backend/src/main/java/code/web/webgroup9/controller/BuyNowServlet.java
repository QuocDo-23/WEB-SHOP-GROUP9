package code.web.webgroup9.controller;

import jakarta.servlet.annotation.WebServlet;
import code.web.webgroup9.dao.*;
import code.web.webgroup9.model.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Optional;


@WebServlet("/buy-now")
public class BuyNowServlet extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();

        int pID = Integer.parseInt(request.getParameter("pID"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        Optional<ProductWithDetails> product = productDAO.getProductById(pID);
        if (product.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        Cart buyNowCart = new Cart();
        buyNowCart.addItem(product.get(), quantity);

        session.setAttribute("cart", buyNowCart);
        session.setAttribute("checkoutType", "buy-now");

        response.sendRedirect(request.getContextPath() + "/payment");
    }
}

package code.web.webgroup9.CartController;

import code.web.webgroup9.model.Cart;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "UpdateCart", value = "/update")
public class UpdateCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart != null) {
            try {
                int productId = Integer.parseInt(request.getParameter("productId"));
                int qty = Integer.parseInt(request.getParameter("qty"));

                if (qty <= 0) {
                    cart.removeItem(productId);
                } else {
                    cart.updateItem(productId, qty);
                }
            } catch (Exception e) {

            }
        }

        String from = request.getParameter("from");

        if ("mini".equals(from)) {
            response.sendRedirect(request.getHeader("Referer"));
        } else {
            response.sendRedirect(request.getContextPath() + "/cart");
        }

    }
}
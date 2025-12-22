package code.web.webgroup9.CartController;

import code.web.webgroup9.model.Cart;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RemoveCart", value = "/remove")
public class RemoveCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart != null) {
            try {
                int productId = Integer.parseInt(request.getParameter("productId"));
                cart.removeItem(productId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath() + "/cart");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
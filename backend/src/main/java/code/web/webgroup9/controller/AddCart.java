package code.web.webgroup9.controller;

import code.web.webgroup9.model.Cart;
import code.web.webgroup9.model.Product;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "AddCart", value = "/add-cart")
public class AddCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pID = Integer.parseInt(request.getParameter("pID"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        HttpSession session = request.getSession();

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }

//        ProductService productService = new ProductService();
//        Product product = productService.getProduct(pID);
//        if(product != null){
//            cart.addItem(product, quantity);
//            session.setAttribute("cart", cart);
//            response.sendRedirect("products");
//            return;
//        }
//        request.setAttribute("msg", "Product not found");
//        request.getRequestDispatcher("products.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
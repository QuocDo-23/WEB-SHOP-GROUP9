package code.web.webgroup9.CartController;

import code.web.webgroup9.model.Cart;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "RemoveCart", value = "/remove")
public class RemoveCart extends HttpServlet {

    private void processRemove(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart != null) {
            try {
                int productId = Integer.parseInt(request.getParameter("productId"));
                cart.removeItem(productId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // ⭐ QUAN TRỌNG: KHÔNG redirect sang /cart
        // chỉ trả OK để fetch xử lý
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRemove(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRemove(request, response);
    }
}

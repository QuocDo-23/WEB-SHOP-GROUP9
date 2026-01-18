package code.web.webgroup9.filter;

import code.web.webgroup9.model.User;
import code.web.webgroup9.service.CategoryService;
import code.web.webgroup9.service.OrderService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class HeaderFilter implements Filter {

    private CategoryService categoryService;
    private OrderService orderDAO;


    @Override
    public void init(FilterConfig filterConfig) {
        categoryService = new CategoryService();
        orderDAO = new OrderService();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                // Đếm đơn hàng chưa giao
                int pendingOrderCount = orderDAO.countOrdersByUserId(user.getId());
                request.setAttribute("orderCount", pendingOrderCount);
            }
        }
            req.setAttribute("categories", categoryService.getSubCategories());
        chain.doFilter(request, response);
    }
}

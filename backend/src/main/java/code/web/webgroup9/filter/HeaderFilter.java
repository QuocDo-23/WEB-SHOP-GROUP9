package code.web.webgroup9.filter;

import code.web.webgroup9.service.CategoryService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class HeaderFilter implements Filter {

    private CategoryService categoryService;

    @Override
    public void init(FilterConfig filterConfig) {
        categoryService = new CategoryService();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

            req.setAttribute("categories", categoryService.getSubCategories());
        chain.doFilter(request, response);
    }
}

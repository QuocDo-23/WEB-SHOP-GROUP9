package code.web.webgroup9.ADMIN.Order;

import code.web.webgroup9.dao.OrderDAO;
import code.web.webgroup9.model.Order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "OrderListServlet", urlPatterns = {"/admin/orders"})
public class OrderListServlet extends HttpServlet {

    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Kiểm tra quyền admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        listOrders(request, response);
    }

    /**
     * Hiển thị danh sách đơn hàng với phân trang và lọc
     */
    private void listOrders(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy tham số phân trang
        int page = 1;
        int pageSize = 10;

        String pageParam = request.getParameter("page");
        String pageSizeParam = request.getParameter("pageSize");

        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1;
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        if (pageSizeParam != null) {
            try {
                pageSize = Integer.parseInt(pageSizeParam);
                if (pageSize < 5) pageSize = 5;
                if (pageSize > 100) pageSize = 100;
            } catch (NumberFormatException e) {
                pageSize = 10;
            }
        }

        // Lấy tham số lọc và tìm kiếm
        String status = request.getParameter("status");
        String searchKeyword = request.getParameter("search");
        String sortBy = request.getParameter("sortBy");
        String sortOrder = request.getParameter("sortOrder");

        if (status == null) status = "all";
        if (sortBy == null) sortBy = "order_date";
        if (sortOrder == null) sortOrder = "DESC";

        // Lấy dữ liệu từ DAO (phân trang tại DAO)
        List<Order> orders = orderDAO.getOrdersWithPagination(
                page, pageSize, status, searchKeyword, sortBy, sortOrder
        );

        // Đếm tổng số bản ghi
        int totalRecords = orderDAO.countOrders(status, searchKeyword);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        // Lấy thống kê trạng thái
        Map<String, Integer> statusStats = orderDAO.getOrderStatusStatistics();

        // Set attributes
        request.setAttribute("orders", orders);
        request.setAttribute("currentPages", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalRecords", totalRecords);
        request.setAttribute("status", status);
        request.setAttribute("searchKeyword", searchKeyword);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortOrder", sortOrder);
        request.setAttribute("statusStats", statusStats);
        request.setAttribute("currentPage", "orders");


        // Forward to JSP
        request.getRequestDispatcher("/Admin/orders-admin.jsp").forward(request, response);
    }
}

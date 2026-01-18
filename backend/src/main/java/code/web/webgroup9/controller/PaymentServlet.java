package code.web.webgroup9.controller;

import code.web.webgroup9.dao.*;
import code.web.webgroup9.model.*;
import code.web.webgroup9.service.OrderService;
import code.web.webgroup9.service.PaymentService;
import code.web.webgroup9.service.addressService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {

    private OrderService orderService;
    private PaymentService paymentService;
    private addressService addressService;
    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        orderService = new OrderService();
        paymentService = new PaymentService();
        addressService = new addressService();
        productDAO = new ProductDAO();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null || cart.getTotalItems() == 0) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        // Lấy thông tin user nếu đã đăng nhập
        User user = (User) session.getAttribute("user");
        if (user != null) {
            // Lấy danh sách địa chỉ đã lưu của user
            List<Address> savedAddresses = addressService.getAddressByUserId(user.getId());
            request.setAttribute("savedAddresses", savedAddresses);

            // Xử lý chọn địa chỉ
            String selectedAddressId = request.getParameter("selectedAddressId");

            if (selectedAddressId != null && !selectedAddressId.equals("new")) {
                // Tìm địa chỉ đã chọn
                try {
                    int addressId = Integer.parseInt(selectedAddressId);
                    Address selectedAddress = null;

                    for (Address addr : savedAddresses) {
                        if (addr.getId() == addressId) {
                            selectedAddress = addr;
                            break;
                        }
                    }

                    if (selectedAddress != null) {
                        request.setAttribute("selectedAddress", selectedAddress);
                    }
                } catch (NumberFormatException e) {
                    // Invalid addressId, ignore
                }
            } else if (selectedAddressId != null) {
                request.setAttribute("isNewAddress", true);
            } else {
                for (Address addr : savedAddresses) {
                    if (addr.isDefault()) {
                        request.setAttribute("selectedAddress", addr);
                        break;
                    }
                }
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login?redirect=payment");
            return;
        }

        request.getRequestDispatcher("/payment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        try {
            // Lấy cart từ session
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null || cart.getTotalItems() == 0) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            User user = (User) session.getAttribute("user");
            Integer userId = user != null ? user.getId() : null;

            // Lấy thông tin từ form
            String checkoutType = request.getParameter("checkoutType");
            String recipientName = request.getParameter("recipientName");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String houseNumber = request.getParameter("houseNumber");
            String commune = request.getParameter("commune");
            String district = request.getParameter("district");
            String addressDetail = request.getParameter("addressDetail");
            if (addressDetail != null && addressDetail.trim().isEmpty()) {
                addressDetail = null;
            }


            String shippingMethod = request.getParameter("shippingMethod");
            String paymentMethod = request.getParameter("paymentMethod");

            boolean saveAddress = "true".equals(request.getParameter("saveAddress"));

            // Tính phí ship
            double shippingFee = "express".equals(shippingMethod) ? 30000 : 0;
            double totalAmount = cart.getTotalPrice() + shippingFee;

            // Tạo đơn hàng mới
            Order order = new Order();
            order.setUserId(userId);
            order.setRecipientName(recipientName);
            order.setRecipientPhone(phone);
            order.setRecipientEmail(email);
            order.setShippingHouseNumber(houseNumber);
            order.setShippingCommune(commune);
            order.setShippingDistrict(district);
            order.setShippingAddressDetail(addressDetail);
            order.setOrderDate(LocalDateTime.now());
            order.setTotal(totalAmount);
            order.setStatus("pending");

            // Lưu order vào database
            int orderId = orderService.insertOrder(order);

            if (orderId > 0) {
                // Lưu các order items
                for (CartItem item : cart.getListItem()) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderId(orderId);
                    orderItem.setProductId(item.getProduct().getId());
                    orderItem.setProductName(item.getProduct().getName());
                    orderItem.setProductMaterial(item.getProduct().getMaterial());
                    orderItem.setPrice(item.getProduct().getDiscountedPrice());
                    orderItem.setImg(item.getProduct().getMainImage());
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setSubtotal(item.getQuantity() * item.getProduct().getDiscountedPrice());
                    orderService.insertOrderItem(orderItem);

                    // Giảm số lượng sản phẩm
                    productDAO.decreaseProductQuantity(item.getProduct().getId(), item.getQuantity());

                }

                // Tạo payment record
                Payment payment = new Payment();
                payment.setOrderId(orderId);
                payment.setPaymentMethod(paymentMethod);
                payment.setAmount(totalAmount);
                payment.setStatus("pending");
                payment.setPayDate(LocalDate.now());

                paymentService.insertPayment(payment);

                // Lưu địa chỉ nếu user chọn
                if (user != null && saveAddress) {
                    Address newAddress = new Address(
                            phone, userId, recipientName,
                            houseNumber, commune, district, addressDetail
                    );
                    newAddress.setEmail(email);
                    addressService.insertAddress(newAddress);
                }

                // Xóa cart sau khi đặt hàng thành công
                cart.removeAll();
                session.setAttribute("cart", cart);
                session.removeAttribute("checkoutType");


                // Chuyển đến trang xác nhận đơn hàng
                session.setAttribute("orderId", orderId);
                response.sendRedirect(request.getContextPath() + "/order-success");

            } else {
                // Lỗi khi tạo đơn hàng
                request.setAttribute("error", "Có lỗi xảy ra khi tạo đơn hàng. Vui lòng thử lại.");
                request.getRequestDispatcher("/payment.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            request.getRequestDispatcher("/payment.jsp").forward(request, response);
        }
    }
}
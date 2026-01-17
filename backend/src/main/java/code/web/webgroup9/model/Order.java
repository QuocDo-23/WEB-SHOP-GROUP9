package code.web.webgroup9.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Order implements Serializable {
    private int id;
    private Integer userId;

    private String recipientName;
    private String recipientPhone;
    private String recipientEmail;

    private String shippingHouseNumber;
    private String shippingCommune;
    private String shippingDistrict;
    private String shippingAddressDetail;


    private LocalDate orderDate;

    private double total;
    private String status;

    public Order() {
        this.status = "pending";
    }

    public int getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public String getShippingHouseNumber() {
        return shippingHouseNumber;
    }

    public String getShippingCommune() {
        return shippingCommune;
    }

    public String getShippingDistrict() {
        return shippingDistrict;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }
    public String getShippingAddressDetail(){
        return shippingAddressDetail;
    }
    public void setShippingAddressDetail(String shippingAddressDetail){
        this.shippingAddressDetail = shippingAddressDetail;
    }



    public double getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public void setShippingHouseNumber(String shippingHouseNumber) {
        this.shippingHouseNumber = shippingHouseNumber;
    }

    public void setShippingCommune(String shippingCommune) {
        this.shippingCommune = shippingCommune;
    }

    public void setShippingDistrict(String shippingDistrict) {
        this.shippingDistrict = shippingDistrict;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }


    public void setTotal(double total) {
        this.total = total;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getOrderDateFormatted() {
        if (orderDate == null) return "";
        return orderDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

}

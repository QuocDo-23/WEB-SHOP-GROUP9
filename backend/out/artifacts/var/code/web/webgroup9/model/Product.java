package code.web.webgroup9.model;

import java.math.BigDecimal;

public class Product {
    private int id;
    private int categoryId;
    private String name;
    private Integer discountId;
    private BigDecimal price;
    private int inventoryQuantity;
    private BigDecimal review;
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public Integer getDiscountId() { return discountId; }
    public void setDiscountId(Integer discountId) { this.discountId = discountId; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public int getInventoryQuantity() { return inventoryQuantity; }
    public void setInventoryQuantity(int inventoryQuantity) { this.inventoryQuantity = inventoryQuantity; }

    public BigDecimal getReview() { return review; }
    public void setReview(BigDecimal review) { this.review = review; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}


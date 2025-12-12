package code.web.webgroup9.model;

import java.math.BigDecimal;

public class ProductWithDetails extends Product {
    private String categoryName;
    private BigDecimal discountRate;
    private String description;
    private String warranty;
    private String material;
    private String voltage;
    private String dimensions;
    private String type;
    private String color;
    private String style;
    private String mainImage;
    private String hoverImage;

    // Calculated fields
    public BigDecimal getDiscountedPrice() {
        if (discountRate != null && discountRate.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal discount = getPrice().multiply(discountRate).divide(new BigDecimal(100));
            return getPrice().subtract(discount);
        }
        return getPrice();
    }

    public boolean hasDiscount() {
        return discountRate != null && discountRate.compareTo(BigDecimal.ZERO) > 0;
    }

    // Getters and Setters
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public BigDecimal getDiscountRate() { return discountRate; }
    public void setDiscountRate(BigDecimal discountRate) { this.discountRate = discountRate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getWarranty() { return warranty; }
    public void setWarranty(String warranty) { this.warranty = warranty; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    public String getVoltage() { return voltage; }
    public void setVoltage(String voltage) { this.voltage = voltage; }

    public String getDimensions() { return dimensions; }
    public void setDimensions(String dimensions) { this.dimensions = dimensions; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getStyle() { return style; }
    public void setStyle(String style) { this.style = style; }

    public String getMainImage() { return mainImage; }
    public void setMainImage(String mainImage) { this.mainImage = mainImage; }

    public String getHoverImage() { return hoverImage; }
    public void setHoverImage(String hoverImage) { this.hoverImage = hoverImage; }
}

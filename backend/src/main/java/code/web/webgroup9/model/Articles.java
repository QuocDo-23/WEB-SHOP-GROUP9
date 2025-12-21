package code.web.webgroup9.model;

import java.sql.Timestamp;

public class Articles {
    private int id;
    private Integer categoryId;
    private String title;
    private String description;
    private Timestamp dateOfPosting;
    private String slug;
    private String mainImg;
    private boolean feature;
    private String categoryName; // For JOIN query

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDateOfPosting() {
        return dateOfPosting;
    }

    public void setDateOfPosting(Timestamp dateOfPosting) {
        this.dateOfPosting = dateOfPosting;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public boolean isFeature() {
        return feature;
    }

    public void setFeature(boolean feature) {
        this.feature = feature;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
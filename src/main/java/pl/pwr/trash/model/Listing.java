package pl.pwr.trash.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Listing {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String photo;
    private Integer userId;
    private ListingStatus statusLis;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Listing(Long id, String title, String description, BigDecimal price, String photo, Integer userId, ListingStatus statusLis, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.photo = photo;
        this.userId = userId;
        this.statusLis = statusLis;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public ListingStatus getStatusLis() {
        return statusLis;
    }

    public void setStatusLis(ListingStatus statusLis) {
        this.statusLis = statusLis;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

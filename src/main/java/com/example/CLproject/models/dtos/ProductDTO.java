package com.example.CLproject.models.dtos;

public class ProductDTO {
    private String name;
    private int productId;
    private String description;
    private Double price;
    private String image;

    public ProductDTO() {
    }

    public ProductDTO(String name, int producerId, String description, Double price, String image) {
        this.name = name;
        this.productId = producerId;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProducerId() {
        return productId;
    }

    public void setProducerId(int producerId) {
        this.productId = producerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "productDTO{" +
                "name='" + name + '\'' +
                ", producerId=" + productId +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}

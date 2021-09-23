package com.theekshana.onlineshop.Model;

public class ProductModel {
    String productId;
    String productTitle;
    String productDescription;
    String productMaincategory;
    String productsubCategory;
    String productQty;
    String originalPrice;
    String productSize;
    String ProductImage;
    String discountPrice;
    String DiscountNote;
    String discountAvailble;
    String timestamp;

    public ProductModel() {
    }

    public ProductModel(String productId, String productTitle, String productDescription, String productMaincategory, String productsubCategory, String productQty, String originalPrice, String productSize, String productImage, String discountPrice, String discountNote, String discountAvailble, String timestamp) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.productDescription = productDescription;
        this.productMaincategory = productMaincategory;
        this.productsubCategory = productsubCategory;
        this.productQty = productQty;
        this.originalPrice = originalPrice;
        this.productSize = productSize;
        ProductImage = productImage;
        this.discountPrice = discountPrice;
        DiscountNote = discountNote;
        this.discountAvailble = discountAvailble;
        this.timestamp = timestamp;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductMaincategory() {
        return productMaincategory;
    }

    public void setProductMaincategory(String productMaincategory) {
        this.productMaincategory = productMaincategory;
    }

    public String getProductsubCategory() {
        return productsubCategory;
    }

    public void setProductsubCategory(String productsubCategory) {
        this.productsubCategory = productsubCategory;
    }

    public String getProductQty() {
        return productQty;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDiscountNote() {
        return DiscountNote;
    }

    public void setDiscountNote(String discountNote) {
        DiscountNote = discountNote;
    }

    public String getDiscountAvailble() {
        return discountAvailble;
    }

    public void setDiscountAvailble(String discountAvailble) {
        this.discountAvailble = discountAvailble;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
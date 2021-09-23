package com.theekshana.onlineshop.Model;

public class subCateModel {

    String productId;
    String productTitle;
    String productImage;

    public subCateModel(String productId, String productTitle, String productImage) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.productImage = productImage;
    }

    public subCateModel() {
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

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}

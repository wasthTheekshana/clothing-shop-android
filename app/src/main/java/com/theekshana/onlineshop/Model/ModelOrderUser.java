package com.theekshana.onlineshop.Model;

public class ModelOrderUser {

    String orderId;
    String orderTime;
    String orderCost;
    String orderStatus;
    String deliveryStatus;
    String oderBy;
    String latitude;
    String longitude;

    public ModelOrderUser() {
    }

    public ModelOrderUser(String orderId, String orderTime, String orderCost, String orderStatus, String deliveryStatus, String oderBy, String latitude, String longitude) {
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.orderCost = orderCost;
        this.orderStatus = orderStatus;
        this.deliveryStatus = deliveryStatus;
        this.oderBy = oderBy;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(String orderCost) {
        this.orderCost = orderCost;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getOderBy() {
        return oderBy;
    }

    public void setOderBy(String oderBy) {
        this.oderBy = oderBy;
    }
}

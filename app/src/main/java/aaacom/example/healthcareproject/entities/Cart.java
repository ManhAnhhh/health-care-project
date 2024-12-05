package aaacom.example.healthcareproject.entities;

public class Cart {
    private int id;
    private String customerName;
    private String productName;
    private float price;
    private String orderDate;
    private String orderPlace;
    private int quantity;
    private int medicine_id;  // New field to store the medicin_id

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderPlace() {
        return orderPlace;
    }

    public void setOrderPlace(String orderPlace) {
        this.orderPlace = orderPlace;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Assuming this is part of your Order or Cart class


    // Setter method to set the medicin_id
    public void setMedicine_id(int medicinId) {
        this.medicine_id = medicinId;  // Assign the passed medicinId to the medicin_id field
    }

    // Getter method to get the medicin_id
    public int getMedicine_id() {
        return medicine_id;  // Return the current value of medicin_id
    }
}
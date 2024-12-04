package aaacom.example.healthcareproject.entities;

import java.util.Date;
import java.util.List;

public class Order {
    int id;
    float total_amount;
    String customer_name;
    String order_date;
    String order_place;
    int medicin_id;
    String medicin_name;
    int quantity;
    int status;

    public Order(int quantity, int medicin_id, String order_place, String order_date, String customer_name, float total_amount) {
        this.quantity = quantity;
        this.medicin_id = medicin_id;
        this.order_place = order_place;
        this.order_date = order_date;
        this.customer_name = customer_name;
        this.total_amount = total_amount;
    }

    public Order(int quantity, int medicin_id, String order_place, String order_date, String customer_name, float total_amount, int id) {
        this.quantity = quantity;
        this.medicin_id = medicin_id;
        this.order_place = order_place;
        this.order_date = order_date;
        this.customer_name = customer_name;
        this.total_amount = total_amount;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(float total_amount) {
        this.total_amount = total_amount;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_place() {
        return order_place;
    }

    public void setOrder_place(String order_place) {
        this.order_place = order_place;
    }

    public int getMedicin_id() {
        return medicin_id;
    }

    public void setMedicin_id(int medicin_id) {
        this.medicin_id = medicin_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMedicin_name() {
        return medicin_name;
    }

    public void setMedicin_name(String medicin_name) {
        this.medicin_name = medicin_name;
    }
}

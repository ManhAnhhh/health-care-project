package aaacom.example.healthcareproject.entities;

public class Order {
    int id;
    float total_amount;
    String customer_name;
    String order_date;
    String order_place;
    int medicine_id;
    String medicine_name;
    int quantity;
    int status;

    public Order(int quantity, int medicin_id, String order_place, String order_date, String customer_name, float total_amount) {
        this.quantity = quantity;
        this.medicine_id = medicin_id;
        this.order_place = order_place;
        this.order_date = order_date;
        this.customer_name = customer_name;
        this.total_amount = total_amount;
    }

    public Order(int quantity, int medicin_id, String medicine_name, String order_place, String order_date, String customer_name, float total_amount, int id) {
        this.quantity = quantity;
        this.medicine_id = medicin_id;
        this.medicine_name = medicine_name;
        this.order_place = order_place;
        this.order_date = order_date;
        this.customer_name = customer_name;
        this.total_amount = total_amount;
        this.id = id;
    }

    public Order() {

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
        return medicine_id;
    }

    public void setMedicin_id(int medicin_id) {
        this.medicine_id = medicin_id;
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

    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }
}

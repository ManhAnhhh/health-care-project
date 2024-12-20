package aaacom.example.healthcareproject.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.text.NumberFormat;
import java.util.Locale;

public class Medicine {
    int id;
    String name;
    float price;
    String description;
    String category;
    String cong_dung;

    public Medicine(int id, String name, float price, String description, String category, String cong_dung) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.cong_dung = cong_dung;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        // Thiết lập định dạng cho Việt Nam
        Locale vietnam = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(vietnam);

        // Định dạng số tiền
        String formattedAmount = currencyFormatter.format(price);

        return id + " - " + name + " - " + formattedAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCong_dung() {
        return cong_dung;
    }

    public void setCong_dung(String cong_dung) {
        this.cong_dung = cong_dung;
    }
}

package aaacom.example.healthcareproject.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.text.NumberFormat;
import java.util.Locale;

public class Medicin implements Parcelable {
    int id;
    String name;
    float price;

    public Medicin(int id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    protected Medicin(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readFloat();
    }

    public static final Creator<Medicin> CREATOR = new Creator<Medicin>() {
        @Override
        public Medicin createFromParcel(Parcel in) {
            return new Medicin(in);
        }

        @Override
        public Medicin[] newArray(int size) {
            return new Medicin[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeFloat(price);
    }
}

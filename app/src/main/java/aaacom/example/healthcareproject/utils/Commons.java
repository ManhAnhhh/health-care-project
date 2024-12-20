package aaacom.example.healthcareproject.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class Commons {
    public static String FormatDecimalCommon(Float price) {
        Locale vietnam = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(vietnam);

        String formattedAmount = currencyFormatter.format(price);

        return formattedAmount;
    }
}

package aaacom.example.healthcareproject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import aaacom.example.healthcareproject.entities.Order;


public class OrderAdapter extends ArrayAdapter<Order> {
    Activity context;
    int LayoutId;
    ArrayList<Order> List = new ArrayList<Order>();

    public OrderAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<Order> object) {
        super(context, resource, object);
        this.context = context;
        this.LayoutId = resource;
        this.List = (ArrayList<Order>) object;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(LayoutId, null);

        if (List.size() > 0 && position >= 0) {
            final TextView txtv_CustomerName = convertView.findViewById(R.id.txtv_CustomerName);
            final TextView txtv_LoaiThuoc = convertView.findViewById(R.id.txtv_LoaiThuoc);
            final TextView txtv_SoLuong = convertView.findViewById(R.id.txtv_SoLuong);
            final TextView txtv_NgayDat = convertView.findViewById(R.id.txtv_NgayDat);
            final TextView txtv_ThanhTien = convertView.findViewById(R.id.txtv_ThanhTien);

            final View view = convertView;
            final Order order = List.get(position);

            txtv_CustomerName.setText(order.getCustomer_name().toString());
            txtv_LoaiThuoc.setText("Thuốc: " + order.getMedicin_id());
            txtv_SoLuong.setText("x" + order.getQuantity());
            txtv_NgayDat.setText("Ngày đặt: " +order.getOrder_date().toString());

            // Thiết lập định dạng cho Việt Nam
            Locale vietnam = new Locale("vi", "VN");
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(vietnam);

            // Định dạng số tiền
            String formattedAmount = currencyFormatter.format(order.getTotal_amount());

            txtv_ThanhTien.setText("Thành tiền: " + formattedAmount);
        }

        return convertView;
    }
}

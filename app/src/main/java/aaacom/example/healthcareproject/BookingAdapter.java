package aaacom.example.healthcareproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import aaacom.example.healthcareproject.entities.Booking;

public class BookingAdapter extends ArrayAdapter<Booking> {
    Activity context;
    int LayoutId;
    ArrayList<Booking> List = new ArrayList<Booking>();

    // Đảm bảo đường dẫn tới cơ sở dữ liệu của bạn
    private static final String DATABASE_PATH = "/data/data/aaacom.example.healthcareproject/databases/HealthcareDB.db";

    public BookingAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<Booking> object) {
        super(context, resource, object);
        this.context = context;
        this.LayoutId = resource;
        this.List = (ArrayList<Booking>) object;
    }

    @SuppressLint("Range")
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(LayoutId, null);

        if (List.size() > 0 && position >= 0) {
            final TextView txtBacsi = convertView.findViewById(R.id.txtBacsi);
            final TextView txtDiachi = convertView.findViewById(R.id.txtDiachi);
            final TextView txtSdt = convertView.findViewById(R.id.txtSdt);
            final TextView txtNgayhen = convertView.findViewById(R.id.txtNgayhẹn);
            final TextView txtGiohen = convertView.findViewById(R.id.txtGiohen);
            final TextView txtFee = convertView.findViewById(R.id.txtThanhtien);  // TextView để hiển thị phí

            final Booking booking = List.get(position);

            // Mở cơ sở dữ liệu trực tiếp
            SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);

            // Câu truy vấn để lấy tên bác sĩ, địa chỉ, số điện thoại và phí
            String query = "SELECT name, hospital_address, contact, fee FROM Doctor WHERE Id = ?";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(booking.getDoctorId())});

            String doctorName = "Unknown";
            String doctorAddress = "Unknown";
            String doctorPhone = "Unknown";
            String doctorFee = "Unknown";

            if (cursor != null && cursor.moveToFirst()) {
                doctorName = cursor.getString(cursor.getColumnIndex("name"));
                doctorAddress = cursor.getString(cursor.getColumnIndex("hospital_address"));
                doctorPhone = cursor.getString(cursor.getColumnIndex("contact"));
                doctorFee = cursor.getString(cursor.getColumnIndex("fee"));
                cursor.close();
            }

            // Hiển thị thông tin bác sĩ
            txtBacsi.setText("Bác sĩ: " + doctorName);
            txtDiachi.setText("Địa chỉ: " + doctorAddress);
            txtSdt.setText("SĐT: " + doctorPhone);
            txtFee.setText("Thành tiền: " + doctorFee + " VND");

            // Hiển thị thông tin booking
            txtNgayhen.setText("Ngày hẹn: " + booking.getDate());
            txtGiohen.setText("Giờ hẹn: " + booking.getTime());
        }

        return convertView;
    }
}

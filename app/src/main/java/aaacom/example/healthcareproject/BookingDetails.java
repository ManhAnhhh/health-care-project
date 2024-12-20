package aaacom.example.healthcareproject;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

import aaacom.example.healthcareproject.dao.BookingDao;
import aaacom.example.healthcareproject.entities.Booking;
import aaacom.example.healthcareproject.utils.MenuEventUtil;

public class BookingDetails extends AppCompatActivity {
    Button btnBack;
    ListView lvLichkham;
    BookingAdapter adapter;
    ArrayList<Booking> bookingList = new ArrayList<>();
    MaterialToolbar toolbar;
    BookingDao bookingDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        // Setup Toolbar
        toolbar = findViewById(R.id.tbr_bookingDetail);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Khởi tạo BookingDao để truy cập cơ sở dữ liệu
        bookingDao = new BookingDao(this);

        // Khởi tạo ListView
        lvLichkham = findViewById(R.id.lvLichkham); // Đảm bảo ID là đúng

        // Tạo adapter và gán vào ListView
        adapter = new BookingAdapter(this, R.layout.book_item_list_view, bookingList);
        lvLichkham.setAdapter(adapter);

        // Lấy danh sách các booking từ BookingDao
        bookingList.clear();
        bookingList.addAll(bookingDao.getBookings(true)); // false nếu không cần tên bác sĩ
        adapter.notifyDataSetChanged(); // Cập nhật adapter sau khi có dữ liệu mới

        // Xử lý sự kiện với cửa sổ hiển thị hệ thống (nếu cần)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return MenuEventUtil.handleMenuEvent(item, this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Xử lý khi nhấn nút quay lại
        onBackPressed(); // Quay lại màn hình trước
        return true;
    }
}

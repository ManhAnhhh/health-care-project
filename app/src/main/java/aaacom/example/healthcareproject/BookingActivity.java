package aaacom.example.healthcareproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

import aaacom.example.healthcareproject.dao.BookingDao;
import aaacom.example.healthcareproject.entities.Booking;

public class BookingActivity extends AppCompatActivity {
    EditText txtHoten, txtDiachi, txtSdt, txtPhi;
    TextView txtNgay, txtGio;
    Button btnBook, btnBack, btnNgay, btnGio;

    //sử dụng để nhận thông tin ngày, giờ mà người dùng đã chọn.
    DatePickerDialog.OnDateSetListener myDate;
    TimePickerDialog.OnTimeSetListener myTime;
    int myYear;
    int myMonth;
    int myDay;
    int myHour;
    int myMinute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        getWidget();

        txtHoten.setKeyListener(null);
        txtDiachi.setKeyListener(null);
        txtSdt.setKeyListener(null);
        txtPhi.setKeyListener(null);
        txtNgay.setText(getDate());
        txtGio.setText(getHour());

        /*Intent it = getIntent();
        String hoten = it.getStringExtra("hoten");
        String diachi = it.getStringExtra("diachi");
        String sdt = it.getStringExtra("sdt");
        String phi = it.getStringExtra("phi");

        txtHoten.setText(hoten);
        txtDiachi.setText(diachi);
        txtSdt.setText(sdt);
        txtPhi.setText("Phí: " + phi);*/

        //Xử lý btnNgay
        btnNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        getandsetDate();

        //Xử lý btnGio
        btnGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });
        getandsetTime();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy dữ liệu từ các trường nhập liệu
                String hoten = txtHoten.getText().toString().trim();
                String diachi = txtDiachi.getText().toString().trim();
                String sdt = txtSdt.getText().toString().trim();
                String phi = txtPhi.getText().toString().trim();
                String ngay = txtNgay.getText().toString().trim();
                String gio = txtGio.getText().toString().trim();

                // Kiểm tra dữ liệu đầu vào
                if (hoten.isEmpty() || diachi.isEmpty() || sdt.isEmpty() || phi.isEmpty() || ngay.isEmpty() || gio.isEmpty()) {
                    Toast.makeText(BookingActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    // Lấy DoctorId từ Intent (giả sử đã có ID bác sĩ truyền vào từ MainActivity hoặc màn hình trước)
                    Intent intent = getIntent();
                    int doctorId = intent.getIntExtra("DoctorId", -1); // Giả sử truyền DoctorId qua Intent

                    // Lấy UserId (có thể từ session hoặc từ đăng nhập, ví dụ UserId là 1)
                    int userId = 1; // Bạn có thể thay bằng cách lấy thông tin người dùng từ hệ thống đăng nhập

                    // Kiểm tra giá trị DoctorId hợp lệ
                    if (doctorId == -1) {
                        Toast.makeText(BookingActivity.this, "Lỗi: Không tìm thấy bác sĩ!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Tạo đối tượng Booking
                    Booking booking = new Booking();
                    booking.setDoctorId(doctorId); // Lấy DoctorId từ Intent
                    booking.setUserId(userId); // Lấy UserId từ session hoặc đăng nhập
                    booking.setDate(ngay);
                    booking.setTime(gio);

                    // Lưu vào database thông qua DAO
                    BookingDao bookingDao = new BookingDao(getApplicationContext());
                    bookingDao.createBooking(booking);

                    // Hiển thị thông báo
                    Toast.makeText(BookingActivity.this, "Đặt lịch thành công!", Toast.LENGTH_SHORT).show();

                    // Chuyển về trang chính hoặc refresh giao diện
                    Intent intentBack = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intentBack);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(BookingActivity.this, "Đặt lịch thất bại. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private String getHour() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return hour+":"+minute;
    }

    private String getDate() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return day+"/"+(month+1)+"/"+year;
    }

    //Time
    private void getandsetTime() {
        final Calendar c = Calendar.getInstance();
        myHour = c.get(Calendar.HOUR_OF_DAY);
        myMinute = c.get(Calendar.MINUTE);

        myTime = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {//h-m
                String timeSelected;
                    timeSelected = i+":"+i1;
                Toast.makeText(BookingActivity.this, timeSelected, Toast.LENGTH_SHORT).show();
                txtGio.setText(timeSelected);
            }
        };
    }

    private void showTimePickerDialog() {
        int style = TimePickerDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, myTime, myHour, myMinute, true);
        timePickerDialog.show();
    }

    //Date
    private void getandsetDate() {
        //Lấy ngày từ java.util.Calendar
        //sử dụng final để giá trị ko bị ghi đè, thay đổi
        final Calendar c = Calendar.getInstance();
        myYear = c.get(Calendar.YEAR);
        myMonth = c.get(Calendar.MONTH);
        myDay = c.get(Calendar.DAY_OF_MONTH);

        //Xử lý sự kiện chọn
        myDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {//y-m-d
                //month có value: 0->11
                String dateSelected = i2+"/"+(i1+1)+"/"+i;
                Toast.makeText(BookingActivity.this, dateSelected, Toast.LENGTH_SHORT).show();
                //Cập nhận sang textbox
                txtNgay.setText(dateSelected);
            }
        };

    }

    private void showDatePickerDialog() {
        //tạo style
        int style = DatePickerDialog.THEME_HOLO_DARK;

        //tạo dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, style, myDate, myYear, myMonth, myDay);
        datePickerDialog.show();
    }


    private void getWidget() {
        txtHoten = findViewById(R.id.txtHoten);
        txtDiachi = findViewById(R.id.txtDiachi);
        txtSdt = findViewById(R.id.txtSdt);
        txtPhi = findViewById(R.id.txtPhi);
        btnBook = findViewById(R.id.btnBook);
        btnBack = findViewById(R.id.btnBack);
        txtNgay = findViewById(R.id.txtNgay);
        txtGio = findViewById(R.id.txtGio);
        btnNgay = findViewById(R.id.btnNgay);
        btnGio = findViewById(R.id.btnGio);
    }
}
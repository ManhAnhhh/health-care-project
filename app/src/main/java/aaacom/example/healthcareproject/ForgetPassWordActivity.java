package aaacom.example.healthcareproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ForgetPassWordActivity extends AppCompatActivity {

    EditText confirmPhone, confirmOTP;
    Button btnConfirm;
    TextView txtSendOTP;
    private String generatedOtp;
    private static final int SMS_PERMISSION_REQUEST_CODE = 101; // Mã yêu cầu quyền SMS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_word);

        confirmPhone = findViewById(R.id.confirmPhone);
        confirmOTP = findViewById(R.id.confirmOTP);
        txtSendOTP = findViewById(R.id.txtSendOTP);
        btnConfirm = findViewById(R.id.btnConfirm);

        // Tạo mã OTP và lưu vào biến toàn cục
        generatedOtp = generateOtp();

        txtSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = confirmPhone.getText().toString().trim();
                if (phone.isEmpty()) {
                    Toast.makeText(ForgetPassWordActivity.this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra quyền gửi SMS và yêu cầu nếu cần
                if (ContextCompat.checkSelfPermission(ForgetPassWordActivity.this, Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Nếu chưa có quyền, yêu cầu quyền
                    ActivityCompat.requestPermissions(ForgetPassWordActivity.this,
                            new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_REQUEST_CODE);
                } else {
                    // Nếu có quyền, gửi OTP qua SMS
                    sendOtpToPhone(phone, generatedOtp);
                }
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredOtp = confirmOTP.getText().toString().trim();

                if (enteredOtp.isEmpty()) {
                    Toast.makeText(ForgetPassWordActivity.this, "Vui lòng nhập mã OTP", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (enteredOtp.equals(generatedOtp)) {
                    // Lấy phone
                    String phone = confirmPhone.getText().toString().trim();

                    // Mã OTP đúng, chuyển sang ResetPassActivity
                    Intent intent = new Intent(ForgetPassWordActivity.this, ResetPasswordActivity.class);
                    intent.putExtra("phone", phone);
                    // Khởi chạy activity mới
                    startActivity(intent);
                } else {
                    // Mã OTP sai, hiển thị thông báo lỗi
                    Toast.makeText(ForgetPassWordActivity.this, "Mã OTP không đúng. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String generateOtp() {
        // Tạo mã OTP ngẫu nhiên 6 chữ số
        int otp = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(otp);
    }

    // Hàm gửi mã OTP qua SMS mà không hiển thị ứng dụng SMS
    private void sendOtpToPhone(String phoneNumber, String otp) {
        String message = "Mã OTP của bạn là: " + otp;
        SmsManager smsManager = SmsManager.getDefault();
        try {
            // Gửi SMS mà không mở giao diện ứng dụng SMS
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "Mã OTP đã được gửi qua SMS", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Không thể gửi SMS: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Xử lý kết quả yêu cầu quyền gửi SMS
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền được cấp, gửi OTP qua SMS
                String phone = confirmPhone.getText().toString().trim();
                sendOtpToPhone(phone, generatedOtp);
            } else {
                // Quyền bị từ chối
                Toast.makeText(this, "Quyền gửi SMS bị từ chối. Không thể gửi OTP.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

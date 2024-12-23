package aaacom.example.healthcareproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import aaacom.example.healthcareproject.dao.UserDao;

public class ResetPasswordActivity extends AppCompatActivity {
    TextView txtPhone, txtReturnLogin;
    EditText editNewPass, editAgainPass;
    Button btnResetPass;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);

        txtPhone = findViewById(R.id.txtPhone);
        txtReturnLogin = findViewById(R.id.txtReturnLogin);
        editNewPass = findViewById(R.id.editNewPass);
        editAgainPass = findViewById(R.id.editAgainPass);
        btnResetPass = findViewById(R.id.btnResetPass);

        userDao = new UserDao(this); // Khởi tạo UserDao

        // Lấy email và password từ Intent
        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");

        // Hiển thị thông tin trong TextViews (hoặc làm gì đó khác)
        if (phone != null) {
            txtPhone.setText(phone);
        }

        txtReturnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Đặt lại mật khẩu
        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = editNewPass.getText().toString().trim();
                String againPassword = editAgainPass.getText().toString().trim();

                if (newPassword.isEmpty() || againPassword.isEmpty()) {
                    Toast.makeText(ResetPasswordActivity.this, "Vui lòng nhập mật khẩu mới", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPassword.equals(againPassword)) {
                    Toast.makeText(ResetPasswordActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isPasswordReset = userDao.resetPassword(phone, newPassword);

                if (isPasswordReset) {
                    Toast.makeText(ResetPasswordActivity.this, "Mật khẩu đã được đặt lại thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "Không thể đặt lại mật khẩu. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
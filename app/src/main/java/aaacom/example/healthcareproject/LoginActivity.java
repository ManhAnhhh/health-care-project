package aaacom.example.healthcareproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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

public class LoginActivity extends AppCompatActivity {
    TextView txtRegister, txtForgetPassword;
    EditText editTK, edtPassword;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        txtForgetPassword = findViewById(R.id.txtForgetPassword);
        txtRegister = findViewById(R.id.txtRegister);
        btnLogin = findViewById(R.id.btnLogin);
        editTK = findViewById(R.id.editTK);
        edtPassword = findViewById(R.id.editMK);

        UserDao userDao = new UserDao(this);

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        txtForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPassWordActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(v -> {
            String account = editTK.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            // Kiểm tra nếu thông tin chưa đầy đủ
            if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            } else {
                // Kiểm tra đăng nhập
                if (userDao.loginUser(account, password)) {
                    // Nếu đăng nhập thành công, chuyển đến màn hình chính
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Nếu thông tin đăng nhập không hợp lệ
                    Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
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
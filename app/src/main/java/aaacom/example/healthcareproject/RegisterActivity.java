package aaacom.example.healthcareproject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import aaacom.example.healthcareproject.dao.UserDao;
import aaacom.example.healthcareproject.entities.User;
import aaacom.example.healthcareproject.utils.DatabaseUtils;

public class RegisterActivity extends AppCompatActivity {

    private EditText editNewTK, editMK, editNewPass, editAgainPass;
    private Button btnRegister;
    private TextView txtLogin;
    private UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Khởi tạo UserDao
        userDao = new UserDao(this);

        // Khởi tạo các view
        editNewTK = findViewById(R.id.editNewTK);
        editMK = findViewById(R.id.editMK);
        editNewPass = findViewById(R.id.editNewPass);
        editAgainPass = findViewById(R.id.editAgainPass);
        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);

        // Bắt sự kiện click đăng ký
        btnRegister.setOnClickListener(view -> {
            String name = editNewTK.getText().toString();
            String email = editMK.getText().toString();
            String password = editNewPass.getText().toString();
            String confirmPassword = editAgainPass.getText().toString();

            if (validateInputs(name, email, password, confirmPassword)) {
                // Tạo đối tượng User
                User user = new User(email, name, password);

                // Gọi phương thức đăng ký người dùng
                boolean registerUser = userDao.registerUser(user);

                if (registerUser) {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    // Chuyển sang màn hình login hoặc quay lại trang login
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Email đã tồn tại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Bắt sự kiện chuyển sang màn hình đăng nhập
        txtLogin.setOnClickListener(view -> {
            // Chuyển sang màn hình đăng nhập (ví dụ dùng Intent)
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private boolean validateInputs(String name, String email, String password, String confirmPassword) {
        // Kiểm tra tên
        if (TextUtils.isEmpty(name)) {
            editNewTK.setError("Vui lòng nhập họ và tên");
            return false;
        }

        // Kiểm tra email
        if (TextUtils.isEmpty(email) || !email.contains("@")) {
            editMK.setError("Vui lòng nhập email hợp lệ");
            return false;
        }

        // Kiểm tra mật khẩu
        if (TextUtils.isEmpty(password)) {
            editNewPass.setError("Vui lòng nhập mật khẩu");
            return false;
        }

        // Kiểm tra xác nhận mật khẩu
        if (TextUtils.isEmpty(confirmPassword)) {
            editAgainPass.setError("Vui lòng xác nhận mật khẩu");
            return false;
        }

        // Kiểm tra mật khẩu và xác nhận mật khẩu có giống nhau không
        if (!password.equals(confirmPassword)) {
            editAgainPass.setError("Mật khẩu xác nhận không khớp");
            return false;
        }

        return true;
    }
}

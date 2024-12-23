package aaacom.example.healthcareproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import aaacom.example.healthcareproject.dao.UserDao;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        UserDao userDao = new UserDao(this);

        // Kiểm tra trạng thái đăng nhập
        if (userDao.isUserLoggedIn()) {
            // Nếu đã đăng nhập, chuyển sang màn hình Home
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else {
            // Nếu chưa đăng nhập, chuyển sang màn hình Login
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        // Kết thúc SplashActivity để không quay lại màn hình này
        finish();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
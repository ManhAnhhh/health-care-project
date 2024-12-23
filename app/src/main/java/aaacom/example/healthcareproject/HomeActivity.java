package aaacom.example.healthcareproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import aaacom.example.healthcareproject.dao.UserDao;

public class HomeActivity extends AppCompatActivity {
    CardView cardViewOrder, cardViewDatThuoc, cardViewFindDoctor,
             cardViewLichKham, cardViewArticle, cardViewLogout;
    TextView username;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        userDao = new UserDao(this);
        getWidgets();



        events();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void events() {
        cardViewOrder.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, OrdersActivity.class));
        });

        cardViewDatThuoc.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, BuyMedicineActivity.class));
        });
        cardViewFindDoctor.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, FindDoctorActivity.class));
        });
        cardViewLichKham.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, BookingDetails.class));
        });
        cardViewArticle.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, ArticleListActivity.class));
        });

        cardViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDao userDao = new UserDao(HomeActivity.this);
                userDao.logoutUser();
                // Chuyển hướng về màn hình đăng nhập
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(HomeActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getWidgets() {
        cardViewOrder = findViewById(R.id.cardview_Orders);
        cardViewDatThuoc = findViewById(R.id.cardview_DatThuoc);
        cardViewFindDoctor = findViewById(R.id.cardView_FindDoctor);
        cardViewLichKham = findViewById(R.id.cardview_LichKham);
        cardViewArticle = findViewById(R.id.cardView_Article);
        cardViewLogout = findViewById(R.id.cardview_Logout);
        username = findViewById(R.id.username);
        if (userDao != null) {
            String name = userDao.getLoggedInUserFullName();
            name = (name != null && !name.isEmpty()) ? name : "";
            username.setText("Xin chào, " + name);
        }

    }
}
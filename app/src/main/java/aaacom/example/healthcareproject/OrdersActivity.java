package aaacom.example.healthcareproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

import aaacom.example.healthcareproject.dao.OrderDao;
import aaacom.example.healthcareproject.entities.Order;
import aaacom.example.healthcareproject.utils.MenuEventUtil;

public class OrdersActivity extends AppCompatActivity {
    Button btnBack;

    ListView lv_Orders;
    OrderAdapter adapter=null;;
    ArrayList<Order> orders = new ArrayList<Order>();
    MaterialToolbar toolbar;
    OrderDao orderDao;

    private final String TAG = "Đơn đặt hàng!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_orders);

        // Setup Toolbar
        toolbar = findViewById(R.id.tbr_Orders);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

//        btnBack = findViewById(R.id.btn_Back);
//        btnBack.setOnClickListener(view -> {
//            finish();
//        });

        orderDao = new OrderDao(this);

        lv_Orders = findViewById(R.id.lv_Orders);
        adapter = new OrderAdapter(this, R.layout.order_item_list_view, orders);
        lv_Orders.setAdapter(adapter);

        orders.clear();
        orders.addAll(orderDao.getOrders());
        adapter.notifyDataSetChanged();

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
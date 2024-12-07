package aaacom.example.healthcareproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import aaacom.example.healthcareproject.dao.OrderDao;
import aaacom.example.healthcareproject.entities.Order;

public class OrdersActivity extends AppCompatActivity {
    Button btnBack;

    ListView lv_Orders;
    OrderAdapter adapter=null;;
    ArrayList<Order> orders = new ArrayList<Order>();

    OrderDao orderDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_orders);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            finish();
        });

        orderDao = new OrderDao(this);

        lv_Orders = findViewById(R.id.lvLichkham);
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
}
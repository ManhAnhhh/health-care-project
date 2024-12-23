package aaacom.example.healthcareproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Calendar;

import aaacom.example.healthcareproject.dao.CartDao;
import aaacom.example.healthcareproject.dao.OrderDao;
import aaacom.example.healthcareproject.entities.Cart;
import aaacom.example.healthcareproject.entities.Order;
import aaacom.example.healthcareproject.utils.Commons;
import aaacom.example.healthcareproject.utils.MenuEventUtil;

public class CartBuyMedicineActivity extends AppCompatActivity {
    SimpleAdapter sa;
    TextView tvTotal;
    ListView lst;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton, btnCheckout, btnBack;
    private String[][] packages = {};
    private CartAdapter adapter;
    EditText txtOrderPlace;
    CartDao cartDao;
    OrderDao orderDao;
    MaterialToolbar toolbar;

    float total_amount = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart_buy_medicine);

        // Setup Toolbar
        toolbar = findViewById(R.id.tbr_cartBuyMedicine);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        dateButton = findViewById(R.id.buttonBMCartDate);
        btnCheckout = findViewById(R.id.buttonBMCartCheckout);
        btnBack = findViewById(R.id.buttonBMStartBack);
        tvTotal = findViewById(R.id.textViewBMCartTotalCost);
        lst = findViewById(R.id.listViewBMCart);
        txtOrderPlace = findViewById(R.id.txt_OrderPlaceMedicine);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Guest");

        cartDao = new CartDao(this);
        ArrayList<Cart> cartItems = cartDao.getCartItems();
        orderDao = new OrderDao(CartBuyMedicineActivity.this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        adapter = new CartAdapter(this, cartItems, new CartAdapter.OnSelectionChangedListener() {
            @Override
            public void onSelectionChanged(ArrayList<Cart> selectedItems) {
                // Update total cost when selection changes
                updateTotalCost(selectedItems);
            }
        });
        lst.setAdapter(adapter);

        btnBack.setOnClickListener(v -> {
            finish();
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Cart> selectedItems = adapter.getSelectedItems();

                if (selectedItems.isEmpty()) {
                    new AlertDialog.Builder(CartBuyMedicineActivity.this)
                            .setTitle("Không có thuốc nào được chọn")
                            .setMessage("Để đặt hàng mua thuốc, vui lòng chọn ít nhất một thuốc.")
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                }

                // Calculate total amount based on selected items
//                float totalAmount = 0.0f;
//                for (Cart item : selectedItems) {
//                    totalAmount += item.getPrice() * item.getQuantity();
//                }
//                tvTotal.setText(String.format("Total Cost: $%.2f", totalAmount));

                String orderPlace = txtOrderPlace.getText().toString().trim();

                if (orderPlace == null || orderPlace.isEmpty()) {
                    new AlertDialog.Builder(CartBuyMedicineActivity.this)
                            .setTitle("Địa chỉ nhận hàng.")
                            .setMessage("Vui lòng nhập địa chỉ nhận hàng.")
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                }

                for (Cart item : selectedItems) {
                    Order order = new Order(item.getQuantity(), item.getMedicine_id(), orderPlace, item.getOrderDate(), item.getCustomerName(), total_amount);

                    orderDao.createOrder(order);
                }

                for (Cart item : selectedItems) {
                    cartDao.deleteCartItem(item.getId());
                }

                new AlertDialog.Builder(CartBuyMedicineActivity.this)
                        .setTitle("Đặt hàng mua thuốc!")
                        .setMessage("Đặt mua thuốc thành công! Bạn sẽ được chuyển sang màn hình đơn hàng sau 5 giây.")
                        .setPositiveButton("OK", (dialog, which) -> {
                            ArrayList<Cart> updatedCartItems = cartDao.getCartItems();
                            adapter.updateData(updatedCartItems);
                            adapter.notifyDataSetChanged();
                        })
                        .show();

                new Handler().postDelayed(() -> {
                    startActivity(new Intent(CartBuyMedicineActivity.this, OrdersActivity.class));
                    finish();
                }, 5000);
            }
        });

        initDatePicker();
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }

    private void updateTotalCost(ArrayList<Cart> selectedItems) {
        for (Cart item : selectedItems) {
            total_amount += item.getPrice() * item.getQuantity();
        }
        tvTotal.setText("Tổng tiền:  " + Commons.FormatDecimalCommon(total_amount));
    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateButton.setText(dayOfMonth+"/"+ month + "/" +year);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis() + 86400000);
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
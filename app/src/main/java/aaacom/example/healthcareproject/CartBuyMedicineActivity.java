package aaacom.example.healthcareproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;

import aaacom.example.healthcareproject.dao.CartDao;
import aaacom.example.healthcareproject.dao.OrderDao;
import aaacom.example.healthcareproject.entities.Cart;
import aaacom.example.healthcareproject.entities.Order;

public class CartBuyMedicineActivity extends AppCompatActivity {
    SimpleAdapter sa;
    TextView tvTotal;
    ListView lst;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton, btnCheckout, btnBack;
    private String[][] packages = {};
    private CartAdapter adapter;

    CartDao cartDao;
    OrderDao orderDao;

    float total_amount = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart_buy_medicine);

        dateButton = findViewById(R.id.buttonBMCartDate);
        btnCheckout = findViewById(R.id.buttonBMCartCheckout);
        btnBack = findViewById(R.id.buttonBMStartBack);
        tvTotal = findViewById(R.id.textViewBMCartTotalCost);
        lst = findViewById(R.id.listViewBMCart);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "testuser"); // Default to "testuser" if not set

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
                ArrayList<Cart> selectedItems = adapter.getSelectedItems(); // Get selected items from the adapter

                if (selectedItems.isEmpty()) {
                    // Show alert if no items are selected
                    new AlertDialog.Builder(CartBuyMedicineActivity.this)
                            .setTitle("No Items Selected")
                            .setMessage("Please select at least one item to proceed.")
                            .setPositiveButton("OK", null)
                            .show();
                    return; // Exit method if no items are selected
                }

                // Calculate total amount based on selected items
//                float totalAmount = 0.0f;
//                for (Cart item : selectedItems) {
//                    totalAmount += item.getPrice() * item.getQuantity(); // Calculate total for selected items
//                }
//                tvTotal.setText(String.format("Total Cost: $%.2f", totalAmount)); // Update total cost display

                // Process each selected item and create an order
                for (Cart item : selectedItems) {
                    Order order = new Order(item.getQuantity(), item.getMedicine_id(), item.getOrderPlace(), item.getOrderDate(), item.getCustomerName(), total_amount);

                    orderDao.createOrder(order);
                }

                // Remove selected items from the cart after placing the order
                for (Cart item : selectedItems) {
                    cartDao.deleteCartItem(item.getId()); // Delete cart item
                }

                // Show confirmation dialog
                new AlertDialog.Builder(CartBuyMedicineActivity.this)
                        .setTitle("Đặt hàng mua thuốc!")
                        .setMessage("Đặt mua thuốc thành công!\\nBạn sẽ được chuyển sang màn hình đơn hàng sau 5 giây.")
                        .setPositiveButton("OK", (dialog, which) -> {
                            // After successful checkout, refresh the cart list and update the adapter
                            ArrayList<Cart> updatedCartItems = cartDao.getCartItems(); // Get updated cart items
                            adapter.updateData(updatedCartItems); // Update the adapter with new cart items
                            adapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
                        })
                        .show();

                // Start a delayed task to navigate to OrderActivity after 5 seconds
                new Handler().postDelayed(() -> {
                    startActivity(new Intent(CartBuyMedicineActivity.this, OrdersActivity.class));
                    finish(); // Close the current activity if no longer needed
                }, 5000); // Delay in milliseconds (5 seconds)
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
            total_amount += item.getPrice() * item.getQuantity(); // Calculate total for selected items
        }
        tvTotal.setText(String.format("Total Cost: $%.2f", total_amount)); // Update total cost display
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
}
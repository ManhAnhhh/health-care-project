package aaacom.example.healthcareproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

import aaacom.example.healthcareproject.dao.CartDao;
import aaacom.example.healthcareproject.dao.MedicineDao;
import aaacom.example.healthcareproject.dao.OrderDao;
import aaacom.example.healthcareproject.entities.Cart;
import aaacom.example.healthcareproject.entities.Medicine;
import aaacom.example.healthcareproject.utils.Commons;
import aaacom.example.healthcareproject.utils.MenuEventUtil;

public class BuyMedicineDetailsActivity extends AppCompatActivity {

    TextView tvTotalCost, txtvName, txtvCategory, txtvCongDung;
    EditText eDetails;
    Button btnAddToCart, btnBack;
    OrderDao orderDao;
    CartDao cartDao;
    MedicineDao medicineDao;
    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buy_medicine_details);

        // Setup Toolbar
        toolbar = findViewById(R.id.tbr_medicineInfo);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        orderDao = new OrderDao(this);
        cartDao = new CartDao(this);
        medicineDao = new MedicineDao(this);

        eDetails = findViewById(R.id.editTextBMDTextMultiLine);
        eDetails.setKeyListener(null);
        tvTotalCost = findViewById(R.id.textViewBMDTotalCost);
        txtvName = findViewById(R.id.txtv_Name);
        txtvCategory = findViewById(R.id.txt_Category);
        txtvCongDung = findViewById(R.id.txt_CongDung);
        btnAddToCart = findViewById(R.id.buttonBMDAddToCart);
        btnBack = findViewById(R.id.buttonBMDGoBackBuy);

        Intent intent = getIntent();

        eDetails.setText(intent.getStringExtra("name"));
        tvTotalCost.setText("Giá thuốc: " + Commons.FormatDecimalCommon(intent.getFloatExtra("price",0.0f)) + "\n");
        GetMedicineById(intent.getIntExtra("id", 0));

        btnBack.setOnClickListener(v -> {
            finish();
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "Guest");
                String product = intent.getStringExtra("name");
                float price = intent.getFloatExtra("price", 0.0f);
                int id = intent.getIntExtra("id", 0);

                // Create a new Cart object
                Cart cart = new Cart();
                cart.setCustomerName(username);
                cart.setProductName(product);
                cart.setPrice(price);
                cart.setOrderDate("2024-12-05");
                cart.setOrderPlace("TestPlace");
                cart.setQuantity(1);
                cart.setMedicine_id(id);

                // Log cart values to verify
                Log.d("AddToCart", "Cart details: " +
                        "Customer: " + cart.getCustomerName() +
                        ", Product: " + cart.getProductName() +
                        ", Price: " + cart.getPrice() +
                        ", Quantity: " + cart.getQuantity());

                // Add to cart
                cartDao.addToCart(cart); // This will either update the quantity or insert a new item
                Toast.makeText(getApplicationContext(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(BuyMedicineDetailsActivity.this, BuyMedicineActivity.class));
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void GetMedicineById(int id) {
        Medicine medicine = medicineDao.GetMedicineById(id);
        if (medicine != null) {
            txtvName.setText(medicine.getName());
            txtvCategory.setText("Danh mục thuốc: " + medicine.getCategory());
            txtvCongDung.setText("CÔng dụng của thuốc: " + medicine.getCong_dung());
            eDetails.setText(medicine.getDescription());
        } else {
            Toast.makeText(this, "Không tìm thấy thuốc", Toast.LENGTH_SHORT).show();
        }
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
        onBackPressed();
        return true;
    }
}
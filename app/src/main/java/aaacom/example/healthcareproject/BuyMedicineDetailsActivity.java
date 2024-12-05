package aaacom.example.healthcareproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import aaacom.example.healthcareproject.dao.CartDao;
import aaacom.example.healthcareproject.dao.OrderDao;
import aaacom.example.healthcareproject.entities.Cart;

public class BuyMedicineDetailsActivity extends AppCompatActivity {

    TextView tvPackageName, tvTotalCost;
    EditText eDetails;
    Button btnAddToCart, btnBack;
    OrderDao orderDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buy_medicine_details);

        tvPackageName = findViewById(R.id.textViewBMDPackageName);
        eDetails = findViewById(R.id.editTextBMDTextMultiLine);
        eDetails.setKeyListener(null);
        tvTotalCost = findViewById(R.id.textViewBMDTotalCost);


        btnAddToCart = findViewById(R.id.buttonBMDAddToCart);
        btnBack = findViewById(R.id.buttonBMDGoBackBuy);

        Intent intent = getIntent();

        tvPackageName.setText(String.valueOf(intent.getStringExtra("name")));
        eDetails.setText(intent.getStringExtra("name"));
        tvTotalCost.setText("Total Cost: " + String.valueOf(intent.getFloatExtra("price",0.0f)) + "\n");

        orderDao = new OrderDao(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(BuyMedicineDetailsActivity.this, BuyMedicineActivity.class)
                );
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "testuser");
                String product = tvPackageName.getText().toString();
                float price = intent.getFloatExtra("price", 0.0f);
                int id = intent.getIntExtra("id", 0);

                // Create a new Cart object
                Cart cart = new Cart();
                cart.setCustomerName(username);
                cart.setProductName(product);
                cart.setPrice(price);
                cart.setOrderDate("2024-12-05"); // Replace with actual date
                cart.setOrderPlace("TestPlace"); // Replace with actual place
                cart.setQuantity(1); // Initially set quantity to 1
                cart.setMedicine_id(id);

                // Log cart values to verify
                Log.d("AddToCart", "Cart details: " +
                        "Customer: " + cart.getCustomerName() +
                        ", Product: " + cart.getProductName() +
                        ", Price: " + cart.getPrice() +
                        ", Quantity: " + cart.getQuantity());

                // Add to cart
                CartDao cartDao = new CartDao(BuyMedicineDetailsActivity.this);
                cartDao.addToCart(cart); // This will either update the quantity or insert a new item
                Toast.makeText(getApplicationContext(), "Added to cart", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(BuyMedicineDetailsActivity.this, BuyMedicineActivity.class));
            }
        });





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
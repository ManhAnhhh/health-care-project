package aaacom.example.healthcareproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {
    CardView cardViewOrder;
    CardView cardViewDatThuoc;
    CardView cardViewFindDoctor;
    CardView cardViewLichKham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

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
    }

    private void getWidgets() {
        cardViewOrder = findViewById(R.id.cardview_Orders);
        cardViewDatThuoc = findViewById(R.id.cardview_DatThuoc);
        cardViewFindDoctor = findViewById(R.id.cardView_FindDoctor);
        cardViewLichKham = findViewById(R.id.cardview_LichKham);

    }
}
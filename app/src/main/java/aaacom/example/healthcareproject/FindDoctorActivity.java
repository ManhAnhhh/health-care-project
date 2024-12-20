package aaacom.example.healthcareproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

import aaacom.example.healthcareproject.utils.MenuEventUtil;

public class FindDoctorActivity extends AppCompatActivity {
    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_find_doctor);
        LinearLayout itemFamilyPhysicians = findViewById(R.id.item_family_physicians);
        LinearLayout itemDietician = findViewById(R.id.item_dietician);
        LinearLayout itemDentist = findViewById(R.id.item_dentist);
        LinearLayout itemSurgeon = findViewById(R.id.item_surgeon);
        LinearLayout itemCardiologists = findViewById(R.id.item_cardiologists);
//        LinearLayout itemBack = findViewById(R.id.item_back);

        // Setup Toolbar
        toolbar = findViewById(R.id.tbr_FindDoctor);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        itemFamilyPhysicians.setOnClickListener(v -> openDoctorList("Bác sĩ gia đình"));
        itemDietician.setOnClickListener(v -> openDoctorList("Chuyên viên dinh dưỡng"));
        itemDentist.setOnClickListener(v -> openDoctorList("Răng hàm mặt"));
        itemSurgeon.setOnClickListener(v -> openDoctorList("Bác sĩ phẫu thuật"));
        itemCardiologists.setOnClickListener(v -> openDoctorList("Bác sĩ tim mạch"));
//        itemBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
    }
    private void openDoctorList(String specialization) {
        Intent intent = new Intent(this, DoctorListActivity.class);
        intent.putExtra("specialization", specialization);
        startActivity(intent);
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
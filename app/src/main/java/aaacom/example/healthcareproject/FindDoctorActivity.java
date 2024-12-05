package aaacom.example.healthcareproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FindDoctorActivity extends AppCompatActivity {

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

        itemFamilyPhysicians.setOnClickListener(v -> openDoctorList("Bác sĩ gia đình"));
        itemDietician.setOnClickListener(v -> openDoctorList("Chuyên viên dinh dưỡng"));
        itemDentist.setOnClickListener(v -> openDoctorList("Răng hàm mặt"));
        itemSurgeon.setOnClickListener(v -> openDoctorList("Bác sĩ phẫu thuật"));
        itemCardiologists.setOnClickListener(v -> openDoctorList("Bác sĩ tim mạch"));
    }
    private void openDoctorList(String specialization) {
        Intent intent = new Intent(this, DoctorListActivity.class);
        intent.putExtra("specialization", specialization);
        startActivity(intent);
    }
}
package aaacom.example.healthcareproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import aaacom.example.healthcareproject.dao.DoctorDao;
import aaacom.example.healthcareproject.entities.Doctor;

public class DoctorListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_list);
        String specialization = getIntent().getStringExtra("specialization");

        DoctorDao doctorDao = new DoctorDao(this);
        ArrayList<Doctor> doctorList = doctorDao.getDoctorsBySpecialization(specialization);

        ArrayList<String> doctorInfoList = new ArrayList<>();
        for (Doctor doctor : doctorList) {
            doctorInfoList.add(doctor.getName() + " - " + doctor.getSpecialization() + " - " + doctor.getExperience() + " năm kinh nghiệm - "
                    + " Địa chỉ: " + doctor.getHospitalAddress() + " - Phí khám: " + doctor.getFee() +"VND \n");
        }

        ListView listView = findViewById(R.id.lv_doctors);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, doctorInfoList);
        listView.setAdapter(adapter);

        // Bắt sự kiện nhấn vào một item trong ListView
        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Lấy bác sĩ đã chọn
            Doctor selectedDoctor = doctorList.get(position);

            // Tạo Intent để chuyển dữ liệu sang Activity khác (ví dụ: DoctorDetailActivity)
            Intent intent = new Intent(DoctorListActivity.this, BookingActivity.class);
            intent.putExtra("DoctorId", selectedDoctor.getId());
            intent.putExtra("DoctorName", selectedDoctor.getName());
            intent.putExtra("DoctorContact", selectedDoctor.getContact());
            intent.putExtra("HospitalAddress", selectedDoctor.getHospitalAddress());
            intent.putExtra("DoctorFee", selectedDoctor.getFee());

            // Chuyển đến Activity tiếp theo
            startActivity(intent);
        });

        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorListActivity.this, FindDoctorActivity.class);
                startActivity(intent);
            }
        });
    }
}
package aaacom.example.healthcareproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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
                    + " Địa chỉ: " + doctor.getHospitalAddress() + "Phí khám: " + doctor.getFee() +"VND \n");
        }

        ListView listView = findViewById(R.id.lv_doctors);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, doctorInfoList);
        listView.setAdapter(adapter);
    }
}
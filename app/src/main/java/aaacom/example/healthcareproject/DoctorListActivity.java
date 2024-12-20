package aaacom.example.healthcareproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

import aaacom.example.healthcareproject.DoctorAdapter;
import aaacom.example.healthcareproject.R;
import aaacom.example.healthcareproject.dao.DoctorDao;
import aaacom.example.healthcareproject.entities.Doctor;
import aaacom.example.healthcareproject.utils.MenuEventUtil;

public class DoctorListActivity extends AppCompatActivity {

    private DoctorAdapter adapter;
    private ArrayList<Doctor> doctorList;
    private ArrayList<Doctor> filteredList;
    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        // Setup Toolbar
        toolbar = findViewById(R.id.tbr_ListDoctor);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        String specialization = getIntent().getStringExtra("specialization");

        DoctorDao doctorDao = new DoctorDao(this);
        doctorList = doctorDao.getDoctorsBySpecialization(specialization);
        filteredList = new ArrayList<>(doctorList);

        ListView listView = findViewById(R.id.lv_doctors);
        adapter = new DoctorAdapter(this, R.layout.item_doctor_list_view, filteredList);
        listView.setAdapter(adapter);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Không làm gì khi submit, chỉ lọc khi người dùng nhập
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterDoctors(newText);
                return true;
            }
        });

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
    }

    private void filterDoctors(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(doctorList);
        } else {
            for (Doctor doctor : doctorList) {
                if (doctor.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(doctor);
                }
            }
        }
        adapter.notifyDataSetChanged();
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

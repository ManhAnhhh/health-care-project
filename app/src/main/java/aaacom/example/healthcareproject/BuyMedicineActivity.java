package aaacom.example.healthcareproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;

import aaacom.example.healthcareproject.dao.MedicineDao;
import aaacom.example.healthcareproject.entities.Medicine;
import aaacom.example.healthcareproject.utils.MenuEventUtil;

public class BuyMedicineActivity extends AppCompatActivity {
    Toolbar tbr_buy_meddicine;
    ArrayList<HashMap<String, String>> list;
    SimpleAdapter sa;
    ListView lst;
    MedicineAdapter adapter;
    ArrayList<Medicine> medicines = new ArrayList<>();
    MedicineDao medicineDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine);

        tbr_buy_meddicine = findViewById(R.id.tbr_buy_meddicine);
        setSupportActionBar(tbr_buy_meddicine);
        tbr_buy_meddicine.setNavigationOnClickListener(v -> onBackPressed());

        lst = findViewById(R.id.listViewBM);

        medicineDao = new MedicineDao(this);

        // Fetch medicines from database
        ArrayList<Medicine> medicines = medicineDao.getMedicines();
        list = new ArrayList<>();

        adapter = new MedicineAdapter(this, R.layout.medicine_item_list_view, medicines);
        medicineDao = new MedicineDao(this);
        

        for (Medicine medicine : medicines) {
            HashMap<String, String> item = new HashMap<>();
            item.put("id", String.valueOf(medicine.getId()));
            item.put("name", medicine.getName());
            item.put("price", "Price: $" + medicine.getPrice());
            list.add(item);
        }

        // Create and set the adapter
        medicines.clear();
        medicines.addAll(medicineDao.getMedicines());
        lst.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        lst.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(BuyMedicineActivity.this, BuyMedicineDetailsActivity.class);
                intent.putExtra("id", medicines.get(i).getId());
                intent.putExtra("name", medicines.get(i).getName());
                intent.putExtra("price", medicines.get(i).getPrice());
                startActivity(intent);
            });
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
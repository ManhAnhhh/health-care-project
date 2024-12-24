package aaacom.example.healthcareproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
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

    SearchView searchViewBM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine);

        tbr_buy_meddicine = findViewById(R.id.tbr_buy_meddicine);
        setSupportActionBar(tbr_buy_meddicine);
        tbr_buy_meddicine.setNavigationOnClickListener(v -> onBackPressed());

        lst = findViewById(R.id.listViewBM);
        searchViewBM = findViewById(R.id.searchViewBM);

        medicineDao = new MedicineDao(this);

        // Fetch medicines from database
        medicines = medicineDao.getMedicines();

        adapter = new MedicineAdapter(this, R.layout.medicine_item_list_view, medicines);
        lst.setAdapter(adapter);

        lst.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(BuyMedicineActivity.this, BuyMedicineDetailsActivity.class);
            intent.putExtra("id", medicines.get(i).getId());
            intent.putExtra("name", medicines.get(i).getName());
            intent.putExtra("price", medicines.get(i).getPrice());
            startActivity(intent);
        });

        // Set up search functionality
        searchViewBM.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    // When the search box is empty, show all medicines
                    adapter.updateList(medicines);
                } else {
                    // Filter medicines based on the query
                    filterMedicines(newText);
                }
                return true;
            }
        });
    }

    // Filter medicines based on search query
    private void filterMedicines(String query) {
        ArrayList<Medicine> filteredList = new ArrayList<>();
        for (Medicine medicine : medicines) {
            if (medicine.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(medicine);
            }
        }

        // Update adapter with filtered list
        adapter.updateList(filteredList);
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
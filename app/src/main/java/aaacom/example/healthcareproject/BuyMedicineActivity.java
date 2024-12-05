package aaacom.example.healthcareproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import aaacom.example.healthcareproject.dao.MedicineDao;
import aaacom.example.healthcareproject.entities.Medicine;

public class BuyMedicineActivity extends AppCompatActivity {

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

        lst = findViewById(R.id.listViewBM);
        Button btnBack = findViewById(R.id.buttonBMBack);
        Button btnGoToCart = findViewById(R.id.buttonBMGoToCart);

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
        // Button listeners
        btnGoToCart.setOnClickListener(view -> {

            startActivity(new Intent(BuyMedicineActivity.this, CartBuyMedicineActivity.class));
        });

        btnBack.setOnClickListener(view -> {
            startActivity(new Intent(BuyMedicineActivity.this, HomeActivity.class));
        });
    }
}
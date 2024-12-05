package aaacom.example.healthcareproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import aaacom.example.healthcareproject.entities.Medicine;
import aaacom.example.healthcareproject.utils.DatabaseUtils;

public class MedicineDao {
    private static String TAG = "MedicineDao";
    private DatabaseUtils databaseUtils;

    public MedicineDao(Context context) {
        databaseUtils = new DatabaseUtils(context);
    }
    public boolean doesTableExist(String tableName) {
        SQLiteDatabase db = databaseUtils.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", new String[]{tableName});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }



    // Get all Medicines
    public ArrayList<Medicine> getMedicines() {
        ArrayList<Medicine> medicines = new ArrayList<>();
        SQLiteDatabase database = databaseUtils.getReadableDatabase();

        // Debugging log
        Log.i(TAG, "getMedicines: Checking Medicine table existence...");

        // Verify table existence before query
        if (!doesTableExist("Medicine")) {
            Log.e(TAG, "getMedicines: Medicine table does not exist!");
            return medicines; // Return empty list if table is missing
        }

        // Query
        String sql = "SELECT * FROM Medicine";
        Cursor cursor = database.rawQuery(sql, null);

        Log.i(TAG, "getMedicines: " + cursor.getCount());

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                float price = cursor.getFloat(2);

                medicines.add(new Medicine(id, name, price));
                cursor.moveToNext();
            }
            cursor.close();
        }

        return medicines;
    }

    // Create Medicine
    public void createMedicine(Medicine medicine) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", medicine.getName());
        values.put("price", medicine.getPrice());

        long medicineId = db.insert("Medicine", null, values);

        Log.i(TAG, "Medicine created: new medicine id: " + medicineId);
    }

    // Delete Medicine
    public void deleteMedicine(int id) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();

        String whereClause = "id=?";
        String[] whereParams = {String.valueOf(id)};

        int affectedRecordAmount = db.delete("Medicine", whereClause, whereParams);

        Log.i(TAG, "Medicine deleted: Affected record amount: " + affectedRecordAmount);
    }
}
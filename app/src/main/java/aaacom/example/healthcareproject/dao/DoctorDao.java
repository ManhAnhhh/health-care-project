package aaacom.example.healthcareproject.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import aaacom.example.healthcareproject.entities.Doctor;
import aaacom.example.healthcareproject.utils.DatabaseUtils;

public class DoctorDao {
    private static final String TAG = "DoctorDao";
    private DatabaseUtils databaseUtils;

    public DoctorDao(Context context) {
        databaseUtils = new DatabaseUtils(context);
    }

    public ArrayList<Doctor> getDoctorsBySpecialization(String specialization) {
        ArrayList<Doctor> doctors = new ArrayList<>();
        SQLiteDatabase db = databaseUtils.getReadableDatabase();

        String query = "SELECT Id, name, specialization, contact, experience, hospital_address, fee FROM Doctor WHERE specialization = ?";
        Cursor cursor = db.rawQuery(query, new String[]{specialization});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int Id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    String contact = cursor.getString(3);
                    int experience = cursor.getInt(4);
                    String hospitalAddress = cursor.getString(5);
                    double fee = cursor.getDouble(6);

                    doctors.add(new Doctor(Id, name, specialization, contact, experience, hospitalAddress, fee));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        db.close();
        return doctors;
    }
}

package aaacom.example.healthcareproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import aaacom.example.healthcareproject.entities.User;
import aaacom.example.healthcareproject.utils.DatabaseUtils;

public class UserDao {
    private DatabaseUtils databaseUtils;

    public UserDao(Context context) {
        databaseUtils = new DatabaseUtils(context);
    }

    // Đăng ký người dùng mới
    public boolean registerUser(User user) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();

        // Kiểm tra nếu email đã tồn tại
        Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE email = ?", new String[]{user.getEmail()});
        if (cursor.getCount() > 0) {
            cursor.close();
            return false; // Người dùng đã tồn tại
        }

        // Thêm người dùng mới
        ContentValues values = new ContentValues();
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        values.put("fullName", user.getFullName());

        long result = db.insert("Users", null, values);
        cursor.close();

        return result != -1; // Trả về true nếu đăng ký thành công
    }

    // Đăng nhập người dùng
    public boolean loginUser(String email, String password) {
        SQLiteDatabase db = databaseUtils.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE email = ? AND password = ?", new String[]{email, password});

        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid; // Trả về true nếu đăng nhập thành công
    }
}

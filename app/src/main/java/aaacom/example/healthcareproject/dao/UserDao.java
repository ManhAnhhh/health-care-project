package aaacom.example.healthcareproject.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import aaacom.example.healthcareproject.entities.User;
import aaacom.example.healthcareproject.utils.DatabaseUtils;

public class UserDao {
    private static final String PREF_NAME = "userSession";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_FULLNAME = "fullName";
    private SharedPreferences sharedPreferences;

    private DatabaseUtils databaseUtils;

    public UserDao(Context context) {
        databaseUtils = new DatabaseUtils(context);
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
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

        // Kiểm tra nếu số điện thoại đã tồn tại
        cursor = db.rawQuery("SELECT * FROM Users WHERE phone = ?", new String[]{user.getPhone()});
        if (cursor.getCount() > 0) {
            cursor.close();
            return false; // Người dùng đã tồn tại với số điện thoại này
        }

        // Thêm người dùng mới
        ContentValues values = new ContentValues();
        values.put("email", user.getEmail());
        values.put("phone", user.getPhone());
        values.put("password", user.getPassword());
        values.put("fullName", user.getFullName());

        long result = db.insert("Users", null, values);
        cursor.close();

        return result != -1; // Trả về true nếu đăng ký thành công
    }

    // Đăng nhập người dùng
    public boolean loginUser(String account, String password) {
        SQLiteDatabase db = databaseUtils.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM Users WHERE (email = ? OR phone = ?) AND password = ?",
                    new String[]{account, account, password});

        boolean isValid = cursor.getCount() > 0;

        if (isValid) {
            // Lấy số điện thoại từ cơ sở dữ liệu và lưu vào SharedPreferences
            cursor.moveToFirst();  // Di chuyển con trỏ đến kết quả đầu tiên
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
            @SuppressLint("Range") String fullName = cursor.getString(cursor.getColumnIndex("fullName"));

            // Lưu email và số điện thoại vào SharedPreferences
            sharedPreferences.edit()
                    .putString(KEY_EMAIL, email)
                    .putString(KEY_PHONE, phone) // Lưu số điện thoại
                    .putString(KEY_FULLNAME, fullName)
                    .apply();
        }

        cursor.close();
        return isValid; // Trả về true nếu đăng nhập thành công
    }

    // Đăng xuất người dùng
    public void logoutUser() {
        sharedPreferences.edit()
                .remove(KEY_EMAIL)
                .remove(KEY_PHONE)
                .remove(KEY_FULLNAME)
                .apply();
    }

    // Kiểm tra trạng thái đăng nhập
    public boolean isUserLoggedIn() {
        return sharedPreferences.contains(KEY_EMAIL) && sharedPreferences.contains(KEY_PHONE);  // Kiểm tra cả email và phone
    }

    public String getLoggedInUserEmail() {
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    public String getLoggedInUserPhone() {
        return sharedPreferences.getString(KEY_PHONE, null);  // Lấy số điện thoại
    }

    public String getLoggedInUserFullName() {
        return sharedPreferences.getString(KEY_FULLNAME, null);
    }

    // Đặt lại mật khẩu qua số điện thoại
    public boolean resetPassword(String phone, String newPassword) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", newPassword);

        // Cập nhật mật khẩu theo số điện thoại
        int rowsUpdated = db.update("Users", values, "phone = ?", new String[]{phone});
        return rowsUpdated > 0; // Trả về true nếu cập nhật thành công
    }
}

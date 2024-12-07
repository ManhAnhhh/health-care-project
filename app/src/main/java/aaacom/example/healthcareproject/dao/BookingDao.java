package aaacom.example.healthcareproject.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import aaacom.example.healthcareproject.entities.Booking;
import aaacom.example.healthcareproject.utils.DatabaseUtils;

public class BookingDao {
    private static final String TAG = "BookingDao";
    private DatabaseUtils databaseUtils;

    public BookingDao(Context context) {
        databaseUtils = new DatabaseUtils(context);
    }

    // Phương thức thêm lịch khám bệnh vào cơ sở dữ liệu
    public void createBooking(Booking booking) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("DoctorId", booking.getDoctorId());
        values.put("UserId", booking.getUserId());
        values.put("Date", booking.getDate());
        values.put("Time", booking.getTime());

        long bookingId = db.insert("Booking", null, values); // "Booking" là tên bảng trong CSDL
        Log.i(TAG, "Booking created: new booking id: " + bookingId);
    }

    // Phương thức lấy tất cả lịch khám từ cơ sở dữ liệu
    public ArrayList<Booking> getBookings() {
        ArrayList<Booking> bookings = new ArrayList<>();
        SQLiteDatabase database = databaseUtils.getReadableDatabase();

        String sql = "SELECT * FROM Booking";
        Cursor cursor = database.rawQuery(sql, null);

        Log.i(TAG, "getBookings: " + cursor.getCount());

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                @SuppressLint("Range") int bookingId = cursor.getInt(cursor.getColumnIndex("BookingId"));
                @SuppressLint("Range") int doctorId = cursor.getInt(cursor.getColumnIndex("DoctorId"));
                @SuppressLint("Range") int userId = cursor.getInt(cursor.getColumnIndex("UserId"));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("Date"));
                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("Time"));

                bookings.add(new Booking(bookingId, doctorId, userId, date, time));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return bookings;
    }

    // Phương thức xóa lịch khám theo BookingId
    public void deleteBooking(int bookingId) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();

        String whereClause = "BookingId=?";
        String[] whereParams = {String.valueOf(bookingId)};

        int affectedRecordAmount = db.delete("Booking", whereClause, whereParams);
        Log.i(TAG, "Booking deleted: Affected record params: " + affectedRecordAmount);
    }

    // Phương thức cập nhật thông tin lịch khám
    public void updateBooking(Booking booking) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("DoctorId", booking.getDoctorId());
        values.put("UserId", booking.getUserId());
        values.put("Date", booking.getDate());
        values.put("Time", booking.getTime());

        String whereClause = "BookingId=?";
        String[] whereParams = {String.valueOf(booking.getBookingId())};

        int affectedRecordAmount = db.update("Booking", values, whereClause, whereParams);
        Log.i(TAG, "Booking updated: Affected record params: " + affectedRecordAmount);
    }
}

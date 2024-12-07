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

    // Phương thức kiểm tra xem đơn đặt lịch khám đã tồn tại
    public boolean isBookingExists(int doctorId, int userId, String date, String time) {
        SQLiteDatabase db = databaseUtils.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Câu lệnh SQL để kiểm tra xem lịch khám có tồn tại không
            String query = "SELECT COUNT(*) FROM Booking WHERE DoctorId = ? AND UserId = ? AND Date = ? AND Time = ?";
            String[] selectionArgs = {
                    String.valueOf(doctorId),
                    String.valueOf(userId),
                    date,
                    time
            };

            cursor = db.rawQuery(query, selectionArgs);

            if (cursor.moveToFirst()) {
                // Nếu COUNT(*) > 0, lịch đã tồn tại
                return cursor.getInt(0) > 0;
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Error checking booking existence: " + e.getMessage());
            return false;
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
    }


    public void createBooking(Booking booking) {
        SQLiteDatabase db = null;
        try {
            db = databaseUtils.getWritableDatabase();

            // Khởi tạo ContentValues để chèn dữ liệu vào bảng
            ContentValues values = new ContentValues();
            values.put("DoctorId", booking.getDoctorId());
            values.put("UserId", booking.getUserId());
            values.put("Date", booking.getDate());
            values.put("Time", booking.getTime());

            // Thực hiện lệnh insert để chèn dữ liệu vào bảng Booking
            long bookingId = db.insert("Booking", null, values);

            // Kiểm tra kết quả và ghi log
            if (bookingId == -1) {
                Log.e(TAG, "Failed to insert booking");
            } else {
                Log.i(TAG, "Booking created: new booking id: " + bookingId);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error creating booking: " + e.getMessage());
        } finally {
            if (db != null && db.isOpen()) {
                db.close();  // Đảm bảo đóng cơ sở dữ liệu sau khi sử dụng
            }
        }
    }



    public ArrayList<Booking> getBookings(boolean includeDoctorName) {
        ArrayList<Booking> bookings = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = databaseUtils.getReadableDatabase();

            String query = includeDoctorName
                    ? "SELECT Booking.*, Doctor.name, Doctor.hospital_address, Doctor.contact, Doctor.fee " +
                    "FROM Booking " +
                    "LEFT JOIN Doctor ON Booking.DoctorId = Doctor.Id" // JOIN giữa Booking và Doctor
                    : "SELECT * FROM Booking";

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    Booking booking = mapCursorToBooking(cursor);
                    bookings.add(booking);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving bookings: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }

        return bookings;
    }



    @SuppressLint("Range")
    private Booking mapCursorToBooking(Cursor cursor) {
        Booking booking = new Booking();

        // Ánh xạ các cột từ cursor vào đối tượng Booking
        booking.setBookingId(cursor.getInt(cursor.getColumnIndex("BookingId")));
        booking.setDoctorId(cursor.getInt(cursor.getColumnIndex("DoctorId")));
        booking.setUserId(cursor.getInt(cursor.getColumnIndex("UserId")));
        booking.setDate(cursor.getString(cursor.getColumnIndex("Date")));
        booking.setTime(cursor.getString(cursor.getColumnIndex("Time")));

        // Ánh xạ tên bác sĩ từ JOIN
        if (cursor.getColumnIndex("name") != -1) {
            String doctorName = cursor.getString(cursor.getColumnIndex("name"));
            // Sử dụng tên bác sĩ ở đây nếu cần
        }

        // Ánh xạ các thông tin khác từ bảng Doctor
        if (cursor.getColumnIndex("hospital_address") != -1) {
            String address = cursor.getString(cursor.getColumnIndex("hospital_address"));
            // Xử lý địa chỉ ở đây
        }

        if (cursor.getColumnIndex("contact") != -1) {
            String phoneNumber = cursor.getString(cursor.getColumnIndex("contact"));
            // Xử lý số điện thoại ở đây
        }

        if (cursor.getColumnIndex("fee") != -1) {
            double fee = cursor.getDouble(cursor.getColumnIndex("fee"));
            // Xử lý phí ở đây
        }

        return booking;
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

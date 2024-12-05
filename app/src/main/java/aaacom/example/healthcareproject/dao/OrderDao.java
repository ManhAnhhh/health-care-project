package aaacom.example.healthcareproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import aaacom.example.healthcareproject.entities.Medicine;
import aaacom.example.healthcareproject.entities.Order;
import aaacom.example.healthcareproject.utils.DatabaseUtils;

public class OrderDao {
    private static String TAG = "OrderDao";
    private DatabaseUtils databaseUtils;

    public OrderDao(Context context) {
        databaseUtils = new DatabaseUtils(context);
    }

    // Check if the medicine exists in the Medicine table
    public boolean doesMedicineExist(int medicin_id) {
        SQLiteDatabase db = databaseUtils.getReadableDatabase();
        String query = "SELECT id FROM Medicine WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(medicin_id)});

        boolean exists = cursor.getCount() > 0;
        cursor.close();

        // Log for debugging
        Log.d("CartDao", "Checking if medicine exists with id " + medicin_id + ": " + exists);

        return exists;
    }

    public ArrayList<Order> getOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        SQLiteDatabase database = databaseUtils.getReadableDatabase();

        String sql = "SELECT * FROM \"Order\"";
        Cursor cursor = database.rawQuery(sql, null);

        Log.i(TAG, "getOrders: " + cursor.getCount());

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int Id = cursor.getInt(0);
                int total_amount = cursor.getInt(1);
                String customer_name = cursor.getString(2);
                String order_date = cursor.getString(3);
                String order_place = cursor.getString(4);
                int medicin_id = cursor.getInt(5);
                int quantity = cursor.getInt(6);

                orders.add(new Order(quantity, medicin_id, order_place, order_date, customer_name, total_amount, Id));
                cursor.moveToNext();
            }
        }
        cursor.close();

        return orders;
    }

    // Create an order (checks if medicin_id exists)
    public void createOrder(Order order) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("total_amount", order.getTotal_amount());
        values.put("customer_name", order.getCustomer_name());
        values.put("order_date", order.getOrder_date());
        values.put("order_place", order.getOrder_place());
        values.put("medicin_id", order.getMedicin_id());
        values.put("quantity", order.getQuantity());
        values.put("status", order.getStatus());

        long orderId = db.insert("\"Order\"", null, values);
        if (orderId != -1) {
            Log.i(TAG, "Order created: new order id: " + orderId);
        } else {
            Log.e(TAG, "Failed to create order for medicine id: " + order.getMedicin_id());
        }
        Log.i(TAG, "Order created: new order id: " + orderId);

    }

    // Delete an order
    public void deleteOrder(int id) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();

        String whereClause = "id=?";
        String[] whereParams = {String.valueOf(id)};

        int affectedRecordAmount = db.delete("Order", whereClause, whereParams);

        Log.i(TAG, "Order deleted: Affected record params: " + affectedRecordAmount);
    }
}
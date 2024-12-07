package aaacom.example.healthcareproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import aaacom.example.healthcareproject.entities.Cart;
import aaacom.example.healthcareproject.utils.DatabaseUtils;

public class CartDao {
    private DatabaseUtils databaseUtils;

    public CartDao(Context context) {
        databaseUtils = new DatabaseUtils(context);
    }

    // Check if the medicine exists in the Medicine table
    public boolean doesMedicineExist(int medicin_id) {
        SQLiteDatabase db = databaseUtils.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM Medicine WHERE id = ?", new String[]{String.valueOf(medicin_id)});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public void addToCart(Cart cart) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Insert values for the Cart item
        values.put("customerName", cart.getCustomerName());
        values.put("productName", cart.getProductName());
        values.put("totalAmount", cart.getPrice());
        values.put("orderDate", cart.getOrderDate());
        values.put("orderPlace", cart.getOrderPlace());
        values.put("quantity", cart.getQuantity()); // Add the quantity to be inserted
        values.put("medicine_id", cart.getMedicine_id());

        // Query to check if the product already exists in the cart for the same customer
        String query = "SELECT id, quantity FROM Cart WHERE customerName = ? AND productName = ?";
        Cursor cursor = db.rawQuery(query, new String[]{cart.getCustomerName(), cart.getProductName()});

        if (cursor.moveToFirst()) {
            // Item already exists in the cart, update the quantity
            int existingQuantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
            int newQuantity = existingQuantity + cart.getQuantity(); // Add new quantity to the existing one

            ContentValues updateValues = new ContentValues();
            updateValues.put("quantity", newQuantity); // Update the quantity in the cart

            // Update the cart with the new quantity
            int affectedRows = db.update("Cart", updateValues, "id = ?", new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("id")))});
            Log.d("CartDao", "Updated quantity for cart item with ID: " + cursor.getInt(cursor.getColumnIndexOrThrow("id")) + " | Affected rows: " + affectedRows);
        } else {
            // Item doesn't exist, insert new item into the cart
            long rowId = db.insert("Cart", null, values);
            Log.d("CartDao", "Inserted new cart item with row ID: " + rowId);
        }

        cursor.close();
        db.close();
    }

    // Get all Cart items
    public ArrayList<Cart> getCartItems() {
        ArrayList<Cart> cartList = new ArrayList<>();
        SQLiteDatabase db = databaseUtils.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Cart", null);

        if (cursor.moveToFirst()) {
            do {
                Cart cart = new Cart();
                cart.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                cart.setCustomerName(cursor.getString(cursor.getColumnIndexOrThrow("customerName")));
                cart.setProductName(cursor.getString(cursor.getColumnIndexOrThrow("productName")));
                cart.setPrice(cursor.getFloat(cursor.getColumnIndexOrThrow("totalAmount")));
                cart.setOrderDate(cursor.getString(cursor.getColumnIndexOrThrow("orderDate")));
                cart.setOrderPlace(cursor.getString(cursor.getColumnIndexOrThrow("orderPlace")));
                cart.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow("quantity")));
                cart.setMedicine_id(cursor.getInt(cursor.getColumnIndexOrThrow("medicine_id")));

                cartList.add(cart);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cartList;
    }

    // Delete a cart item
    public void deleteCartItem(int id) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();
        db.delete("Cart", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Clear all cart items
    public void clearCart() {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();
        db.delete("Cart", null, null);
        db.close();
    }
}
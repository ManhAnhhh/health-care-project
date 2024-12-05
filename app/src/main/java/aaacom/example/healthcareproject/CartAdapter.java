package aaacom.example.healthcareproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import aaacom.example.healthcareproject.entities.Cart;

public class CartAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Cart> cartItems;  // List of Cart objects
    private ArrayList<Cart> selectedItems = new ArrayList<>(); // List of selected Cart items
    private OnSelectionChangedListener onSelectionChangedListener; // Listener to notify selection change

    // Constructor to initialize the context and cart items
    public CartAdapter(Context context, ArrayList<Cart> cartItems, OnSelectionChangedListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.onSelectionChangedListener = listener;
    }

    @Override
    public int getCount() {
        return cartItems.size(); // Return the size of the cartItems list
    }

    @Override
    public Object getItem(int position) {
        return cartItems.get(position); // Return the Cart item at the given position
    }

    @Override
    public long getItemId(int position) {
        return position; // Return the position as the item ID
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.cart_item_list_view, parent, false);
        } else {
            view = convertView;
        }

        // Find the views
        CheckBox checkBox = view.findViewById(R.id.checkbox);
        TextView productName = view.findViewById(R.id.line_a);
        TextView price = view.findViewById(R.id.line_b);
        TextView quantity = view.findViewById(R.id.line_d);

        Cart cart = cartItems.get(position);

        // Bind the Cart object's properties to the views
        productName.setText(cart.getProductName());
        price.setText("Price: $" + cart.getPrice());
        quantity.setText("Quantity: " + cart.getQuantity());

        // Handle the checkbox state change (selection/unselection)
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedItems.add(cart); // Add to selected items if checked
            } else {
                selectedItems.remove(cart); // Remove from selected items if unchecked
            }

            // Notify the activity that the selection has changed
            onSelectionChangedListener.onSelectionChanged(getSelectedItems());
        });

        // Set the checkbox checked state based on the selected items list
        checkBox.setChecked(selectedItems.contains(cart));

        return view;
    }

    // Method to get the selected items
    public ArrayList<Cart> getSelectedItems() {
        return selectedItems;
    }

    // Method to update the cart data (e.g., after modifying the cart)
    public void updateData(ArrayList<Cart> updatedCartItems) {
        this.cartItems = updatedCartItems;
        this.selectedItems.clear(); // Clear the selected items list as data has changed
        notifyDataSetChanged(); // Notify the adapter to refresh the ListView
    }

    // Interface to notify selection changes
    public interface OnSelectionChangedListener {
        void onSelectionChanged(ArrayList<Cart> selectedItems);
    }
}
package aaacom.example.healthcareproject.utils;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import aaacom.example.healthcareproject.CartBuyMedicineActivity;
import aaacom.example.healthcareproject.HomeActivity;
import aaacom.example.healthcareproject.MainActivity;
import aaacom.example.healthcareproject.R;

public class MenuEventUtil {
    public static boolean handleMenuEvent(MenuItem item, Context context) {
        int idSelected = item.getItemId();
        if (idSelected == R.id.mnuCard) {
            context.startActivity(new Intent(context, CartBuyMedicineActivity.class));
            return true;
        }
        else if (idSelected == R.id.mnuHome) {
            context.startActivity(new Intent(context, HomeActivity.class));
            return true;
        }
        return false;
    }
}

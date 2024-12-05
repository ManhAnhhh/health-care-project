package aaacom.example.healthcareproject;

import static java.lang.Integer.parseInt;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import aaacom.example.healthcareproject.dao.OrderDao;
import aaacom.example.healthcareproject.entities.Medicine;
import aaacom.example.healthcareproject.entities.Order;

public class OrderInforActivity extends AppCompatActivity {
    TextView txtvMedicinInfo, txtvTotalAmount;
    Button btnQuayLai, btnConfirm, btnAddQuantity, btnSubQuantity;
    EditText txtCustomerName, txtOrderPlace, txtQuantity;
    Medicine medicin = null;
    int totalAmount = 0;
    int quantity = 1;

    OrderDao orderDao;

    private static String TAG = "OrderInforActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_infor);

        orderDao = new OrderDao(this);

        getWidgets();

        Bundle b = getIntent().getBundleExtra("DATA");
        medicin = b.getParcelable(MainActivity.MEDICIN_DATA);

        if (medicin != null) {
            txtvMedicinInfo.setText(medicin.toString());
            quantity = 1;
            handleCheckTotalAmount(quantity);
        } else {
            txtvMedicinInfo.setText("Không có thông tin thuốc!");
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }

//        Event Button
        btnQuayLai.setOnClickListener(view -> {
            finish();
        });

        btnConfirm.setOnClickListener(view -> {
            handleOrder();
        });

        btnAddQuantity.setOnClickListener(v -> {
            handleChangeQuantity(1, true);
        });

        btnSubQuantity.setOnClickListener(v -> {
            handleChangeQuantity(1, false);
        });
//        End Event Button

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void handleCheckTotalAmount (int quantity) {
        totalAmount = (int) medicin.getPrice() * quantity;

        // Thiết lập định dạng cho Việt Nam
        Locale vietnam = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(vietnam);

        // Định dạng số tiền
        String formattedAmount = currencyFormatter.format(totalAmount);

        txtvTotalAmount.setText(String.valueOf(formattedAmount));
    }

    public void handleOrder() {
        try {
            // Lấy thời gian hiện tại
            LocalDateTime localDateTime = LocalDateTime.now();
            // Định dạng thời gian theo kiểu "yyyy-MM-dd HH:mm:ss"
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = localDateTime.format(formatter);

            String customerName = txtCustomerName.getText().toString();
            String orderPlace = txtOrderPlace.getText().toString();

            Order order = new Order(quantity, medicin.getId(), orderPlace, formattedDateTime, customerName, totalAmount);

            orderDao.createOrder(order);

            Toast.makeText(this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this, "Đặt hàng thất bại!", Toast.LENGTH_SHORT).show();
            Log.e(TAG, ex.getMessage());
        }
    }

    private void handleChangeQuantity(int add_value, boolean add_yn) {
        if (add_yn) {
            quantity += add_value;
        } else {
            quantity -= add_value;
        }

        if (quantity <= 0) {
            quantity = 1;
        }

        txtQuantity.setText(String.valueOf(quantity));

        handleCheckTotalAmount(quantity);
    }

    private void getWidgets() {
        txtvMedicinInfo = findViewById(R.id.txtv_MedicinInfo);
        txtvTotalAmount = findViewById(R.id.txtv_totalAmount);
        btnConfirm = findViewById(R.id.btn_Confirm);
        btnQuayLai = findViewById(R.id.btn_QuayLai);
        btnAddQuantity = findViewById(R.id.btn_AddQuantity);
        btnSubQuantity = findViewById(R.id.btnSubQuantity);
        txtQuantity = findViewById(R.id.txt_Quantity);
        txtQuantity.setText(String.valueOf(quantity));
        txtCustomerName = findViewById(R.id.txt_CustomerName);
        txtOrderPlace = findViewById(R.id.txt_OrderPlace);

        txtCustomerName.setText("TRần Văn Anh");
        txtOrderPlace.setText("Ở đâu cũng được!");
    }
}
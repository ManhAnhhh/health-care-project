package aaacom.example.healthcareproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import aaacom.example.healthcareproject.R;
import aaacom.example.healthcareproject.utils.MenuEventUtil;

public class ArticleDetailActivity extends AppCompatActivity {
    private TextView txtvArticleDetailTitle, txtvArticleDetailContent;
    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        // Setup Toolbar
        toolbar = findViewById(R.id.tbr_ArticleDetail);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        txtvArticleDetailTitle = findViewById(R.id.txtv_ArticleDetailTitle);
        txtvArticleDetailContent = findViewById(R.id.txtv_ArticleDetailContent);

        // Lấy dữ liệu từ Intent
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");

        // Hiển thị tiêu đề và nội dung
        txtvArticleDetailTitle.setText(title);
        txtvArticleDetailContent.setText(content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return MenuEventUtil.handleMenuEvent(item, this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Xử lý khi nhấn nút quay lại
        onBackPressed(); // Quay lại màn hình trước
        return true;
    }
}

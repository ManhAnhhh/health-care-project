package aaacom.example.healthcareproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import aaacom.example.healthcareproject.R;

public class ArticleDetailActivity extends AppCompatActivity {
    private TextView txtvArticleDetailTitle, txtvArticleDetailContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        txtvArticleDetailTitle = findViewById(R.id.txtv_ArticleDetailTitle);
        txtvArticleDetailContent = findViewById(R.id.txtv_ArticleDetailContent);

        // Lấy dữ liệu từ Intent
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");

        // Hiển thị tiêu đề và nội dung
        txtvArticleDetailTitle.setText(title);
        txtvArticleDetailContent.setText(content);

        Button btnBackB = findViewById(R.id.btn_BackB);
        btnBackB.setOnClickListener(v -> {
           finish();
        });
    }
}

package aaacom.example.healthcareproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

import aaacom.example.healthcareproject.ArticleAdapter;
import aaacom.example.healthcareproject.R;
import aaacom.example.healthcareproject.dao.ArticleDao;
import aaacom.example.healthcareproject.entities.Article;
import aaacom.example.healthcareproject.utils.MenuEventUtil;

public class ArticleListActivity extends AppCompatActivity {
    private ListView lvArticles;
    private ArticleDao articleDao;
    private ArticleAdapter articleAdapter;
    private ArrayList<Article> articles;
    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        // Setup Toolbar
        toolbar = findViewById(R.id.tbr_ListArticle);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        lvArticles = findViewById(R.id.lvArticles);
        articleDao = new ArticleDao(this);

        articles = articleDao.getAllArticles();
        articleAdapter = new ArticleAdapter(this, R.layout.item_article, articles);

        lvArticles.setAdapter(articleAdapter);

        // Bắt sự kiện khi nhấn vào một bài viết
        lvArticles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy bài viết được chọn
                Article selectedArticle = articles.get(position);

                // Tạo Intent để chuyển sang ArticleDetailActivity
                Intent intent = new Intent(ArticleListActivity.this, ArticleDetailActivity.class);

                // Truyền tiêu đề và nội dung qua Intent
                intent.putExtra("title", selectedArticle.getTitle());
                intent.putExtra("content", selectedArticle.getContent());

                // Chuyển sang ArticleDetailActivity
                startActivity(intent);
            }
        });
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

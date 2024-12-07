package aaacom.example.healthcareproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import aaacom.example.healthcareproject.ArticleAdapter;
import aaacom.example.healthcareproject.R;
import aaacom.example.healthcareproject.dao.ArticleDao;
import aaacom.example.healthcareproject.entities.Article;

public class ArticleListActivity extends AppCompatActivity {
    private ListView lvArticles;
    private ArticleDao articleDao;
    private ArticleAdapter articleAdapter;
    private ArrayList<Article> articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        lvArticles = findViewById(R.id.lvArticles);
        articleDao = new ArticleDao(this);

        articles = articleDao.getAllArticles();
        articleAdapter = new ArticleAdapter(this, R.layout.item_article, articles);

        lvArticles.setAdapter(articleAdapter);

        Button btnBackA = findViewById(R.id.btn_BackA);
        btnBackA.setOnClickListener(v -> {
           finish();
        });
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
}

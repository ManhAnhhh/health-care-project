package aaacom.example.healthcareproject.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import aaacom.example.healthcareproject.entities.Article;
import aaacom.example.healthcareproject.utils.DatabaseUtils;

public class ArticleDao {
    private DatabaseUtils databaseUtils;

    public ArticleDao(Context context) {
        databaseUtils = new DatabaseUtils(context);
    }

    public ArrayList<Article> getAllArticles() {
        ArrayList<Article> articles = new ArrayList<>();
        SQLiteDatabase db = databaseUtils.getReadableDatabase();

        String query = "SELECT * FROM Article";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String content = cursor.getString(2);

                articles.add(new Article(id, title, content));
                cursor.moveToNext();
            }
            cursor.close();
        }

        return articles;
    }
}

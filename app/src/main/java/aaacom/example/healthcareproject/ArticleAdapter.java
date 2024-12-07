package aaacom.example.healthcareproject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import aaacom.example.healthcareproject.entities.Article;

public class ArticleAdapter extends ArrayAdapter<Article> {
    private final Activity context;
    private final int resourceId;
    private final ArrayList<Article> articles;

    public ArticleAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<Article> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourceId = resource;
        this.articles = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(resourceId, null);

        TextView txtvTitle = convertView.findViewById(R.id.txtv_ArticleTitle);
        //TextView txtvContent = convertView.findViewById(R.id.txtv_ArticleContent);

        Article article = articles.get(position);
        txtvTitle.setText(article.getTitle());
        //txtvContent.setText(article.getContent());

        return convertView;
    }
}

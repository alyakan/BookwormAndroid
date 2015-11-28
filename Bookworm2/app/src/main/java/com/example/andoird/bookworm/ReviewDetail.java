package com.example.andoird.bookworm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReviewDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        populateReviewDetailPosts();
        populateDetailView();
    }


    protected void populateReviewDetailPosts() {
        String[] data = {
                "I agree",
                "Is the author of it dead ?",
                "Gal you're out of your mind",
                "I agree, you should read next sequel",
                "Rock Paper Rock",
                "Hahaha your comment gave me cancer",
                "Agreed",
        };

        List<String> posts_ArrayList = new ArrayList<String>(Arrays.asList(data));
        final ArrayAdapter<String> postsAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item_reviews, // The xml component
                R.id.list_item_reviews_text_view, // The textview inside the xml component
                posts_ArrayList
        );
        ListView listview = (ListView) this.findViewById(R.id.listview_posts);
        listview.setAdapter(postsAdapter);


    }


    protected void populateDetailView() {
        Intent intent = this.getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            String review = intent.getStringExtra(Intent.EXTRA_TEXT);
            TextView review_text = (TextView) this.findViewById(R.id.review_detail_text);
            review_text.setText(review);
            setTitle(review);
        }
    }

}

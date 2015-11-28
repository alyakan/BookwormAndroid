package com.example.andoird.bookworm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        populateDetailView();
    }


    protected void populateDetailView(){
        Intent intent = this.getIntent();
        if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
            String postStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            TextView detail_text = (TextView) this.findViewById(R.id.post_detail_text);
            detail_text.setText(postStr);
        }

        String[] data = {
                "Great Book",
                "Awesome Book",

        };
        List<String> comment_ArrayList = new ArrayList<String>(Arrays.asList(data));
        final ArrayAdapter<String> commentAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item_comment, // The xml component
                R.id.list_item_comment_text_view, // The textview inside the xml component
                comment_ArrayList
        );
        PostDetail commentlist = this;
        ListView listview = (ListView) this.findViewById(R.id.listview_comment);
        listview.setAdapter(commentAdapter);
    }
}

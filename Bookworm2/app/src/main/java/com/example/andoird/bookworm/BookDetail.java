package com.example.andoird.bookworm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookDetail extends AppCompatActivity {

    Button button;
    Button button_2;
    String buttonText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateDetailView();
        populateBookDetailReviews();
        addListenerOnButton();

    }

    protected void populateDetailView() {
        Intent intent = this.getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            String book_name = intent.getStringExtra(Intent.EXTRA_TEXT);
            String id = intent.getStringExtra(Intent.EXTRA_ASSIST_CONTEXT);
            TextView detail_text = (TextView) this.findViewById(R.id.book_detail_text);
            detail_text.setText("Book title: " + book_name);
            TextView author_text = (TextView) this.findViewById(R.id.author_detail_text);
            author_text.setText("ID: " + id);
            setTitle(book_name);
        }
    }

    protected void populateBookDetailReviews() {
        String[] data = {
                "This book is awesome",
                "This book is great",
                "This book is beautiful",
                "This book is magnificent",
                "This book is awesome",
                "This book is great",
                "This book is beautiful",
                "This book is magnificent",
                "This book is depressing",
                "This book is good",

        };

        List<String> reviews_ArrayList = new ArrayList<String>(Arrays.asList(data));
        final ArrayAdapter<String> reviewsAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item_reviews, // The xml component
                R.id.list_item_reviews_text_view, // The textview inside the xml component
                reviews_ArrayList
        );
        ListView listview = (ListView) this.findViewById(R.id.listview_reviews);
        listview.setAdapter(reviewsAdapter);
        button = (Button) findViewById(R.id.finish_reading_button);
        button.setText(String.format("Start Reading"));
        final BookDetail reviewlist = this;
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = reviewsAdapter.getItem(i);
                Intent intent = new Intent(reviewlist, ReviewDetail.class).
                        putExtra(Intent.EXTRA_TEXT, item);
                startActivity(intent);

            }
        });

    }

    public void addListenerOnButton() {

        button = (Button) findViewById(R.id.finish_reading_button);

        button_2  =(Button) findViewById(R.id.read_later_button);

        buttonText = button.getText().toString();


        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (buttonText.equals("Start Reading")) {
                    button.setText(String.format("Finish Reading"));
                    button_2.setVisibility(View.GONE);
                }

                if(buttonText.equals("Finish Reading")){
                    button.setText(String.format("Start Reading"));
                }

            }
        });

    }

}





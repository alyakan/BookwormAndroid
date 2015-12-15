package com.example.andoird.bookworm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyBookList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book_list);
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

        Intent intent = this.getIntent();
        populateBookCategoryList(intent);
    }

    protected void populateBookCategoryList(Intent intent){
        MyBookList myBookList = this;

        if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){

            String category = intent.getStringExtra(Intent.EXTRA_TEXT);
            //Log.v("intent", category);
            if(category.contains("To read later")){
                populateReadLater();
                //Log.v("success", "READ LATER");
            }
            if(category.contains("Now reading")){
                populateReadingNow();
            }
            if(category.contains("Finished reading")){
                populateFinishedReading();
            }

        }


    }

    void populateReadLater(){
        setTitle("Read Later");
        MyBookList myBookList = this;
        String[] data = {
                "Harry Potter 8",
                "Hunger Games 2",
                "Dark Places",

        };
        List<String> categories_ArrayList = new ArrayList<String>(Arrays.asList(data));
        final ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item_my_category, // The xml component
                R.id.list_item_my_category_text_view, // The textview inside the xml component
                categories_ArrayList
        );
        ListView listview = (ListView) this.findViewById(R.id.listview_my_books);
        listview.setAdapter(categoryAdapter);
    }

    void populateReadingNow(){}

    void populateFinishedReading(){}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_newsfeed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_booklist){
            Intent intent = new Intent(this, BookList.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}

package com.example.andoird.bookworm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyBookListCategories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book_list_categories);
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

        populateBookCategoryList();
    }

    protected void populateBookCategoryList(){
        String[] data = {
                "To read later (3)",
                "Now reading (4)",
                "Finished reading (8)",

        };
        List<String> categories_ArrayList = new ArrayList<String>(Arrays.asList(data));
        final ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item_my_category, // The xml component
                R.id.list_item_my_category_text_view, // The textview inside the xml component
                categories_ArrayList
        );

        MyBookListCategories booklistcategory = this;
        ListView listview = (ListView) this.findViewById(R.id.listview_my_books);
        listview.setAdapter(categoryAdapter);
//        //listview.setAdapter(authorAdapter);
        final MyBookListCategories bookListCategories = this;
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = categoryAdapter.getItem(i);
                Log.v("item", item);
                Intent intent = new Intent(bookListCategories, MyBookList.class).
                        putExtra(Intent.EXTRA_TEXT, item);
                startActivity(intent);

            }
        });


    }

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

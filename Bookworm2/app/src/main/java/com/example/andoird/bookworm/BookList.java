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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
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
        populateBookList();
    }

    protected void populateBookList(){
        String[] data = {
                "Harry Potter 1",
                "Harry Potter 2",
                "Harry Potter 3",
                "Harry Potter 4",
                "Harry Potter 5",
                "Harry Potter 6",
                "Harry Potter 7",
                "Harry Potter 8",
                "Hunger Games 1",
                "Hunger Games 2",
                "Gone Girl",
                "Dark Places",
                "Sharp Objects"

        };
        String[] data2 = {
                "J. K. Rowling",
                "J. K. Rowling",
                "J. K. Rowling",
                "J. K. Rowling",
                "J. K. Rowling",
                "J. K. Rowling",
                "J. K. Rowling",
                "J. K. Rowling",
                "Suzanne Collins",
                "Suzanne Collins",
                "Gillian Flynn",
                "Gillian Flynn",
                "Gillian Flynn"

        };
        List<String> book_ArrayList = new ArrayList<String>(Arrays.asList(data));
        List<String> author_ArrayList = new ArrayList<String>(Arrays.asList(data));
        final ArrayAdapter<String> bookAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item_book, // The xml component
                R.id.list_item_book_text_view, // The textview inside the xml component
                book_ArrayList
        );

        final ArrayAdapter<String> authorAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item_book, // The xml component
                R.id.list_item_book_author_text_view, // The textview inside the xml component
                author_ArrayList
        );
        BookList booklist = this;
        ListView listview = (ListView) this.findViewById(R.id.listview_book);
        listview.setAdapter(bookAdapter);
        //listview.setAdapter(authorAdapter);
        final BookList bookList = this;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = bookAdapter.getItem(i);

                Intent intent = new Intent(bookList, BookDetail.class).
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

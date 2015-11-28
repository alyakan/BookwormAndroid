package com.example.andoird.bookworm;
import com.facebook.FacebookSdk;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewsFeed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_newsfeed);
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
        populateNewsfeed();
    }

    protected void populateNewsfeed(){
        String[] data = {
                "Aly is now reading Harry Potter 1",
                "Rana is now reading Harry Potter 2",
                "Kareem is now reading Harry Potter 3",
                "Mohamed is now reading Harry Potter 4",
                "Joe is now reading Harry Potter 5",
                "Sara is now reading Harry Potter 6",
                "Ahmed is now reading Harry Potter 7",
                "Aly is now reading Harry Potter 8",
                "Aly is now reading Hunger Games 1",
                "Aly is now reading Hunger Games 2"

        };
        List<String> newsfeed_ArrayList = new ArrayList<String>(Arrays.asList(data));
        final ArrayAdapter<String> newsfeedAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item_newsfeed, // The xml component
                R.id.list_item_newsfeed_text_view, // The textview inside the xml component
                newsfeed_ArrayList
        );

        ListView listview = (ListView) this.findViewById(R.id.listview_newsfeed);
        listview.setAdapter(newsfeedAdapter);
        final NewsFeed newsFeed = this;
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = newsfeedAdapter.getItem(i);

                Intent intent = new Intent(newsFeed, PostDetail.class).
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

        if(id == R.id.action_friendlist){
            Intent intent = new Intent(this, FriendList.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.example.andoird.bookworm;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
        String string = intent.getStringExtra(Intent.EXTRA_TEXT);
        String name = "";
        if(string.contains("friend")) {
            name = string.split(",")[1];
            ((TextView)findViewById(R.id.username_textview)).setText(name);
            populateFriendNewsfeed(name);
        }else{
            name = "Aly Yakan";
            ((TextView)findViewById(R.id.username_textview)).setText(name);
            populateNewsfeed();
        }

        populateProfileInfo(name);

    }

    protected  void populateFriendNewsfeed(String name){
        String[] data = {
                name + " has finished reading Harry Potter 1",
                name + " has finished reading Harry Potter 2",
                name + " has finished reading Harry Potter 3",
                name + " has finished reading Harry Potter 4",
                name + " has finished reading Harry Potter 5",
                name + " has finished reading Harry Potter 6",
                name + " has started reading Harry Potter 7",
                name + " has started reading Harry Potter 8",
                name + " has started reading Hunger Games 1",
                name + " has added Hunger Games 2 to your wish list"

        };
        List<String> newsfeed_ArrayList = new ArrayList<String>(Arrays.asList(data));
        final ArrayAdapter<String> newsfeedAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item_newsfeed, // The xml component
                R.id.list_item_newsfeed_text_view, // The textview inside the xml component
                newsfeed_ArrayList
        );

        ListView listview = (ListView) this.findViewById(R.id.profile_listView);

        listview.setAdapter(newsfeedAdapter);
        final Profile profile = this;
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = newsfeedAdapter.getItem(i);
                int duration = Toast.LENGTH_SHORT;
//                Intent intent = new Intent(this, DetailActivity.class).
//                        putExtra(Intent.EXTRA_TEXT, forecast);
//                startActivity(intent);
                Toast toast = Toast.makeText(profile, item, duration);
                toast.show();
            }
        });
    }

    protected void populateNewsfeed(){
        String[] data = {
                "You have finished reading Harry Potter 1",
                "You have finished reading Harry Potter 2",
                "You have finished reading Harry Potter 3",
                "You have finished reading Harry Potter 4",
                "You have finished reading Harry Potter 5",
                "You have finished reading Harry Potter 6",
                "You have started reading Harry Potter 7",
                "You have started reading Harry Potter 8",
                "You have started reading Hunger Games 1",
                "You have added Hunger Games 2 to your wish list"

        };
        List<String> newsfeed_ArrayList = new ArrayList<String>(Arrays.asList(data));
        final ArrayAdapter<String> newsfeedAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item_newsfeed, // The xml component
                R.id.list_item_newsfeed_text_view, // The textview inside the xml component
                newsfeed_ArrayList
        );

        ListView listview = (ListView) this.findViewById(R.id.profile_listView);

        listview.setAdapter(newsfeedAdapter);
        final Profile profile = this;
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = newsfeedAdapter.getItem(i);
                int duration = Toast.LENGTH_SHORT;
//                Intent intent = new Intent(this, DetailActivity.class).
//                        putExtra(Intent.EXTRA_TEXT, forecast);
//                startActivity(intent);
                Toast toast = Toast.makeText(profile, item, duration);
                toast.show();
            }
        });

    }

    public void populateProfileInfo(String profileName){
        String [] data = {
                "Birthday",
                "Email",
                "Gender"
        };
        // Adding child data
        List<String> name = new ArrayList<String>();
        name.add("10/8/1994");
        name.add(profileName + "@gmail.com");
        name.add("Male");

        List<String> profile_info = new ArrayList<String>();
        profile_info.add("Personal Info");


        HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();
        listDataChild.put(profile_info.get(0), name); // Header, Child data

        com.example.andoird.bookworm.ExpandableListAdapter expandableListAdapter = new com.example.andoird.bookworm.ExpandableListAdapter(this, profile_info, listDataChild);

        ExpandableListView expandableListView = (ExpandableListView) this.findViewById(R.id.lvExp);
        expandableListView.setAdapter(expandableListAdapter);
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

        if(id == R.id.action_my_booklist){
            Intent intent = new Intent(this, MyBookListCategories.class);
            startActivity(intent);
        }

        if(id == R.id.action_newsfeed){
            Intent intent = new Intent(this, NewsFeed.class);
            startActivity(intent);
        }

        if(id == R.id.action_friendlist){
            Intent intent = new Intent(this, FriendList.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}

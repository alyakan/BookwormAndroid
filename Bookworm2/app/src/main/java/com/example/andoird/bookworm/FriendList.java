package com.example.andoird.bookworm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FriendList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
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
        populateFriendList();
    }

    protected void populateFriendList(){
        String[] data = {
                "Aly Yakan",
                "Menna El-kashef",
                "Kareem Tarek",
                "Moustafa Mahmoud",
                "Rana El-Garem",
                "Salma Shoukry",
                "Heba Ashraf",
                "Mariam Jarkas",
                "Nadine Samir",
                "Ahmed Etefy",
                "Gihan Said"

        };
        List<String> friend_ArrayList = new ArrayList<String>(Arrays.asList(data));
        final ArrayAdapter<String> friendAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item_friend, // The xml component
                R.id.list_item_friend_text_view, // The textview inside the xml component
                friend_ArrayList
        );
        final FriendList friendList = this;
        ListView listview = (ListView) this.findViewById(R.id.listview_friend);
        listview.setAdapter(friendAdapter);

//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String item = bookAdapter.getItem(i);
//
//                Intent intent = new Intent(friendList, BookDetail.class).
//                        putExtra(Intent.EXTRA_TEXT, item);
//                startActivity(intent);
//
//            }
//        });


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

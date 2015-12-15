package com.example.andoird.bookworm;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Profile extends AppCompatActivity {
    private boolean followRequest;

    private int ID;
    private String first_name;
    private String last_name;
    private String city;
    private String country;
    private String date_of_birth;
    private String gender;
    private String email;

    private ArrayAdapter<String> newsfeedAdapter;

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
        String name = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (intent.hasExtra(Intent.EXTRA_REFERRER_NAME)) { // Coming from follow request list
            followRequest = true;
            ID = Integer.parseInt(intent.getStringExtra(Intent.EXTRA_ASSIST_CONTEXT));
            populateFriendNewsfeed(name);
            ((TextView) findViewById(R.id.username_textview)).setText(name);
            getProfileInfo();

        } else {
            String string = null;
            if(intent.hasExtra(Intent.EXTRA_ASSIST_CONTEXT)) {
                string = intent.getStringExtra(Intent.EXTRA_ASSIST_CONTEXT);
            }
            ((TextView) findViewById(R.id.username_textview)).setText(name);
            if (string != null && string.contains("friend")) { // Coming from friends list
                name = string.split(",")[1];
                ((TextView) findViewById(R.id.username_textview)).setText(name);
                populateFriendNewsfeed(name);

            } else {
                populateNewsfeed();
                getMyProfileInfo();
            }
        }

//        ((TextView)findViewById(R.id.username_textview)).setText(name);
//        if(string.contains("friend")) {
//            name = string.split(",")[1];
//            ((TextView)findViewById(R.id.username_textview)).setText(name);
//            populateFriendNewsfeed(name);
//        }else{
//            name = "Aly Yakan";
//            ((TextView)findViewById(R.id.username_textview)).setText(name);
//            populateNewsfeed();
//        }

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
        this.newsfeedAdapter = new ArrayAdapter<String>(
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
//                "You have finished reading Harry Potter 1",
//                "You have finished reading Harry Potter 2",
//                "You have finished reading Harry Potter 3",
//                "You have finished reading Harry Potter 4",
//                "You have finished reading Harry Potter 5",
//                "You have finished reading Harry Potter 6",
//                "You have started reading Harry Potter 7",
//                "You have started reading Harry Potter 8",
//                "You have started reading Hunger Games 1",
//                "You have added Hunger Games 2 to your wish list"

        };

        List<String> newsfeed_ArrayList = new ArrayList<String>(Arrays.asList(data));
        newsfeedAdapter = new ArrayAdapter<String>(
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
                "Gender",
                "City",
                "Country"
        };
        // Adding child data
        List<String> info = new ArrayList<String>();
        info.add(date_of_birth);
        info.add(email);
        info.add(gender);
        info.add(city);
        info.add(country);
        ((TextView) findViewById(R.id.username_textview)).setText(first_name + " " + last_name);
        List<String> profile_info = new ArrayList<String>();
        profile_info.add("Personal Info");


        HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();
        listDataChild.put(profile_info.get(0), info); // Header, Child data

        com.example.andoird.bookworm.ExpandableListAdapter expandableListAdapter = new com.example.andoird.bookworm.ExpandableListAdapter(this, profile_info, listDataChild);

        ExpandableListView expandableListView = (ExpandableListView) this.findViewById(R.id.lvExp);
        expandableListView.setAdapter(expandableListAdapter);
        getRecentUpdates();
    }

    private void getMyProfileInfo() {
        String url = "https://bookworm-alyakan.c9users.io/get_current_user.json";

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            ID = jsonObject.getInt("id");
                            first_name = jsonObject.getString("first_name");
                            last_name = jsonObject.getString("last_name");
                            email = jsonObject.getString("email");
                            date_of_birth = jsonObject.getString("date_of_birth");
                            city = jsonObject.getString("city");
                            country = jsonObject.getString("country");
                            gender = jsonObject.getString("gender");
                            Log.d("PROFILE", "--------------------------------");
                            Log.d("PROFILE", ID+"");
                            Log.d("PROFILE", first_name);
                            Log.d("PROFILE", last_name);
                            Log.d("PROFILE", city);
                            Log.d("PROFILE", email);
                            Log.d("PROFILE", date_of_birth);
                            Log.d("PROFILE", gender);
                            populateProfileInfo(first_name);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        Volley.newRequestQueue(this).add(getRequest);
    }

    /*
    * Gets Profile Info for a user other than myself*/
    private void getProfileInfo(){
        String url = "https://bookworm-alyakan.c9users.io/users/" + ID + ".json";

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            first_name = jsonObject.getString("first_name");
                            last_name = jsonObject.getString("last_name");
                            email = jsonObject.getString("email");
                            date_of_birth = jsonObject.getString("date_of_birth");
                            city = jsonObject.getString("city");
                            country = jsonObject.getString("country");
                            gender = jsonObject.getString("gender");
                            Log.d("PROFILE", "--------------------------------");
                            Log.d("PROFILE", first_name);
                            Log.d("PROFILE", last_name);
                            Log.d("PROFILE", city);
                            Log.d("PROFILE", email);
                            Log.d("PROFILE", date_of_birth);
                            Log.d("PROFILE", gender);
                            populateProfileInfo(first_name);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        Volley.newRequestQueue(this).add(getRequest);
    }

    private void getRecentUpdates(){
        String url = "https://bookworm-alyakan.c9users.io/recent_updates/" + ID + ".json";

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                             populateListWithResponse(response);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        Volley.newRequestQueue(this).add(getRequest);
    }
    private void populateListWithResponse(String response){
        String []contents;

        try{
            JSONArray jsonResponse = new JSONArray((response));
            contents = new String[jsonResponse.length()];
//            userIDs = new int[jsonResponse.length()];
            for (int i = 0; i < jsonResponse.length(); i++){
                String content = jsonResponse.getJSONObject(i).getString("content") + "\n"
                        + jsonResponse.getJSONObject(i).getString("created_at");

//                userIDs[i] = Integer.parseInt(jsonSender.getString("id"));
                contents[i] = content;
            }
            if(contents != null) {
                newsfeedAdapter.clear();
                newsfeedAdapter.addAll(contents);
            }
        } catch(JSONException e){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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

        if(id == R.id.action_follow_requests){
            Intent intent = new Intent(this, FollowRequests.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}

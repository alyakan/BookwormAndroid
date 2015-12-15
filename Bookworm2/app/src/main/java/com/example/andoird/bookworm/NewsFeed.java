package com.example.andoird.bookworm;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewsFeed extends AppCompatActivity {
    private ArrayAdapter<String> newsfeedAdapter;


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
        String[] data = {};
        List<String> newsfeed_ArrayList = new ArrayList<String>(Arrays.asList(data));
        newsfeedAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item_newsfeed, // The xml component
                R.id.list_item_newsfeed_text_view, // The textview inside the xml component
                newsfeed_ArrayList
        );

        ListView listview = (ListView) this.findViewById(R.id.listview_newsfeed);
        listview.setAdapter(newsfeedAdapter);
        new FetchNewsFeedTask().execute();
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

        if(id == R.id.action_verification){
            Intent intent = new Intent(this,Verification.class);
            startActivity(intent);
        }

        if(id == R.id.action_friendlist){
            Intent intent = new Intent(this, FriendList.class);
            startActivity(intent);
        }

        if(id == R.id.action_my_booklist){
            Intent intent = new Intent(this, MyBookListCategories.class);
            startActivity(intent);
        }

        if(id == R.id.action_profile){
            Intent intent = new Intent(this, Profile.class);
            startActivity(intent);
        }
        
        return super.onOptionsItemSelected(item);
    }

    public class FetchNewsFeedTask extends AsyncTask<Void, Void, String[]> {
        private final String LOG_TAG = FetchNewsFeedTask.class.getSimpleName();

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onPostExecute(String[] strings) {
            // Waits for doInBackground to return the array of strings that will update
            // the adapter


            if(strings != null) {

                newsfeedAdapter.clear();
                newsfeedAdapter.addAll(strings);
            }
        }

        @Override
        protected String[] doInBackground(Void... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String newsfeedJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast



                final String NEWSFEED_BASE_URL = "https://bookworm-alyakan.c9users.io/newsfeed.json";

               // final String USER_CREATE_BASE_URL = "https://bookworm-alyakan.c9users.io/users/new";
                //URL url2 = new URL(USER_CREATE_BASE_URL);
               // HttpURLConnection urlConnection1 = (HttpURLConnection) url2.openConnection();

                //urlConnection1.setRequestMethod("POST");
 //               urlConnection.connect();
//                {"utf8"=>"✓",
//                        "authenticity_token"=>"RfJIoHs3Ap9z5AJCraU5aa2wmR66vHTbKA3NqhpZb1BjbZHp3JvkZifuHAEcrhhRjYMo1AxSWEjZTSYobhdqYg==",
//                        "user"=>{"first_name"=>"",
//                        "last_name"=>"",
//                        "email"=>"",
//                        "password"=>"[FILTERED]",
//                        "password_confirmation"=>"[FILTERED]",
//                        "gender"=>"",
//                        "city"=>"",
//                        "country"=>"",
//                        "fbtoken"=>""},
//                    "commit"=>"Create my account"}

                Uri builtUri = Uri.parse(NEWSFEED_BASE_URL).buildUpon()
                        .build();

                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Built URI" + url);

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                URL url2 = new URL("https://bookworm-alyakan.c9users.io/generate.json");
                HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                newsfeedJsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                        return null;
                    }
                }
            }
            try{
                String[] NewsFeedDetails = getNewsFeedDataFromJson(newsfeedJsonStr);
                return NewsFeedDetails;
            }catch(JSONException e){
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
                return null;
            }


        }

        /*
        * Take JSON String representing the book list retrieved from the API, convert it
        * into a JSON Array and extract the information to populate the booklistAdapter
        * */
        private String[] getNewsFeedDataFromJson(String newsfeedJsonStr)
                throws JSONException {


            final String OWM_ID = "id";
            final String OWM_CONTENT = "content";
            final String OWM_CREATED_AT = "created_at";



            JSONArray newsfeedArray = new JSONArray(newsfeedJsonStr);
           // bookIDs = new ArrayList<Integer>();

            String[] resultStrs = new String[newsfeedArray.length()];
            for(int i = 0; i < newsfeedArray.length(); i++) {
                String content;
                String created_at;
                int ID;
                try{
                    Log.v(LOG_TAG, "------------------------------------");
                    content = newsfeedArray.getJSONObject(i).getString(OWM_CONTENT);
                    created_at = newsfeedArray.getJSONObject(i).getString(OWM_CREATED_AT);
                    ID = Integer.parseInt(newsfeedArray.getJSONObject(i).getString(OWM_ID));

                    resultStrs[i] = content;

                    Log.v(LOG_TAG, content);
                    Log.v(LOG_TAG, created_at);
                    Log.v(LOG_TAG, ID+"");
                }catch(Exception e){
                    Log.e(LOG_TAG, e.getMessage());
                }

            }


            return resultStrs;

        }
    }

}




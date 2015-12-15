package com.example.andoird.bookworm;

import android.annotation.TargetApi;
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

public class MyBookListCategories extends AppCompatActivity {
    private ArrayAdapter<String> bookAdapter;


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
        String[] data = { };
        List<String> categories_ArrayList = new ArrayList<String>(Arrays.asList(data));
        bookAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item_my_category, // The xml component
                R.id.list_item_my_category_text_view, // The textview inside the xml component
                categories_ArrayList
        );

        //MyBookListCategories booklistcategory = this;
        ListView listview = (ListView) this.findViewById(R.id.listview_my_books);
        listview.setAdapter(bookAdapter);
//        //listview.setAdapter(authorAdapter);
        new FetchMyBookListTask().execute();
        final MyBookListCategories bookListCategories = this;

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
/*
        if(id == R.id.action_booklist){
            Intent intent = new Intent(this, BookList.class);
            startActivity(intent);
        }*/

        return super.onOptionsItemSelected(item);
    }

    public class FetchMyBookListTask extends AsyncTask<Void, Void, String[]> {
        private final String LOG_TAG = FetchMyBookListTask.class.getSimpleName();

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onPostExecute(String[] strings) {
            // Waits for doInBackground to return the array of strings that will update
            // the adapter


            if(strings != null) {

                bookAdapter.clear();
                bookAdapter.addAll(strings);
            }
        }

        @Override
        protected String[] doInBackground(Void... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String booklistJsonStr = null;

            try {


                final String BookList_BASE_URL = "https://bookworm-alyakan.c9users.io/my_booklist.json";


                Uri builtUri = Uri.parse(BookList_BASE_URL).buildUpon()
                        .build();

                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Built URI" + url);

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

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
                booklistJsonStr = buffer.toString();

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
                String[] BookListDetails = getBookListDataFromJson(booklistJsonStr);
                return BookListDetails;
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
        private String[] getBookListDataFromJson(String booklistJsonStr)
                throws JSONException {


            final String OWM_ID = "id";
            final String OWM_TITLE= "title";
            final String OWM_STATUS = "status";



            JSONArray booklistArray = new JSONArray(booklistJsonStr);
            // bookIDs = new ArrayList<Integer>();

            String[] resultStrs = new String[booklistArray.length()];
            for(int i = 0; i < booklistArray.length(); i++) {
                String title;
                String status;
                int ID;
                try{
                    Log.v(LOG_TAG, "------------------------------------");
                    title = booklistArray.getJSONObject(i).getString(OWM_TITLE);
                    status = booklistArray.getJSONObject(i).getString(OWM_STATUS);
                    ID = Integer.parseInt(booklistArray.getJSONObject(i).getString(OWM_ID));

                    resultStrs[i] = title;

                    Log.v(LOG_TAG, title);
                    Log.v(LOG_TAG, ID+"");
                }catch(Exception e){
                    Log.e(LOG_TAG, e.getMessage());
                }

            }


            return resultStrs;

        }
    }
}

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
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.andoird.bookworm.model.BookPage;
import com.example.andoird.bookworm.model.User;
import com.example.andoird.bookworm.util.PublicApiRoutes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public class BookList extends AppCompatActivity {
    private ArrayAdapter<String> bookAdapter;
    protected ArrayList<Integer> bookIDs; // Stores ID of each book retrieved from API so we can send it with the intent
    private final String LOG_TAG = BookList.class.getSimpleName();
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
        List<String> author_ArrayList = new ArrayList<String>(Arrays.asList(data2));
        bookAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item_book, // The xml component
                R.id.list_item_book_text_view, // The textview inside the xml component
                book_ArrayList
        );
        final ArrayAdapter<String> authorAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item_book, // The xml component
                R.id.list_item_book_text_view, // The textview inside the xml component
                author_ArrayList
        );
        BookList booklist = this;
        ListView listview = (ListView) this.findViewById(R.id.listview_book);
        listview.setAdapter(bookAdapter);


        new FetchBooksTask().execute();
        final BookList bookList = this;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = bookAdapter.getItem(i);
                int id = bookIDs.get(i);
                Intent intent = new Intent(bookList, BookDetail.class).
                        putExtra(Intent.EXTRA_TEXT, item);
                intent.putExtra(Intent.EXTRA_ASSIST_CONTEXT, id+"");
                //intent.putExtra("EXTRA ID", bookIDs.get(i));
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


    public class FetchBooksTask extends AsyncTask<Void, Void, String[]> {
        private final String LOG_TAG = FetchBooksTask.class.getSimpleName();

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
            PublicApiRoutes publicApiRoutes = new PublicApiRoutes() {
                @Override
                public Call<User> createUser(@Body User user) {
                    return null;
                }

                @Override
                public Call<BookPage> createBookPage(@Body BookPage bookPage) {
                    return null;
                }

                @Override
                public void login(@Field("session[email]") String email, @Field("session[password]") String password, Callback<User> callback) {

                }

                @Override
                public void getProducts(Callback<List<BookPage>> callback) {

                }
            };
            BookPage bookPage = new BookPage("title", "author", "publisher", 2005, "genre", 23543);
            publicApiRoutes.createBookPage(bookPage);

//            User user = new User();
//            user.setFirst_name("Moustafa");
//            user.setLast_name("Mahmoud");
//            user.setEmail("moustafa@mahmoud.com");
//            user.setCity("Cairo");
//            user.setCountry("Egypt");
//            user.setDate_of_birth("8/8/1994");
//            user.setGender("male");
//            user.setFbtoken("CAAJ3klW63LYBABR2SyaLQ2FwHHOCaCGX4D7Mu" +
//                    "sZAC8O6hhRicLPz72hfiW8Ph7pE1apZBHIyni8RlkODEI4MLElJvjB9z3" +
//                    "rg9XCA3rWCsZBfElzpR8PPeOWszprGiWR12GVYdI5uJ3mtZAaDqo9lpJ6" +
//                    "hpW0iJp9iDb0mui1gvdvQHE3NDTdEAwhDZBFBm6ssZD");
//            publicApiRoutes.createUser(user);

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast



                final String BOOKLIST_BASE_URL = "https://bookworm-alyakan.c9users.io/books.json";

//                final String USER_CREATE_BASE_URL = "https://bookworm-alyakan.c9users.io/users/new";
//                URL url2 = new URL(USER_CREATE_BASE_URL);
//                HttpURLConnection urlConnection1 = (HttpURLConnection) url2.openConnection();
//
//                urlConnection1.setRequestMethod("POST");
//                urlConnection.connect();
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

                Uri builtUri = Uri.parse(BOOKLIST_BASE_URL).buildUpon()
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
                String[] booksDetails = getBookDataFromJson(booklistJsonStr);
                return booksDetails;
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
        private String[] getBookDataFromJson(String booklistJsonStr)
                throws JSONException {


            final String OWM_ID = "id";
            final String OWM_TITLE = "title";
            final String OWM_AUTHOR = "author";
            final String OWM_PUBLISHER = "publisher";
            final String OWM_YEAR = "year";
            final String OWM_GENRE = "genre";
            final String OWM_URL = "url";

            JSONArray booklistArray = new JSONArray(booklistJsonStr);
            bookIDs = new ArrayList<Integer>();

            String[] resultStrs = new String[booklistArray.length()];
            for(int i = 0; i < booklistArray.length(); i++) {
                String title;
                String author;
                String genre;
                int ID;
                try{
                    Log.v(LOG_TAG, "------------------------------------");
                    title = booklistArray.getJSONObject(i).getString(OWM_TITLE);
                    author = booklistArray.getJSONObject(i).getString(OWM_AUTHOR);
                    genre = booklistArray.getJSONObject(i).getString(OWM_GENRE);
                    ID = Integer.parseInt(booklistArray.getJSONObject(i).getString(OWM_ID));

                    resultStrs[i] = title;
                    bookIDs.add(ID);

                    Log.v(LOG_TAG, title);
                    Log.v(LOG_TAG, author);
                    Log.v(LOG_TAG, genre);
                    Log.v(LOG_TAG, ID+"");
                }catch(Exception e){
                    Log.e(LOG_TAG, e.getMessage());
                }

            }


            return resultStrs;

        }
    }

}

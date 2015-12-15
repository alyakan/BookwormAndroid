package com.example.andoird.bookworm;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BookDetail extends AppCompatActivity {

    private Button button;
    private Button button_2;
    private Button button_3;

    private int bookID;
    private int status;
    private String title;
    private String author;
    private String genre;
    private int status_2;

    private ArrayList<Integer> reviewID; // a list of ids for each review, for item click listener
    private ArrayList<String> reviewURLs; // a list of urls for each review, for item click listener

    private ArrayList<String> reviews; // For Adapter population
    private ArrayAdapter<String> reviewsAdapter;

    private int current_status ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new FetchBookDetailsTask().execute();
        getBookID();
        populateBookDetailReviews();
        addListenerOnButton();
        addListenerOnButton2();
        addListenerOnButton3();




    }



    public int getStatus() {
        return this.status;
    }

    public void setStatus(int newStatus) {
        this.status = newStatus;
    }

    protected void getBookID() {
        Intent intent = this.getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_ASSIST_CONTEXT)) {
            String id = intent.getStringExtra(Intent.EXTRA_ASSIST_CONTEXT);
            bookID = Integer.parseInt(id);
        }
    }

    protected void populateDetailView() {

        Intent intent = this.getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            String book_name = intent.getStringExtra(Intent.EXTRA_TEXT);
            String id = intent.getStringExtra(Intent.EXTRA_ASSIST_CONTEXT);
            TextView detail_text = (TextView) this.findViewById(R.id.book_detail_text);
            detail_text.setText("Book title: " + title);
            TextView author_text = (TextView) this.findViewById(R.id.author_detail_text);
            author_text.setText("Author: " + author);
            bookID = Integer.parseInt(id);
            setTitle(title);
        }
    }

    protected void toggle(int s){
        button = (Button) findViewById(R.id.finish_reading_button);
        button_2 = (Button) findViewById(R.id.read_later_button);
        switch (s) {
            case 0:
                button.setText(String.format("Start reading"));
                button_2.setVisibility(View.GONE);
                break;

            case 1:
                button.setText(String.format("Finish reading"));
                button_2.setVisibility(View.GONE);
                break;

            case 2:
                button.setText(String.format("Start reading"));
                button_2.setVisibility(View.VISIBLE);
                break;
            default:
                button.setText(String.format("Start reading"));
                button_2.setVisibility(View.VISIBLE);
                break;
           /* case 4:
                button.setText(String.format("Start reading"));
                button_2.setVisibility(View.VISIBLE);
                break;*/
        }

    }

    protected void populateBookDetailReviews() {

        String[] data = {

        };

        List<String> reviews_ArrayList = new ArrayList<String>(Arrays.asList(data));
        reviewsAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item_reviews, // The xml component
                R.id.list_item_reviews_text_view, // The textview inside the xml component
                reviews_ArrayList
        );
        ListView listview = (ListView) this.findViewById(R.id.listview_reviews);
        listview.setAdapter(reviewsAdapter);
       // button = (Button) findViewById(R.id.finish_reading_button);
        //button.setText(String.format("Start Reading"));
        final BookDetail reviewlist = this;
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = reviewsAdapter.getItem(i);
                Intent intent = new Intent(reviewlist, ReviewDetail.class).
                        putExtra(Intent.EXTRA_TEXT, item);
                //toggle();
                startActivity(intent);

            }
        });


    }

    public void addListenerOnButton() {
        button = (Button) findViewById(R.id.finish_reading_button);
        button_2 = (Button) findViewById(R.id.read_later_button);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (button.getText().toString().equals("Finish reading"))
                    alterStatus(2);
                else
                    alterStatus(1);
            }
        });

    }

    public void addListenerOnButton2() {
        button_2 = (Button) findViewById(R.id.read_later_button);
        button_2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
               alterStatus(0);
            }
        });


    }

    public void addListenerOnButton3() {
        button_3 = (Button) findViewById(R.id.button);
        button_3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                EditText txtDescription = (EditText) findViewById(R.id.post_edit_text);
                String text = txtDescription.getText().toString();
                postReview(text);
            }
        });


    }



        public class FetchBookDetailsTask extends AsyncTask<Void, Void, String> {
        private final String LOG_TAG = FetchBookDetailsTask.class.getSimpleName();

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String[] data = new String[reviews.size()];
            reviewsAdapter.clear();
            reviewsAdapter.addAll(reviews.toArray(data));
            Log.v(LOG_TAG, "----------");
            Log.v(LOG_TAG, s);
            if(s.equals("null"))
                toggle(4);
            else
                toggle(Integer.parseInt(s));
            populateDetailView();

        }





        @Override
        protected String doInBackground(Void... String) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String bookdetailJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast


                final String BOOKDETAIL_BASE_URL = "https://bookworm-alyakan.c9users.io/book_pages/" + bookID + ".json";


                Uri builtUri = Uri.parse(BOOKDETAIL_BASE_URL).buildUpon()
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
                    // return null;
                }
                bookdetailJsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
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
            try {
                String s = getBookDataFromJson(bookdetailJsonStr);
                return s;
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
                return null;
            }


        }

        /*
        * Take JSON String representing the book list retrieved from the API, convert it
        * into a JSON Array and extract the information to populate the booklistAdapter
        * */
        private String getBookDataFromJson(String bookdetialJsonStr)
                throws JSONException {


            final String OWM_ID = "id";
            final String OWM_TITLE = "title";
            final String OWM_AUTHOR = "author";
            final String OWM_PUBLISHER = "publisher";
            final String OWM_YEAR = "year";
            final String OWM_STATUS = "status";
            final String OWM_GENRE = "genre";
            final String OWM_REVIEWS = "reviews";

            JSONObject bookDetailJson = new JSONObject(bookdetialJsonStr);
            JSONArray reviewsJSONArray = bookDetailJson.getJSONArray(OWM_REVIEWS);
            String status_string = bookDetailJson.getString(OWM_STATUS);



            try {
                Log.v(LOG_TAG, "------------------------------------");
                title = bookDetailJson.getString(OWM_TITLE);
                author = bookDetailJson.getString(OWM_AUTHOR);
                genre = bookDetailJson.getString(OWM_GENRE);
                bookID = Integer.parseInt(bookDetailJson.getString(OWM_ID));
                //String status_string = bookDetailJson.getString(OWM_STATUS);

                //current_status = status;

                Log.v(LOG_TAG, title);
                Log.v(LOG_TAG, author);
                Log.v(LOG_TAG, genre);
                Log.v(LOG_TAG, bookID + "");
                Log.v(LOG_TAG, status_string + "");
               // Log.v(LOG_TAG, current_status + "");

            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage());
            }
            reviewID = new ArrayList<Integer>();
            reviews = new ArrayList<String>();
            for (int i = 0; i < reviewsJSONArray.length(); i++) {
                String review;
                String user_id;
                String review_id;
                try {
                    Log.v(LOG_TAG, "------------------------------------");
                    review = reviewsJSONArray.getJSONObject(i).getString("review");
                    user_id = reviewsJSONArray.getJSONObject(i).getString("user_id");
                    review_id = reviewsJSONArray.getJSONObject(i).getString("id");
                    reviews.add(review);
                    reviewID.add(Integer.parseInt(review_id));

                    Log.v(LOG_TAG, review);
                    Log.v(LOG_TAG, user_id);
                    Log.v(LOG_TAG, review_id + "");
                } catch (Exception e) {
                    Log.e(LOG_TAG, e.getMessage());
                }
            }
            return status_string;
        }
    }

    public void   alterStatus(int st ) {


        String url = "https://bookworm-alyakan.c9users.io/current_status/"+bookID+"/"+st+".json" ;

        // Request a string response
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            /*response = response.getJSONObject("args");
                            String site = response.getString("site"),
                                    network = response.getString("network");
                            System.out.println("Site: "+site+"\nNetwork: "+network);*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        Volley.newRequestQueue(this).add(jsonRequest);
        toggle(st);


    }

    public void postReview(String s){
        final String  review = s;
        String url = "https://bookworm-alyakan.c9users.io/book_pages/"+bookID+"/reviews";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {/*
                            JSONObject jsonResponse = new JSONObject(response).getJSONObject("form");
                            String site = jsonResponse.getString("site"),
                                    network = jsonResponse.getString("network");
                            System.out.println("Site: "+site+"\nNetwork: "+network))*/
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
        ) {
            @Override
            protected Map<String, String> getParams()
            {

                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("review", review);
                return params;

            }
        };
        Volley.newRequestQueue(this).add(postRequest);

    }
}





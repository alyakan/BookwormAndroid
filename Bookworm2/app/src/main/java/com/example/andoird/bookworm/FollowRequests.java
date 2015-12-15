package com.example.andoird.bookworm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FollowRequests extends AppCompatActivity {
    private ArrayAdapter<String> requestAdapter;
    private int[] userIDs;
    private String []followRequestsNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_requests);
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
        populateFollowRequestsList();
    }

    protected void populateFollowRequestsList(){
        String[] data = {
//                "Aly has requested to follow you",
//                "Kareem has requested to follow you",
//                "Youssef has requested to follow you",
//                "Etefy has requested to follow you",
//                "Rana has requested to follow you",
//                "Menna has requested to follow you",

        };


        List<String> request_ArrayList = new ArrayList<String>(Arrays.asList(data));
        requestAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item_follow_request, // The xml component
                R.id.list_item_follow_request_text_view, // The textview inside the xml component
                request_ArrayList
        );

        final FollowRequests followRequests = this;
        ListView listview = (ListView) this.findViewById(R.id.listview_followrequests);
        listview.setAdapter(requestAdapter);
        getFollowRequests();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = followRequestsNames[i];
                Intent intent = new Intent(followRequests, Profile.class).
                        putExtra(Intent.EXTRA_TEXT, item);
                String id = userIDs[i] + "";
                Log.v("ID: ", id);
                intent.putExtra(Intent.EXTRA_ASSIST_CONTEXT, id);
                intent.putExtra(Intent.EXTRA_REFERRER_NAME, "follow_request");
                startActivity(intent);

            }
        });



    }

    private void getFollowRequests(){
        String url = "https://bookworm-alyakan.c9users.io/users/2/follows.json";

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            populateListWithResponse(response);
                            JSONArray jsonResponse = new JSONArray(response);

                            String verif = jsonResponse.getJSONObject(0).getString("sender");

                            Log.d("Verification", "--------------------------------");
                            Log.d("FOLLOWW", verif);

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
        String []followRequests;

        try{
            JSONArray jsonResponse = new JSONArray((response));
            followRequestsNames = new String[jsonResponse.length()];
            userIDs = new int[jsonResponse.length()];
            for (int i = 0; i < jsonResponse.length(); i++){

                String sender = jsonResponse.getJSONObject(i).getString("sender");
                JSONObject jsonSender = new JSONObject(sender);

                userIDs[i] = Integer.parseInt(jsonSender.getString("id"));

                String senderName = jsonSender.getString("first_name") + " " + jsonSender.getString("last_name");

                followRequestsNames[i] = senderName;
            }
            if(followRequestsNames != null) {
                requestAdapter.clear();
                requestAdapter.addAll(followRequestsNames);
            }
        } catch(JSONException e){

        }
    }

}

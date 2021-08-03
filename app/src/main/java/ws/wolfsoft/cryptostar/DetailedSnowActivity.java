package ws.wolfsoft.cryptostar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapter.SnowDataAdapter;
import Domain.SnowCount;
import Domain.SnowResponse;
import Domain.UrlConfig;
import Domain.User;
import ws.wolfsoft.cryptostar.Helper.StaticEntityHelper;


public class DetailedSnowActivity extends AppCompatActivity {

    private ArrayList<SnowResponse> userModalArrayList;
    private SnowDataAdapter userRVAdapter;
    private RecyclerView userRV;
    private ProgressBar loadingPB;
    private NestedScrollView nestedSV;

    TextView tvTotalCountReport,tvReportName;


    // creating a variable for our page and limit as 2
    // as our api is having highest limit as 2 so
    // we are setting a limit = 2
    int offset = 0, limit=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_snow);


        // creating a new array list.
        userModalArrayList = new ArrayList<SnowResponse>();

        // initializing our views.
        userRV = findViewById(R.id.idRVUsers);
        loadingPB = findViewById(R.id.idPBLoading);
        nestedSV = findViewById(R.id.idNestedSV);

        tvTotalCountReport = findViewById(R.id.tvTotalCountReport);
        tvReportName = findViewById(R.id.tvReportName);


        Intent i = getIntent();
        UrlConfig urlConfig = (UrlConfig) i.getParcelableExtra("urlConfig");
        tvTotalCountReport.setText(""+urlConfig.Count);
        tvReportName.setText(urlConfig.Endpoint_name);

        userRVAdapter = new SnowDataAdapter(userModalArrayList, DetailedSnowActivity.this);

        // setting layout manager to our recycler view.
        userRV.setLayoutManager(new LinearLayoutManager(DetailedSnowActivity.this));

        // setting adapter to our recycler view.
        userRV.setAdapter(userRVAdapter);


        Toast.makeText(getApplicationContext(),"Feteching Snow report details",Toast.LENGTH_LONG).show();
        volleyPost(urlConfig,limit,offset);

        // adding on scroll change listener method for our nested scroll view.
        nestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // on scroll change we are checking when users scroll as bottom.
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    offset = offset+limit;
                    loadingPB.setVisibility(View.VISIBLE);
                    volleyPost(urlConfig,limit,offset);
                }
            }
        });
    }


    public void volleyPost(UrlConfig urlConfig,int limit, int offset){
        String postUrl = StaticEntityHelper.BASE_URL+"SnowHelperApi/SnowContent?limit="+limit+"&offset="+offset;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Log.i("response",urlConfig.url);

        JSONObject postData = new JSONObject();
        try {
            postData.put("url", urlConfig.url);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response",postUrl);

                try {

                    // on below line we are extracting data from our json array.
                    JSONArray dataArray = response.getJSONArray("result");
                    Log.i("response","Response Count: "+dataArray.length());

                    if(dataArray.length() == 0)
                    {
                        loadingPB.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"No more data to show..",Toast.LENGTH_SHORT).show();
                    }

                    // passing data from our json array in our array list.
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject jsonObject = dataArray.getJSONObject(i);

                        JsonElement mJson =  JsonParser.parseString(jsonObject.toString());
                        Gson gson = new Gson();
                        SnowResponse snowResponse = gson.fromJson(mJson, SnowResponse.class);
                        snowResponse.assignmentGroupName = jsonObject.getString("assignment_group.name");
                        snowResponse.assignedToName = jsonObject.getString("assigned_to.name");
                        // on below line we are extracting data from our json object.
                        userModalArrayList.add(snowResponse);                   // passing array list to our adapter class.
                        //userRVAdapter = new SnowDataAdapter(userModalArrayList, DetailedSnowActivity.this);

                        // setting layout manager to our recycler view.
                        //userRV.setLayoutManager(new LinearLayoutManager(DetailedSnowActivity.this));

                        // setting adapter to our recycler view.
                        //userRV.setAdapter(userRVAdapter);


                        userRVAdapter.notifyItemInserted(userModalArrayList.size()-1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i("response",error.getMessage());

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Repost data fetching failed.",
                        Toast.LENGTH_LONG);

                toast.show();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }


    public User getPref(String key, Context context) {
        SharedPreferences preferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String userInfo = preferences.getString(key, null);
        JsonElement mJson =  JsonParser.parseString(userInfo);
        Gson gson = new Gson();
        User user = gson.fromJson(mJson, User.class);
        return user;
    }
}
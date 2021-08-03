package ws.wolfsoft.cryptostar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

import org.json.JSONException;
import org.json.JSONObject;

import Domain.SnowCount;
import Domain.UrlConfig;
import ModelClass.WalletModelClass;
import ws.wolfsoft.cryptostar.Helper.StaticEntityHelper;
import ws.wolfsoft.cryptostar.databinding.ActivityDetailedViewBinding;

public class DetailedView extends AppCompatActivity {

    private ActivityDetailedViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailedViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        Intent i = getIntent();
        UrlConfig urlConfig = (UrlConfig) i.getParcelableExtra("urlConfig");

        toolBarLayout.setTitle("Total Count: "+urlConfig.Count);

        Toast.makeText(getApplicationContext(),"Feteching Snow report details",Toast.LENGTH_LONG).show();
        volleyPost(urlConfig);

    }

    public void volleyPost(UrlConfig urlConfig){
        String postUrl = StaticEntityHelper.BASE_URL+"SnowHelperApi/SnowContent";
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
                Log.i("response",response.toString());
                JsonElement mJson =  JsonParser.parseString(response.toString());
                Gson gson = new Gson();
                //SnowCount snowCount = gson.fromJson(mJson, SnowCount.class);


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
}
package ws.wolfsoft.cryptostar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapter.CryptoListRecycleAdapter;
import Adapter.WalletRecycleAdapter;
import Domain.SnowCount;
import Domain.UrlConfig;
import Domain.User;
import ModelClass.CryptoListModelClass;
import ModelClass.WalletModelClass;
import ws.wolfsoft.cryptostar.Helper.StaticEntityHelper;

public class WalletCryptoStarActivity extends AppCompatActivity implements View.OnClickListener{


    int totalCount = 0;
    int itemCounter = 0;

    ImageView wallet_img,chart_img,trading_img,alert_img,setting_img;
    TextView wallet_txt,chart_txt,trading_txt,alert_txt,setting_txt,tvTotalCount,tvUserName;
    LinearLayout linear1,linear2,linear3,linear4,linear5;


    private ArrayList<WalletModelClass> walletModelClasses;
    private RecyclerView recyclerView;
    private WalletRecycleAdapter bAdapter;


    private String title[] = {"Bitcoin","Ethereum","Ripple","LiteCoin","Bitcoin"};
    private Integer icon[]={ R.drawable.ic_btc,R.drawable.ic_etherium,R.drawable.ic_ripple,R.drawable.ic_litecoin,R.drawable.ic_btc};
    private String icon_type[] = {"BTC","ETH","XRP","LTC","BTC"};
    private String percentage[] = {"20%","5%","5%","18%","25%"};
    private Integer arrow[] = {R.drawable.ic_arrowup,R.drawable.ic_arrowup,R.drawable.ic_arrowdown,R.drawable.ic_arrowdown,R.drawable.ic_arrowup};
    private String price[] = {"$5,291.20","$2,213.04","$4,831.69","$2,529.21","$5,291.20"};
    private String value[] = {"0.592 BTC","2.624 ETH","2.624 XRP","2.624 XRP","0.592 BTC"};



    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_crypto_star);




        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(WalletCryptoStarActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

//        recyclerView.getViewTreeObserver()
//                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//                    @Override
//                    public void onScrollChanged() {
//                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//
//                        int pos = linearLayoutManager.find();
//
//                        Log.i("response","verticle: "+pos);
//                    }
//                });




        walletModelClasses = new ArrayList<>();

//        for (int i = 0; i < title.length; i++) {
//            WalletModelClass beanClassForRecyclerView_contacts = new WalletModelClass(title[i],icon[i],icon_type[i],
//                    percentage[i],arrow[i],price[i],value[i]);
//            walletModelClasses.add(beanClassForRecyclerView_contacts);
//        }

        bAdapter = new WalletRecycleAdapter(WalletCryptoStarActivity.this,walletModelClasses);
        recyclerView.setAdapter(bAdapter);

        Toast.makeText(getApplicationContext(),"Fetching report urls.",Toast.LENGTH_LONG).show();
        volleyGet();

        tvTotalCount = findViewById(R.id.tvTotalCount);
        tvUserName = findViewById(R.id.tvUserName);
        String userName = getPref("userInfo",getApplicationContext()).Name;
        tvUserName.setText(userName);


        wallet_img = findViewById(R.id.wallet_img);
        chart_img = findViewById(R.id.chart_img);
        trading_img = findViewById(R.id.trading_img);
        alert_img = findViewById(R.id.alert_img);
        setting_img = findViewById(R.id.setting_img);

        wallet_txt = findViewById(R.id.wallet_txt);
        chart_txt = findViewById(R.id.chart_txt);
        trading_txt = findViewById(R.id.trading_txt);
        alert_txt = findViewById(R.id.alert_txt);
        setting_txt = findViewById(R.id.setting_txt);

        linear1 = findViewById(R.id.linear1);
        linear2 = findViewById(R.id.linear2);
        linear3 = findViewById(R.id.linear3);
        linear4 = findViewById(R.id.linear4);
        linear5 = findViewById(R.id.linear5);


        linear1.setOnClickListener(this);
        linear2.setOnClickListener(this);
        linear3.setOnClickListener(this);
        linear4.setOnClickListener(this);
        linear5.setOnClickListener(this);
    }

    public void volleyGet()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticEntityHelper.BASE_URL+"UrlConfigApil/GetUrl_config_tabl";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.i("response",response.toString());
                        try
                        {
                            ArrayList<UrlConfig> listdata = new ArrayList<UrlConfig>();
                            JSONArray jArray = new JSONArray(response.toString());
                            if (jArray != null) {
                                for (int i=0;i<jArray.length();i++){

                                    JsonElement mJson =  JsonParser.parseString(jArray.getString(i));
                                    Gson gson = new Gson();
                                    UrlConfig object = gson.fromJson(mJson, UrlConfig.class);
                                    listdata.add(object);

                                }
                            }

                            Log.i("response","Response count: "+listdata.size());
                            Toast.makeText(getApplicationContext(),"Total "+listdata.size()+" reports found",Toast.LENGTH_LONG).show();
                            for (UrlConfig urlConfig: listdata) {
                                volleyPost(urlConfig);
                            }

                        }
                        catch (Exception e)
                        {
                            Toast.makeText(getApplicationContext(),"Error in response",Toast.LENGTH_LONG).show();
                            Log.i("response",e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("response",error.toString());

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public User getPref(String key, Context context) {
        SharedPreferences preferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String userInfo = preferences.getString(key, null);
        JsonElement mJson =  JsonParser.parseString(userInfo);
        Gson gson = new Gson();
        User user = gson.fromJson(mJson, User.class);
        return user;
    }

    public void volleyPost(UrlConfig urlConfig){
        String postUrl = StaticEntityHelper.BASE_URL+"SnowHelperApi/SnowGenericCall";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("url", urlConfig.url);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                Log.i("response",response.toString());
                JsonElement mJson =  JsonParser.parseString(response.toString());
                Gson gson = new Gson();
                SnowCount snowCount = gson.fromJson(mJson, SnowCount.class);

                totalCount = totalCount + Integer.parseInt(snowCount.result.stats.count);

                WalletModelClass beanClassForRecyclerView_contacts = new WalletModelClass(urlConfig.Endpoint_name,icon[0],icon_type[0],
                        percentage[0],arrow[0],snowCount.result.stats.count,itemCounter,urlConfig);
                walletModelClasses.add(beanClassForRecyclerView_contacts);

                bAdapter.notifyItemInserted(walletModelClasses.size()-1);
                itemCounter++;

                tvTotalCount.setText(""+totalCount);


//                SharedPreferences.Editor editor = getSharedPreferences("UserData", MODE_PRIVATE).edit();
//                editor.putString("userInfo", response.toString());
//                editor.apply();
//
//                Intent intent = new Intent(LoginActivity.this,WalletCryptoStarActivity.class);
//                startActivity(intent);
//
//                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i("response",error.getMessage());

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Repost count fetching failed.",
                        Toast.LENGTH_LONG);

                toast.show();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.linear1:

                wallet_img.setImageResource(R.drawable.ic_wallet_coloring);
                chart_img.setImageResource(R.drawable.ic_chart_gray);
                trading_img.setImageResource(R.drawable.ic_trading_gray);
                alert_img.setImageResource(R.drawable.ic_alert_gray);
                setting_img.setImageResource(R.drawable.ic_settings_gray);


                wallet_txt.setTextColor(Color.parseColor("#141a22"));
                chart_txt.setTextColor(Color.parseColor("#a6b0b9"));
                trading_txt.setTextColor(Color.parseColor("#a6b0b9"));
                alert_txt.setTextColor(Color.parseColor("#a6b0b9"));
                setting_txt.setTextColor(Color.parseColor("#a6b0b9"));

                break;


            case R.id.linear2:

                wallet_img.setImageResource(R.drawable.ic_wallet_gray);
                chart_img.setImageResource(R.drawable.ic_chart_coloring);
                trading_img.setImageResource(R.drawable.ic_trading_gray);
                alert_img.setImageResource(R.drawable.ic_alert_gray);
                setting_img.setImageResource(R.drawable.ic_settings_gray);


                wallet_txt.setTextColor(Color.parseColor("#a6b0b9"));
                chart_txt.setTextColor(Color.parseColor("#141a22"));
                trading_txt.setTextColor(Color.parseColor("#a6b0b9"));
                alert_txt.setTextColor(Color.parseColor("#a6b0b9"));
                setting_txt.setTextColor(Color.parseColor("#a6b0b9"));


                break;

            case R.id.linear3:

                wallet_img.setImageResource(R.drawable.ic_wallet_gray);
                chart_img.setImageResource(R.drawable.ic_chart_gray);
                trading_img.setImageResource(R.drawable.ic_trading_coloring);
                alert_img.setImageResource(R.drawable.ic_alert_gray);
                setting_img.setImageResource(R.drawable.ic_settings_gray);


                wallet_txt.setTextColor(Color.parseColor("#a6b0b9"));
                chart_txt.setTextColor(Color.parseColor("#a6b0b9"));
                trading_txt.setTextColor(Color.parseColor("#141a22"));
                alert_txt.setTextColor(Color.parseColor("#a6b0b9"));
                setting_txt.setTextColor(Color.parseColor("#a6b0b9"));

                break;

            case R.id.linear4:

                wallet_img.setImageResource(R.drawable.ic_wallet_gray);
                chart_img.setImageResource(R.drawable.ic_chart_gray);
                trading_img.setImageResource(R.drawable.ic_trading_gray);
                alert_img.setImageResource(R.drawable.ic_alert_coloring);
                setting_img.setImageResource(R.drawable.ic_settings_gray);



                wallet_txt.setTextColor(Color.parseColor("#a6b0b9"));
                chart_txt.setTextColor(Color.parseColor("#a6b0b9"));
                trading_txt.setTextColor(Color.parseColor("#a6b0b9"));
                alert_txt.setTextColor(Color.parseColor("#141a22"));
                setting_txt.setTextColor(Color.parseColor("#a6b0b9"));

                break;


            case R.id.linear5:

                wallet_txt.setTextColor(Color.parseColor("#a6b0b9"));
                chart_txt.setTextColor(Color.parseColor("#a6b0b9"));
                trading_txt.setTextColor(Color.parseColor("#a6b0b9"));
                alert_txt.setTextColor(Color.parseColor("#a6b0b9"));
                setting_txt.setTextColor(Color.parseColor("#141a22"));

                SharedPreferences.Editor editor = getSharedPreferences("UserData", MODE_PRIVATE).edit();
                editor.putString("userInfo", null);
                editor.apply();

                Intent intent = new Intent(WalletCryptoStarActivity.this,LoginActivity.class);
                startActivity(intent);

                break;

        }

    }
}
